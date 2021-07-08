package com.example.tapandgo.repository.impl

import com.example.tapandgo.api.UserService
import com.example.tapandgo.database.CarDao
import com.example.tapandgo.database.RentalDao
import com.example.tapandgo.database.UserDao
import com.example.tapandgo.helpers.EncryptedPrefs
import com.example.tapandgo.model.Profile
import com.example.tapandgo.model.remote.user.PostLoginRequest
import com.example.tapandgo.model.remote.user.PostRefreshTokenRequest
import com.example.tapandgo.model.remote.user.PostRegistrationRequest
import com.example.tapandgo.repository.UserRepository
import com.example.tapandgo.utils.*
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class UserRepositoryImpl(
    private val userDao: UserDao,
    private val carDao: CarDao,
    private val rentalDao: RentalDao,
    private val userService: UserService
) : UserRepository {

    override suspend fun removeAll() {
        userDao.deleteAll()
    }

    override suspend fun login(email: String, password: String) {
        try {
            val result = userService.login(PostLoginRequest(email, password))
            userDao.insert(result.profile.toLocalProfile())
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

    override suspend fun register(
        firstName: String,
        lastName: String,
        email: String,
        password: String
    ) {
        try {
            val result =
                userService.register(PostRegistrationRequest(firstName, lastName, email, password))
            userDao.insert(result.profile.toLocalProfile())
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

    override suspend fun logout() {
        userDao.deleteAll()
        carDao.deleteAll()
        rentalDao.deleteAll()
        EncryptedPrefs.encryptedPrefs.edit().clear().commit()
    }

    override suspend fun getUser(): Flow<Resource<Profile>> {
        return networkBoundResource(
            query = { userDao.loadProfile() },
            fetch = {
                if (EncryptedPrefs.shouldVerify()) {
                    refreshToken()
                }
                userService.getProfile()
            },
            saveFetchResult = { result ->
                val profile = result.toLocalProfile()
                userDao.deleteAll()
                userDao.insert(profile)
            }
        )
    }

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
}