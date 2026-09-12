package com.mackbex.rickdex.core.ui

import androidx.compose.runtime.Composable
import com.mackbex.rickdex.domain.common.DataError

@Composable
fun DataError.asMessage(): String = when (this) {
  DataError.Network.NO_CONNECTION -> "인터넷 연결을 확인해주세요"
  DataError.Network.TIMEOUT -> "응답이 지연되고 있어요. 다시 시도해주세요"
  DataError.Network.TOO_MANY_REQUESTS -> "잠시 후 다시 시도해주세요"
  DataError.Network.SERVER_ERROR -> "서버에 문제가 생겼어요"
  DataError.Network.NOT_FOUND -> "결과가 없어요"
  DataError.Network.UNKNOWN -> "알 수 없는 오류가 발생했어요"
  else -> "오류가 발생했어요"
}