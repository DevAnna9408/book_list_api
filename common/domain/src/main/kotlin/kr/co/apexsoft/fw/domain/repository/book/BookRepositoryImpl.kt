package kr.co.apexsoft.fw.domain.repository.book

import kr.co.apexsoft.fw.domain.model.book.Book
import kr.co.apexsoft.fw.domain.model.book.QBook
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class BookRepositoryImpl: QuerydslRepositorySupport(Book::class.java), BookRepositoryCustom {

    private val qBook = QBook.book
    override fun getAllBookList(): List<Book> {
        return from(qBook)
            .where(
                qBook.deleted.isFalse
            )
            .fetch() ?: emptyList()
    }


}
