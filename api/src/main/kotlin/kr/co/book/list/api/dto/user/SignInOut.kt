package kr.co.book.list.api.dto.user

import kr.co.book.list.domain._common.EnumValue
import kr.co.book.list.lib.security.SignInUser

data class SignInOut(
    val userId: String,
    val accessToken: String,
    val roles: List<EnumValue>,
    val customInfo: CustomInfo,
) {
    data class CustomInfo(
        val userId: String?,
        val userOid: Long?,
        val nickName: String?
    )

    companion object {
        fun from(signInUser: SignInUser, accessToken: String): SignInOut {
            return SignInOut(
                userId = signInUser.username,
                accessToken = accessToken,
                roles = signInUser.roles().map { EnumValue(it) },
                customInfo = CustomInfo(
                    userId = signInUser.userId(),
                    userOid = signInUser.userOid(),
                    nickName = signInUser.nickName()
                )
            )
        }
    }
}
