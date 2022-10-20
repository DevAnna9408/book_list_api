package kr.co.apexsoft.fw.api.dto.user

import kr.co.apexsoft.fw.domain.model.user.User

data class UserOut(
    val oid: Long,
    val userId: String,
) {
    companion object {
        fun fromEntity(e: User): UserOut {
            return UserOut(
                oid = e.oid!!,
                userId = e.userId,
            )
        }
    }
}
