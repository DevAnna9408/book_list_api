package kr.co.apexsoft.fw.api.dto.book

import kr.co.apexsoft.fw.domain.model.book.Book
import kr.co.apexsoft.fw.domain.model.book.BookThumb
import kr.co.apexsoft.fw.domain.model.user.User

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
