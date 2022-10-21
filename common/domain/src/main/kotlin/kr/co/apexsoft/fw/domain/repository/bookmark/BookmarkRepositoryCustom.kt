package kr.co.apexsoft.fw.domain.repository.bookmark

import kr.co.apexsoft.fw.domain.model.book.Book
import kr.co.apexsoft.fw.domain.model.bookmark.Bookmark
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface BookmarkRepositoryCustom {
    fun getBookmarkByUserOid(
        userOid: Long,
        isWritten: Boolean,
        pageable: Pageable
        ): Page<Bookmark>

    fun getByUserOidAndBookOid(userOid: Long, bookOid: Long): Bookmark
    fun getBookOidsInBookmark(userOid: Long): List<Bookmark>
    fun checkIsAlreadyExists(userOid: Long, bookOid: Long): Long
    fun getByUserOid(userOid: Long): List<Bookmark>
}
