package com.mackbex.rickdex.data.common

import com.mackbex.rickdex.domain.common.DataError
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


fun Throwable.toDataError(): DataError.Network = when (this) {
  is UnknownHostException -> DataError.Network.NO_CONNECTION
  is SocketTimeoutException -> DataError.Network.TIMEOUT
  is IOException -> DataError.Network.NO_CONNECTION
  is HttpException -> when (code()) {
    404 -> DataError.Network.NOT_FOUND
    429 -> DataError.Network.TOO_MANY_REQUESTS
    in 500..599 -> DataError.Network.SERVER_ERROR
    else -> DataError.Network.UNKNOWN
  }

  else -> DataError.Network.UNKNOWN
}