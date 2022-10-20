package kr.co.apexsoft.fw.api.service.query.book

import kr.co.apexsoft.fw.api.dto.book.BookOut
import kr.co.apexsoft.fw.domain.repository.book.BookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BookQueryService(

    private val bookRepository: BookRepository

) {

    fun getAllBookList(): List<BookOut> {
        return bookRepository.getAllBookList().map { BookOut.fromEntity(it) }
    }

}
