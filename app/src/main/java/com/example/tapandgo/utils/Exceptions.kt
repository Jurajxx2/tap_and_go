package com.example.tapandgo.utils

import okio.IOException
import retrofit2.HttpException

data class ApiException(val statusCode: String) : Exception()

class NoInternetException : Exception()

class UnexpectedException(cause: Exception) : Exception(cause)

class ConflictRecordException : Exception()

class BadRequestException : Exception()

class MissingRefreshTokenException : Exception()

fun mapToDomainException(
    remoteException: Exception,
    httpExceptionsMapper: (HttpException) -> Exception? = { null }
): Exception {
    return when (remoteException) {
        is IOException -> NoInternetException()
        is HttpException -> httpExceptionsMapper(remoteException) ?: ApiException(remoteException.code().toString())
        else -> UnexpectedException(remoteException)
    }
}