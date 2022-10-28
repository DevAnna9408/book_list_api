package kr.co.book.list.domain.repository.bookmark

import kr.co.book.list.domain.model.book.Book
import kr.co.book.list.domain.model.bookmark.Bookmark
import org.springframework.data.jpa.repository.JpaRepository

interface BookmarkRepository: JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {
}
