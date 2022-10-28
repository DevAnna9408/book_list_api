package kr.co.book.list.domain.repository.book

import kr.co.book.list.domain.model.book.Book
import org.springframework.data.jpa.repository.JpaRepository

interface BookRepository: JpaRepository<Book, Long>, BookRepositoryCustom {
}
