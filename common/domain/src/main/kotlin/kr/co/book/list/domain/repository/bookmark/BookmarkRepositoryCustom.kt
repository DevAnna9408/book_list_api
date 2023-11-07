package kr.co.book.list.domain.repository.bookmark

import kr.co.book.list.domain.model.bookmark.Bookmark
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.Optional

interface BookmarkRepositoryCustom {
    fun getBookmarkByUserOid(
        userOid: Long,
        pageable: Pageable
        ): Page<Bookmark>

    fun getByUserOidAndBookOid(userOid: Long, bookOid: Long): Bookmark
    fun getOptionalByUserOidAndBookOid(userOid: Long, bookOid: Long): Optional<Bookmark>
    fun getBookOidsWhereBookmark(userOid: Long): List<Bookmark>
    fun getByUserOid(userOid: Long): List<Bookmark>
    fun getAllByOid(bookOid: Long): List<Bookmark>
}
