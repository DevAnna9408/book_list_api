package kr.co.apexsoft.fw.api.dto.user

import kr.co.apexsoft.fw.domain.model.Role
import kr.co.apexsoft.fw.domain.model.user.User
import org.springframework.security.crypto.password.PasswordEncoder

data class SignUpIn(
    val role: Role,
    val userId: String,
    val password: String,
) {
    fun toEntity(passwordEncoder: PasswordEncoder): User {
        return User(
            userId = userId,
            roles = listOf(Role.ROLE_USER),
            password = passwordEncoder.encode(password),
        )
    }
}
