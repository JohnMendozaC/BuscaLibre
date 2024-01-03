package com.john.buscalibre.shared.data.network

import retrofit2.Response
import java.net.HttpURLConnection

sealed class NetworkResponse<T> {
    data class Success<T>(val response: T) : NetworkResponse<T>()
    data class Error<T>(val codeError: Int = 0) : NetworkResponse<T>()
    companion object {
        suspend fun <T> validateResponse(request: suspend () -> Response<T>): NetworkResponse<T> {
            return try {
                val response = request.invoke()
                val isValid = if (response.isSuccessful) {
                    when (response.code()) {
                        HttpURLConnection.HTTP_OK -> {
                            true
                        }

                        else -> {
                            false
                        }
                    }
                } else {
                    false
                }
                return if (isValid) {
                    val body = response.body()
                    if (body == null) {
                        Error()
                    } else {
                        Success(body)
                    }
                } else {
                    Error()
                }
            } catch (_: Exception) {
                Error()
            }
        }
    }
}
