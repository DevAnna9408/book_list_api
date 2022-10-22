package kr.co.apexsoft.fw.api.service.command.book

import kr.co.apexsoft.fw.api.dto.book.BookIn
import kr.co.apexsoft.fw.api.dto.book.BookOut
import kr.co.apexsoft.fw.api.dto.book.BookThumbIn
import kr.co.apexsoft.fw.api.dto.bookmark.BookmarkIn
import kr.co.apexsoft.fw.domain.repository.book.BookRepository
import kr.co.apexsoft.fw.domain.repository.book.BookThumbRepository
import kr.co.apexsoft.fw.domain.repository.bookmark.BookmarkRepository
import kr.co.apexsoft.fw.domain.repository.user.UserRepository
import kr.co.apexsoft.fw.lib.security.SecurityUtil
import kr.co.apexsoft.fw.lib.utils.MessageUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BookCommandService (

    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val bookmarkRepository: BookmarkRepository,
    private val bookThumbRepository: BookThumbRepository

        ) {

    fun createBook(bookIn: BookIn) {
        SecurityUtil.checkUserOid(bookIn.userOid)
        if (bookIn.content.isBlank()) throw RuntimeException(MessageUtil.getMessage("CONTENT_IS_NULL_OR_BLANK"))
        if (bookIn.author.isBlank()) throw RuntimeException(MessageUtil.getMessage("AUTHOR_IS_NULL_OR_BLANK"))
        if (bookIn.title.isBlank()) throw RuntimeException(MessageUtil.getMessage("TITLE_IS_NULL_OR_BLANK"))

        val dbUser = userRepository.getByOid(bookIn.userOid)

        val book = try {
            bookRepository.save(bookIn.toEntity(dbUser))
        } catch (e: RuntimeException) {
            e.stackTrace.toString()
            throw RuntimeException(MessageUtil.getMessage("ERROR"))
        }
        bookmarkRepository.save(BookmarkIn.toEntity(dbUser, book))
    }

    fun thumbsUp(userOid: Long, bookOid: Long) {
        SecurityUtil.checkUserOid(userOid)
        val dbUser = userRepository.getByOid(userOid)
        val dbBook = bookRepository.getByOid(bookOid)

        if (bookThumbRepository.getByUserOidAndUp(userOid, bookOid) > 0) {
            throw RuntimeException(MessageUtil.getMessage("ALREADY_UP"))
        } else {
            try {
                dbBook._thumbsUp()
                bookThumbRepository.save(BookThumbIn(true).toEntity(
                    dbUser, dbBook
                ))
            } catch (
                e: RuntimeException
            ) {
                e.stackTrace.toString()
                throw RuntimeException(MessageUtil.getMessage("ERROR"))
            }
        }
    }

    fun thumbsDown(userOid: Long, bookOid: Long) {
        SecurityUtil.checkUserOid(userOid)
        val dbUser = userRepository.getByOid(userOid)
        val dbBook = bookRepository.getByOid(bookOid)
        if (bookThumbRepository.getByUserOidAndDown(userOid, bookOid) > 0) {
            throw RuntimeException(MessageUtil.getMessage("ALREADY_DOWN"))
        } else {
            try {
                dbBook._thumbsDown()
                bookThumbRepository.save(BookThumbIn(false).toEntity(
                    dbUser, dbBook
                ))
            } catch (
                e: RuntimeException
            ) {
                e.stackTrace.toString()
                throw RuntimeException(MessageUtil.getMessage("ERROR"))
            }
        }
    }

    fun deleteBook(userOid: Long, bookOid: Long) {
        SecurityUtil.checkUserOid(userOid)
        if (bookRepository.checkByUserOidAndBookOid(userOid, bookOid) < 1) throw RuntimeException(MessageUtil.getMessage("NOT_MINE"))

        val dbBook = bookRepository.getByOid(bookOid)
        val dbBookmarks = bookmarkRepository.getAllByOid(bookOid)
        val dbBookThumbs = bookThumbRepository.getAllByBookOid(bookOid)

        try {
            bookThumbRepository.deleteAll(dbBookThumbs)
            bookmarkRepository.deleteAll(dbBookmarks)
            bookRepository.delete(dbBook)
        } catch (
            e: RuntimeException
        ) {
            e.stackTrace.toString()
            throw RuntimeException(MessageUtil.getMessage("ERROR"))
        }

    }

    fun getRandomBook(userOid: Long): BookOut {
        SecurityUtil.checkUserOid(userOid)
        val dbBooks = bookRepository.findAll()
            .filter { !it.deleted() }
            .filter { it.thumbsUp() >= 10 }
            .map { it.oid }
        val dbBook = bookRepository.getByOid(dbBooks.random()!!)
        return BookOut.fromEntity(dbBook)
    }
}
