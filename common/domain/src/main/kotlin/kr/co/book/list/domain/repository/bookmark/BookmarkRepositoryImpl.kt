package kr.co.book.list.domain.repository.bookmark

import kr.co.book.list.domain.model.book.Book
import kr.co.book.list.domain.model.bookmark.Bookmark
import kr.co.book.list.domain.model.bookmark.QBookmark
import kr.co.book.list.domain.model.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils
import java.util.*

class BookmarkRepositoryImpl: QuerydslRepositorySupport(Book::class.java), BookmarkRepositoryCustom {

    private val qBookmark = QBookmark.bookmark
    override fun getBookmarkByUserOid(
        userOid: Long,
        pageable: Pageable
        ): Page<Bookmark> {

        val result = from(qBookmark)
            .where(
                qBookmark.bookmarkUser.oid.eq(userOid),
                qBookmark.bookmarkUser.status.eq(User.Status.ACTIVE),
                qBookmark.book.deleted.isFalse
            )
            .fetchAll()

        val pagedResult = querydsl!!.applyPagination(pageable, result).fetch() ?: emptyList()
        return PageableExecutionUtils.getPage(pagedResult, pageable) { result.fetchCount() }
    }

    override fun getByUserOidAndBookOid(userOid: Long, bookOid: Long): Bookmark {
        return from(qBookmark)
            .where(
                qBookmark.bookmarkUser.oid.eq(userOid),
                qBookmark.bookmarkUser.status.eq(User.Status.ACTIVE),
                qBookmark.book.deleted.isFalse,
                qBookmark.book.oid.eq(bookOid)
            )
            .fetchOne()
    }

    override fun getOptionalByUserOidAndBookOid(userOid: Long, bookOid: Long): Optional<Bookmark> {
        return Optional.ofNullable(
                from(qBookmark)
                        .where(
                                qBookmark.bookmarkUser.oid.eq(userOid),
                                qBookmark.bookmarkUser.status.eq(User.Status.ACTIVE),
                                qBookmark.book.deleted.isFalse,
                                qBookmark.book.oid.eq(bookOid)
                        )
                        .fetchOne()
        )
    }

    override fun getBookOidsWhereBookmark(userOid: Long): List<Bookmark> {
        return from(qBookmark)
            .where(
                qBookmark.book.deleted.isFalse,
                qBookmark.bookmarkUser.status.eq(User.Status.ACTIVE),
                qBookmark.bookmarkUser.oid.eq(userOid)
            )
            .fetch()
    }

    override fun getByUserOid(userOid: Long): List<Bookmark> {
        return from(qBookmark)
            .where(
                qBookmark.book.deleted.isFalse,
                qBookmark.bookmarkUser.status.eq(User.Status.ACTIVE),
                qBookmark.bookmarkUser.oid.eq(userOid)
            )
            .fetch() ?: emptyList()
    }

    override fun getAllByOid(bookOid: Long): List<Bookmark> {
        return from(qBookmark)
            .where(
                qBookmark.book.oid.eq(bookOid),
                qBookmark.bookmarkUser.status.eq(User.Status.ACTIVE),
                qBookmark.book.deleted.isFalse
            )
            .fetch()
    }
}
