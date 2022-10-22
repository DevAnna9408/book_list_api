package kr.co.apexsoft.fw.api.dto.user

import kr.co.apexsoft.fw.domain.model.user.User

data class UserSimpleOut(
    val userOid: Long,
    val userId: String,
    val nickName: String
) {
    companion object {
        fun fromEntity(e: User): UserSimpleOut {
            return UserSimpleOut(
                userOid = e.oid!!,
                userId = e.userId,
                nickName = e.nickName()
            )
        }
    }
}
