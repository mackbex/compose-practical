package com.mackbex.rickdex.domain.common

sealed interface DataError {
  enum class Network : DataError {
    NO_CONNECTION,
    TIMEOUT,
    TOO_MANY_REQUESTS,
    SERVER_ERROR,
    NOT_FOUND,
    UNKNOWN
  }
}
