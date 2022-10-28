package kr.co.book.list.api.dto.book

import kr.co.book.list.domain.model.book.Book
import kr.co.book.list.domain.model.book.BookThumb
import kr.co.book.list.domain.model.user.User

data class BookThumbIn(
    val thumbsYn: Boolean
) {
    fun toEntity(u: User, b: Book): BookThumb {
        return BookThumb(
            thumbBook = b,
            thumbUser = u,
            thumbYn = thumbsYn
        )
    }

}
