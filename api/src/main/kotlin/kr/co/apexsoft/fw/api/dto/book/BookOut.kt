package kr.co.apexsoft.fw.api.dto.book

import kr.co.apexsoft.fw.domain.model.book.Book

data class BookOut(
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
                content = e.content(),
                author = e.author(),
                title = e.title(),
                thumbsUp = e.thumbsUp(),
                thumbsDown = e.thumbsDown(),
                isMarked = when(e.isMarked()) {
                    null -> false
                    else -> true
                }
            )
        }
    }
}
