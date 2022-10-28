package kr.co.book.list.api.dto.bookmark

import kr.co.book.list.domain.model.book.Book
import kr.co.book.list.domain.model.bookmark.Bookmark
import kr.co.book.list.domain.model.user.User

object BookmarkIn {
    fun toEntity(u: User, b: Book): Bookmark {
        return Bookmark(
            bookmarkUser = u,
            book = b
        )
    }

}
