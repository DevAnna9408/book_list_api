package kr.co.book.list.lib.error

import kr.co.book.list.lib.utils.MessageUtil

class JwtUnauthenticatedAccessException
    : RuntimeException(MessageUtil.getMessage("UNAUTHORIZED_ACCESS")) {
}
