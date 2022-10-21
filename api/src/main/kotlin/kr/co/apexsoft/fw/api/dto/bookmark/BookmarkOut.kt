package kr.co.apexsoft.fw.api.dto.bookmark

import kr.co.apexsoft.fw.domain.model.bookmark.Bookmark

data class BookmarkOut(
    val bookOid: Long,
    val content: String,
    val author: String,
    val title: String,
    val thumbsUp: Int,
    val thumbsDown: Int,
) {
    companion object {
        fun fromEntity(e: Bookmark): BookmarkOut {
            return BookmarkOut(
                bookOid = e.book().oid!!,
                content = e.book().content(),
                author = e.book().author(),
                title = e.book().title(),
                thumbsUp = e.book().thumbsUp(),
                thumbsDown = e.book().thumbsDown(),
            )
        }
    }
}
