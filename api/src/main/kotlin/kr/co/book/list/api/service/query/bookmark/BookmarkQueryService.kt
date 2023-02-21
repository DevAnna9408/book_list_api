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

    fun getMyBookmarksByPage(
        userOid: Long,
        pageable: Pageable
        ): Page<BookmarkOut> {
        SecurityUtil.checkUserOid(userOid)
        return bookmarkRepository.getBookmarkByUserOid(
            userOid,
            pageable
        ).map { BookmarkOut.fromEntity(it) }
    }

    fun getBookOidsWhereBookmark(userOid: Long): List<Long> {
        SecurityUtil.checkUserOid(userOid)
        return bookmarkRepository.getBookOidsWhereBookmark(userOid).map { it.book().oid!! }
    }
}
