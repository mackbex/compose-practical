package com.mackbex.rickdex.domain.common


sealed interface Result<out D, out E : DataError> {
  data class Success<out D>(val data: D) : Result<D, Nothing>
  data class Failure<out E : DataError>(val error: E) : Result<Nothing, E>
}

inline fun <D, E : DataError, R> Result<D, E>.map(transform: (D) -> R): Result<R, E> =
  when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Failure -> this
  }