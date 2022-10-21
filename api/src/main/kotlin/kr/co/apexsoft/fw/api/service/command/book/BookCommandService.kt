package kr.co.apexsoft.fw.api.service.command.book

import kr.co.apexsoft.fw.api.dto.book.BookIn
import kr.co.apexsoft.fw.api.dto.bookmark.BookmarkIn
import kr.co.apexsoft.fw.domain.repository.book.BookRepository
import kr.co.apexsoft.fw.domain.repository.bookmark.BookmarkRepository
import kr.co.apexsoft.fw.domain.repository.user.UserRepository
import kr.co.apexsoft.fw.lib.utils.MessageUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BookCommandService (

    private val userRepository: UserRepository,
    private val bookRepository: BookRepository,
    private val bookmarkRepository: BookmarkRepository

        ) {

    fun createBook(bookIn: BookIn) {

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
}
