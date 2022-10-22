package kr.co.apexsoft.fw.api.service.query.book

import kr.co.apexsoft.fw.api.dto.book.BookOut
import kr.co.apexsoft.fw.api.dto.book.PostCountAndThumbsUpOut
import kr.co.apexsoft.fw.domain.repository.book.BookRepository
import kr.co.apexsoft.fw.domain.repository.bookmark.BookmarkRepository
import kr.co.apexsoft.fw.lib.security.SecurityUtil
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BookQueryService(

    private val bookRepository: BookRepository,
    private val bookmarkRepository: BookmarkRepository

) {

    fun getAllBookList(
        userOid: Long,
        sortParam: Boolean,
        reverse: Boolean,
        sort: Sort,
        pageable: Pageable): Page<BookOut> {
        SecurityUtil.checkUserOid(userOid)
        return bookRepository.getAllBookList(
            sortParam,
            reverse,
            sort,
            pageable
        ).map { BookOut.fromEntity(it) }
    }

    fun getAllMyBookList(
        userOid: Long,
        sortParam: Boolean,
        reverse: Boolean,
        sort: Sort,
        pageable: Pageable): Page<BookOut> {
        SecurityUtil.checkUserOid(userOid)
        return bookRepository.getAllMyBookList(
            userOid,
            sortParam,
            reverse,
            sort,
            pageable
        ).map { BookOut.fromEntity(it) }
    }



    fun getPostCountAndThumbsUp(userOid: Long): PostCountAndThumbsUpOut {
        SecurityUtil.checkUserOid(userOid)
        val count = bookRepository.getListByUserOid(userOid).size
        val thumbs = bookRepository.getListByUserOid(userOid).sumBy { it.thumbsUp() }
        val bookmark = bookmarkRepository.getByUserOid(userOid).size

        return PostCountAndThumbsUpOut(
            postCount = count,
            thumbsUp = thumbs,
            bookmark = bookmark
        )

    }

}
