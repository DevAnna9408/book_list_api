package kr.co.apexsoft.fw.api.service.command.bookmark

import kr.co.apexsoft.fw.api.dto.bookmark.BookmarkIn
import kr.co.apexsoft.fw.domain.repository.book.BookRepository
import kr.co.apexsoft.fw.domain.repository.bookmark.BookmarkRepository
import kr.co.apexsoft.fw.domain.repository.user.UserRepository
import kr.co.apexsoft.fw.lib.utils.MessageUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BookmarkCommandService (

    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val bookmarkRepository: BookmarkRepository

        ) {

    fun createBookmark(userOid: Long, bookOid: Long) {
        val dbUser = userRepository.getByOid(userOid)
        val dbBook = bookRepository.getByOid(bookOid)

        if (bookmarkRepository.checkIsAlreadyExists(dbUser.oid!!, dbBook.oid!!) > 0) throw RuntimeException(MessageUtil.getMessage("ALREADY_EXIST"))

        try {
            bookmarkRepository.save(BookmarkIn.toEntity(dbUser, dbBook))
        } catch (
            e: RuntimeException
        ) {
            e.stackTrace.toString()
            throw RuntimeException(MessageUtil.getMessage("ERROR"))
        }
    }

    fun deleteBookmark(userOid: Long, bookOid: Long) {
        val dbBookmark = bookmarkRepository.getByUserOidAndBookOid(userOid, bookOid)

        try {
            bookmarkRepository.delete(dbBookmark!!)
        } catch (
            e: RuntimeException
        ) {
            e.stackTrace.toString()
            throw RuntimeException(MessageUtil.getMessage("ERROR_DELETE_BOOKMARK") + " " + MessageUtil.getMessage("JUST_MSG"))

        }


    }
}
