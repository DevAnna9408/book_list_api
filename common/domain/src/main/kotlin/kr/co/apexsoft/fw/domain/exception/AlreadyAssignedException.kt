package kr.co.apexsoft.fw.domain.exception

class AlreadyAssignedException(
    msg: String,
    val userOid: Long,
    val userName: String,
) : RuntimeException(msg) {
}
