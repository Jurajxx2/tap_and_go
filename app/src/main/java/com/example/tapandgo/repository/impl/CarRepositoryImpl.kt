package com.example.tapandgo.repository.impl

import com.example.tapandgo.api.CarService
import com.example.tapandgo.api.UserService
import com.example.tapandgo.database.CarDao
import com.example.tapandgo.helpers.EncryptedPrefs
import com.example.tapandgo.helpers.FirebaseObject
import com.example.tapandgo.model.Car
import com.example.tapandgo.model.remote.user.PostRefreshTokenRequest
import com.example.tapandgo.repository.CarRepository
import com.example.tapandgo.utils.*
import com.google.firebase.database.GenericTypeIndicator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class CarRepositoryImpl(
    private val carService: CarService,
    private val carDao: CarDao,
    private val userService: UserService
) : CarRepository {

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

    override suspend fun removeAll() {
        carDao.deleteAll()
    }

    override fun getCars(): Flow<Resource<List<Car>>> {
        return networkBoundResource(
            query = { carDao.getAll() },
            fetch = {
                if (EncryptedPrefs.shouldVerify()) {
                    refreshToken()
                }
                carService.getCars()
            },
            saveFetchResult = { items ->
                val carList = items.map { it.toLocalCar() }
                carDao.deleteAll()
                carDao.insertAll(*carList.toTypedArray())
            }
        )
    }
}