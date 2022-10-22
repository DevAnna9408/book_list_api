package kr.co.apexsoft.fw.api.dto.user

import kr.co.apexsoft.fw.domain._common.EnumValue
import kr.co.apexsoft.fw.lib.security.SignInUser

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
