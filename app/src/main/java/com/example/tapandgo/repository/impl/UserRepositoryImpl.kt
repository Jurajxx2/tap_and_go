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
import java.lang.Exception

class UserRepositoryImpl(private val userDao: UserDao, private val carDao: CarDao, private val rentalDao: RentalDao, private val userService: UserService): UserRepository {

    override suspend fun login(email: String, password: String) {
        try {
            val result = userService.login(PostLoginRequest(email, password))
            userDao.insert(result.profile.toLocalProfile())
            EncryptedPrefs.setToken(result.accessToken)
            EncryptedPrefs.setToken(result.refreshToken)
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

    override suspend fun register(firstName: String, lastName: String, email: String, password: String) {
        try {
            val result = userService.register(PostRegistrationRequest(firstName, lastName, email, password))
            userDao.insert(result.profile.toLocalProfile())
            EncryptedPrefs.setToken(result.accessToken)
            EncryptedPrefs.setToken(result.refreshToken)
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
        //carDao.deleteAll()
        rentalDao.deleteAll()
        EncryptedPrefs.encryptedPrefs.edit().clear().apply()
    }

    override suspend fun getUser() {
        try {
            val result = userService.getProfile()
            userDao.insert(result.toLocalProfile())
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

    override suspend fun refreshToken() {
        try {
            val result = userService.refreshToken(PostRefreshTokenRequest(EncryptedPrefs.getRefreshToken() ?: throw MissingRefreshTokenException()))
            EncryptedPrefs.setToken(result.accessToken)
            EncryptedPrefs.setToken(result.refreshToken)
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