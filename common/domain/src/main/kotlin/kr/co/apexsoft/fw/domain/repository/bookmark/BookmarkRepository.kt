package kr.co.apexsoft.fw.domain.repository.bookmark

import kr.co.apexsoft.fw.domain.model.book.Book
import kr.co.apexsoft.fw.domain.model.bookmark.Bookmark
import org.springframework.data.jpa.repository.JpaRepository

interface BookmarkRepository: JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {
}
