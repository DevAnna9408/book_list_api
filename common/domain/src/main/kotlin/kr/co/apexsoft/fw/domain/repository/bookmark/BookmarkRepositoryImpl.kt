package kr.co.apexsoft.fw.domain.repository.bookmark

import com.querydsl.core.types.dsl.BooleanExpression
import kr.co.apexsoft.fw.domain.model.book.Book
import kr.co.apexsoft.fw.domain.model.bookmark.Bookmark
import kr.co.apexsoft.fw.domain.model.bookmark.QBookmark
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils

class BookmarkRepositoryImpl: QuerydslRepositorySupport(Book::class.java), BookmarkRepositoryCustom {

    private val qBookmark = QBookmark.bookmark
    override fun getBookmarkByUserOid(
        userOid: Long,
        isWritten: Boolean,
        pageable: Pageable
        ): Page<Bookmark> {

        val result = from(qBookmark)
            .where(
                qBookmark.bookmarkUser.oid.eq(userOid),
                eqIsWritten(isWritten, userOid)
            )
            .fetchAll()

        val pagedResult = querydsl!!.applyPagination(pageable, result).fetch() ?: emptyList()
        return PageableExecutionUtils.getPage(pagedResult, pageable) { result.fetchCount() }
    }

    override fun getByUserOidAndBookOid(userOid: Long, bookOid: Long): Bookmark {
        return from(qBookmark)
            .where(
                qBookmark.bookmarkUser.oid.eq(userOid),
                qBookmark.book.deleted.isFalse,
                qBookmark.book.oid.eq(bookOid)
            )
            .fetchOne()
    }

    override fun getBookOidsInBookmark(userOid: Long): List<Bookmark> {
        return from(qBookmark)
            .where(
                qBookmark.bookmarkUser.oid.eq(userOid)
            )
            .fetch()
    }

    override fun checkIsAlreadyExists(userOid: Long, bookOid: Long): Long {
        return from(qBookmark)
            .where(
                qBookmark.bookmarkUser.oid.eq(userOid),
                qBookmark.book.deleted.isFalse,
                qBookmark.book.oid.eq(bookOid)
            )
            .fetchCount()
    }

    override fun getByUserOid(userOid: Long): List<Bookmark> {
        return from(qBookmark)
            .where(
                qBookmark.bookmarkUser.oid.eq(userOid)
            )
            .fetch() ?: emptyList()
    }

    override fun getAllByOid(bookOid: Long): List<Bookmark> {
        return from(qBookmark)
            .where(
                qBookmark.book.oid.eq(bookOid),
                qBookmark.book.deleted.isFalse
            )
            .fetch()
    }

    private fun eqIsWritten(isWritten: Boolean, userOid: Long): BooleanExpression? {
        return if (!isWritten) null
        else qBookmark.book.postUser.oid.eq(userOid)
    }


}
