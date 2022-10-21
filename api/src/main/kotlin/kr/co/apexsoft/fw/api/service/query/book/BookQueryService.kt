package kr.co.apexsoft.fw.api.service.query.book

import kr.co.apexsoft.fw.api.dto.book.BookOut
import kr.co.apexsoft.fw.domain.repository.book.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class BookQueryService(

    private val bookRepository: BookRepository

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

}
