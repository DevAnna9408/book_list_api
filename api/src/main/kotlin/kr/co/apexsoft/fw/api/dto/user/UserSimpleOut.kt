package kr.co.apexsoft.fw.api.dto.user

import kr.co.apexsoft.fw.domain.model.user.User

data class UserSimpleOut(
    val userId: String,
) {
    companion object {
        fun fromEntity(e: User): UserSimpleOut {
            return UserSimpleOut(
                userId = e.userId,
            )
        }
    }
}
