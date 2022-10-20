package kr.co.apexsoft.fw.domain.repository.book

import kr.co.apexsoft.fw.domain.model.book.Book
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class BookRepositoryImpl: QuerydslRepositorySupport(Book::class.java), BookRepositoryCustom {
}
