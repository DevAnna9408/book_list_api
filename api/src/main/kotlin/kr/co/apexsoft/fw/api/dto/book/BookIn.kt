package kr.co.apexsoft.fw.api.dto.book

import kr.co.apexsoft.fw.domain.model.book.Book
import kr.co.apexsoft.fw.domain.model.user.User

data class BookIn(
    val content: String,
    val author: String,
    val title: String,
    val userOid: Long
) {
    fun toEntity(u: User): Book {
        return Book(
            content = content,
            author = author,
            title = title,
            postUser = u
        )
    }

}
