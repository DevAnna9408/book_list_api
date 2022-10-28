package kr.co.book.list.domain.repository.book

import kr.co.book.list.domain.model.book.BookThumb
import org.springframework.data.jpa.repository.JpaRepository

interface BookThumbRepository: JpaRepository<BookThumb, Long>, BookThumbRepositoryCustom {
}
