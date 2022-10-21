package kr.co.apexsoft.fw.api.dto.bookmark

import kr.co.apexsoft.fw.domain.model.book.Book
import kr.co.apexsoft.fw.domain.model.bookmark.Bookmark
import kr.co.apexsoft.fw.domain.model.user.User

object BookmarkIn {
    fun toEntity(u: User, b: Book): Bookmark {
        return Bookmark(
            bookmarkUser = u,
            book = b
        )
    }

}
