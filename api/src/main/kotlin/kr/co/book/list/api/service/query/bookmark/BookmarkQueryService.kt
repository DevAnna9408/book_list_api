package kr.co.book.list.api.service.query.bookmark

import kr.co.book.list.api.dto.bookmark.BookmarkOut
import kr.co.book.list.domain.repository.bookmark.BookmarkRepository
import kr.co.book.list.lib.security.SecurityUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BookmarkQueryService (

    private val bookmarkRepository: BookmarkRepository

        ) {

    fun getMyBookmark(
        userOid: Long,
        isWritten: Boolean,
        pageable: Pageable
        ): Page<BookmarkOut> {
        SecurityUtil.checkUserOid(userOid)
        return bookmarkRepository.getBookmarkByUserOid(
            userOid,
            isWritten,
            pageable
        ).map { BookmarkOut.fromEntity(it) }
    }

    fun getBookOidsInBookmark(userOid: Long): List<Long> {
        SecurityUtil.checkUserOid(userOid)
        return bookmarkRepository.getBookOidsInBookmark(userOid).map { it.book().oid!! }
    }
}