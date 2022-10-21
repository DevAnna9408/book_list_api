package kr.co.apexsoft.fw.api.service.query.book

import kr.co.apexsoft.fw.api.dto.book.BookOut
import kr.co.apexsoft.fw.api.dto.book.PostCountAndThumbsUpOut
import kr.co.apexsoft.fw.domain.repository.book.BookRepository
import kr.co.apexsoft.fw.domain.repository.bookmark.BookmarkRepository
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
        sortParam: Boolean,
        reverse: Boolean,
        sort: Sort,
        pageable: Pageable): Page<BookOut> {
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
        return bookRepository.getAllMyBookList(
            userOid,
            sortParam,
            reverse,
            sort,
            pageable
        ).map { BookOut.fromEntity(it) }
    }



    fun getPostCountAndThumbsUp(userOid: Long): PostCountAndThumbsUpOut {

        val count = bookRepository.getByUserOid(userOid).size
        val thumbs = bookRepository.getByUserOid(userOid).sumBy { it.thumbsUp() }
        val bookmark = bookmarkRepository.getByUserOid(userOid).size

        return PostCountAndThumbsUpOut(
            postCount = count,
            thumbsUp = thumbs,
            bookmark = bookmark
        )

    }

}
