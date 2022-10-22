package kr.co.apexsoft.fw.domain.repository.book

import kr.co.apexsoft.fw.domain.model.book.BookThumb
import org.springframework.data.jpa.repository.JpaRepository

interface BookThumbRepository: JpaRepository<BookThumb, Long>, BookThumbRepositoryCustom {
}
