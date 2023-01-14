package kr.co.book.list.domain.repository.book

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.Expressions
import kr.co.book.list.domain.model.book.Book
import kr.co.book.list.domain.model.book.QBook
import kr.co.book.list.domain.model.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils
import java.time.LocalDate

class BookRepositoryImpl: QuerydslRepositorySupport(Book::class.java), BookRepositoryCustom {

    private val qBook = QBook.book
    override fun getAllBookList(
        pageable: Pageable
    ): Page<Book> {
        val result = from(qBook)
                .where(
                    qBook.deleted.isFalse,
                    qBook.postUser.status.eq(User.Status.ACTIVE),
                    qBook.thumbsDown.lt(10)
                )
                .orderBy(
                    qBook.oid.asc()
                )
                .fetchAll()

        val pagedResult = querydsl!!.applyPagination(pageable, result).fetch() ?: emptyList()
        return PageableExecutionUtils.getPage(pagedResult, pageable) { result.fetchCount() }

    }

    override fun getAllBookListReversed(pageable: Pageable): Page<Book> {
        val result = from(qBook)
            .where(
                qBook.deleted.isFalse,
                qBook.postUser.status.eq(User.Status.ACTIVE),
                qBook.thumbsDown.lt(10)
            )
            .orderBy(
                qBook.oid.desc()
            )
            .fetchAll()

        val pagedResult = querydsl!!.applyPagination(pageable, result).fetch() ?: emptyList()
        return PageableExecutionUtils.getPage(pagedResult, pageable) { result.fetchCount() }
    }

    override fun getAllBookListByThumbsUp(pageable: Pageable): Page<Book> {
        val result = from(qBook)
            .where(
                qBook.deleted.isFalse,
                qBook.postUser.status.eq(User.Status.ACTIVE),
                qBook.thumbsDown.lt(10)
            )
            .orderBy(
                qBook.thumbsUp.desc()
            )
            .fetchAll()

        val pagedResult = querydsl!!.applyPagination(pageable, result).fetch() ?: emptyList()
        return PageableExecutionUtils.getPage(pagedResult, pageable) { result.fetchCount() }
    }

    override fun getAllBookListByThumbsDown(pageable: Pageable): Page<Book> {
        val result = from(qBook)
            .where(
                qBook.deleted.isFalse,
                qBook.postUser.status.eq(User.Status.ACTIVE),
                qBook.thumbsDown.lt(10)
            )
            .orderBy(
                qBook.thumbsUp.asc()
            )
            .fetchAll()

        val pagedResult = querydsl!!.applyPagination(pageable, result).fetch() ?: emptyList()
        return PageableExecutionUtils.getPage(pagedResult, pageable) { result.fetchCount() }
    }

    override fun getAllMyBookList(
        userOid: Long,
        pageable: Pageable
    ): Page<Book> {
        val result = from(qBook)
                .where(
                    qBook.deleted.isFalse,
                    qBook.postUser.status.eq(User.Status.ACTIVE),
                    qBook.thumbsDown.lt(10),
                    qBook.postUser.oid.eq(userOid)
                )
                .orderBy(
                    qBook.createdTime.desc()
                )
                .fetchAll()

        val pagedResult = querydsl!!.applyPagination(pageable, result).fetch() ?: emptyList()
        return PageableExecutionUtils.getPage(pagedResult, pageable) { result.fetchCount() }
    }

    override fun getByOid(bookOid: Long): Book {
        return from(qBook)
            .where(
                qBook.deleted.isFalse,
                qBook.postUser.status.eq(User.Status.ACTIVE),
                qBook.oid.eq(bookOid)
            )
            .fetchOne()
    }

    override fun getListByUserOid(userOid: Long): List<Book> {
        return from(qBook)
            .where(
                qBook.deleted.isFalse,
                qBook.postUser.status.eq(User.Status.ACTIVE),
                qBook.postUser.oid.eq(userOid)
            )
            .fetch() ?: emptyList()
    }

    override fun checkByUserOidAndBookOid(userOid: Long, bookOid: Long): Long {
        return from(qBook)
            .where(
                qBook.deleted.isFalse,
                qBook.postUser.status.eq(User.Status.ACTIVE),
                qBook.postUser.oid.eq(userOid),
                qBook.oid.eq(bookOid)
            )
            .fetchCount()
    }

    override fun checkAlreadyPost(userOid: Long): Long {
        return from(qBook)
            .where(
                qBook.postUser.oid.eq(userOid),
                qBook.postUser.status.eq(User.Status.ACTIVE),
                qBook.deleted.isFalse,
                qBook.createdTime.eq(LocalDate.now())
            )
            .fetchCount()
    }


}
