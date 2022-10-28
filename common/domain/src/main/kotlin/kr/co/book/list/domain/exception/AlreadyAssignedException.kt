package kr.co.book.list.domain.exception

class AlreadyAssignedException(
    msg: String,
    val userOid: Long,
    val userName: String,
) : RuntimeException(msg) {
}
