package kr.co.book.list.api.dto.user

import kr.co.book.list.domain.model.Role
import kr.co.book.list.domain.model.user.User
import org.springframework.security.crypto.password.PasswordEncoder

data class SignUpIn(
    val userId: String,
    val password: String,
    val question: String,
    val answer: String
) {
    fun toEntity(passwordEncoder: PasswordEncoder): User {
        return User(
            userId = userId,
            nickName = userId.split("@")[0],
            roles = listOf(Role.ROLE_USER),
            password = passwordEncoder.encode(password),
            question = question,
            answer = answer
        )
    }
}
