package kr.co.book.list.api.dto.siren

import kr.co.book.list.domain.model.book.Book
import kr.co.book.list.domain.model.user.User
import kr.co.book.list.domain.model.user.UserSirenHistory

data class SirenIn(

    val sirenUserOid: Long,
    val sirenedUserOid: Long,
    val sirenedBookOid: Long,
    val reason: String

) {
    fun toEntity(sirenUser: User, sirenedUser: User, sirenedBook: Book): UserSirenHistory {
        return UserSirenHistory(
            sirenUser = sirenUser,
            sirenedUser = sirenedUser,
            sirenedBook = sirenedBook,
            reason = reason
        )
    }

}
