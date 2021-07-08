package com.example.tapandgo.repository.impl

import com.example.tapandgo.api.RentalService
import com.example.tapandgo.api.UserService
import com.example.tapandgo.database.RentalDao
import com.example.tapandgo.helpers.EncryptedPrefs
import com.example.tapandgo.model.Rental
import com.example.tapandgo.model.remote.rental.PostCreateRentalRequest
import com.example.tapandgo.model.remote.user.PostRefreshTokenRequest
import com.example.tapandgo.repository.RentalRepository
import com.example.tapandgo.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import java.lang.Exception

class RentalRepositoryImpl(
    private val rentalService: RentalService,
    private val rentalDao: RentalDao,
    private val userService: UserService
) : RentalRepository {

    override suspend fun refreshToken() {
        try {
            val result = userService.refreshToken(
                PostRefreshTokenRequest(
                    EncryptedPrefs.getRefreshToken() ?: throw MissingRefreshTokenException()
                )
            )
            EncryptedPrefs.setToken(result.accessToken)
            EncryptedPrefs.setRefreshToken(result.refreshToken)
        } catch (e: Exception) {
            throw mapToDomainException(e) {
                when (it.code()) {
                    400 -> BadRequestException()
                    409 -> ConflictRecordException()
                    else -> UnexpectedException(it)
                }
            }
        }
    }

    override suspend fun createRental(id: Int, from: String, to: String) {
        if (EncryptedPrefs.shouldVerify()) {
            refreshToken()
        }
        if (from == to) {
            val newTo = stringToDate(to).toLocalDate()
            rentalService.createRental(
                PostCreateRentalRequest(
                    id,
                    from,
                    localDateAtTheEndOfDayToString(newTo)
                )
            )
        } else {
            rentalService.createRental(PostCreateRentalRequest(id, from, to))
        }
    }

    override suspend fun removeAll() {
        rentalDao.deleteAll()
    }

    @ExperimentalCoroutinesApi
    override fun getRentals(): Flow<Resource<List<Rental>>> {
        return networkBoundResource(
            query = { rentalDao.getAll() },
            fetch = {
                if (EncryptedPrefs.shouldVerify()) {
                    refreshToken()
                }
                rentalService.getRentals()
            },
            saveFetchResult = { result ->
                val rentalList = result.data.map { it.toLocalRental() }
                rentalDao.deleteAll()
                rentalDao.insertAll(*rentalList.toTypedArray())
            }
        )
    }
}