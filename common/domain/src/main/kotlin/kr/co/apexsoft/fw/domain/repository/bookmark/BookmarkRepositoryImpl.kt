package kr.co.apexsoft.fw.domain.repository.bookmark

import kr.co.apexsoft.fw.domain.model.book.Book
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class BookmarkRepositoryImpl: QuerydslRepositorySupport(Book::class.java), BookmarkRepositoryCustom {
}
