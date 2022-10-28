package kr.co.book.list.api.dto.book

import kr.co.book.list.domain.model.book.Book

data class BookOut(
    val bookOid: Long,
    val content: String,
    val author: String,
    val title: String,
    val thumbsUp: Int,
    val thumbsDown: Int,
    val isMarked: Boolean
) {
    companion object {
        fun fromEntity(e: Book): BookOut {
            return BookOut(
                bookOid = e.oid!!,
                content = e.content(),
                author = e.author(),
                title = e.title(),
                thumbsUp = e.thumbsUp(),
                thumbsDown = e.thumbsDown(),
                isMarked = false
            )
        }
    }
}
