package kr.co.book.list.api.service.query.book

import kr.co.book.list.api.dto.book.BookOut
import kr.co.book.list.api.dto.book.PostCountAndThumbsUpOut
import kr.co.book.list.domain.repository.book.BookRepository
import kr.co.book.list.domain.repository.bookmark.BookmarkRepository
import kr.co.book.list.lib.security.SecurityUtil
import kr.co.book.list.lib.utils.MessageUtil
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

    fun checkAlreadyPost(userOid: Long) {
        if (bookRepository.checkAlreadyPost(userOid) >= 10) throw RuntimeException(MessageUtil.getMessage("ALREADY_POST"))
    }

    fun getAllBookList(
        userOid: Long,
        pageable: Pageable): Page<BookOut> {
        SecurityUtil.checkUserOid(userOid)
        return bookRepository.getAllBookList(
            pageable
        ).map { BookOut.fromEntity(it) }
    }

    fun getAllBookListReversed(
        userOid: Long,
        pageable: Pageable): Page<BookOut> {
        SecurityUtil.checkUserOid(userOid)
        return bookRepository.getAllBookListReversed(
            pageable
        ).map { BookOut.fromEntity(it) }
    }

    fun getAllBookListByThumbsUp(
        userOid: Long,
        pageable: Pageable): Page<BookOut> {
        SecurityUtil.checkUserOid(userOid)
        return bookRepository.getAllBookListByThumbsUp(
            pageable
        ).map { BookOut.fromEntity(it) }
    }

    fun getAllBookListByThumbsDown(
        userOid: Long,
        pageable: Pageable): Page<BookOut> {
        SecurityUtil.checkUserOid(userOid)
        return bookRepository.getAllBookListByThumbsDown(
            pageable
        ).map { BookOut.fromEntity(it) }

    }

    fun getAllMyBookList(
        userOid: Long,
        pageable: Pageable): Page<BookOut> {
        SecurityUtil.checkUserOid(userOid)
        return bookRepository.getAllMyBookList(
            userOid,
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
