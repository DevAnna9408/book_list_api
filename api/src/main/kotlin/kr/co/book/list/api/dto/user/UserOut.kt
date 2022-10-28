package kr.co.book.list.api.dto.user

import kr.co.book.list.domain.model.user.User

data class UserOut(
    val oid: Long,
    val userId: String,
    val nickName: String
) {
    companion object {
        fun fromEntity(e: User): UserOut {
            return UserOut(
                oid = e.oid!!,
                userId = e.userId,
                nickName = e.nickName()
            )
        }
    }
}