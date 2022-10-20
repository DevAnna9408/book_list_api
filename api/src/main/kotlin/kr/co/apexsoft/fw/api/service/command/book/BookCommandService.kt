package kr.co.apexsoft.fw.api.service.command.book

import kr.co.apexsoft.fw.api.dto.book.BookIn
import kr.co.apexsoft.fw.domain.repository.book.BookRepository
import kr.co.apexsoft.fw.domain.repository.user.UserRepository
import kr.co.apexsoft.fw.lib.utils.MessageUtil
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class BookCommandService (

    private val userRepository: UserRepository,
    private val bookRepository: BookRepository

        ) {

    fun createBook(bookIn: BookIn) {

        val dbUser = userRepository.getByOid(bookIn.userOid)
        try {
            bookRepository.save(bookIn.toEntity(dbUser))
        } catch (e: RuntimeException) {
            e.stackTrace.toString()
            throw RuntimeException(MessageUtil.getMessage("ERROR"))
        }
    }
}
