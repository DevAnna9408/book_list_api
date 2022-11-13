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
        sortParam: Boolean,
        reverse: Boolean,
        sort: Sort,
        pageable: Pageable
    ): Page<Book> {
        val result = if (reverse) {
            from(qBook)
                .where(
                    qBook.deleted.isFalse,
                    qBook.postUser.status.eq(User.Status.ACTIVE),
                    qBook.thumbsDown.lt(10)
                )
                .orderBy(
                    *sort.map {
                        OrderSpecifier(
                            if (it.isAscending) Order.ASC else Order.DESC,
                            Expressions.path(String::class.java, qBook, it.property)
                        )
                    }
                        .toList().toTypedArray()
                )
                .orderBy(
                    qBook.createdTime.desc()
                )
                .fetchAll()
        } else {
            from(qBook)
                .where(
                    qBook.deleted.isFalse,
                    qBook.postUser.status.eq(User.Status.ACTIVE),
                    qBook.thumbsDown.lt(10)
                )
                .orderBy(
                    *sort.map {
                        OrderSpecifier(
                            if (it.isAscending) Order.ASC else Order.DESC,
                            Expressions.path(String::class.java, qBook, it.property)
                        )
                    }
                        .toList().toTypedArray()
                )
                .orderBy(
                    qBook.createdTime.asc()
                )
                .fetchAll()

        }


        val pagedResult = querydsl!!.applyPagination(pageable, result).fetch() ?: emptyList()
        return PageableExecutionUtils.getPage(pagedResult, pageable) { result.fetchCount() }

    }

    override fun getAllMyBookList(
        userOid: Long,
        sortParam: Boolean,
        reverse: Boolean,
        sort: Sort,
        pageable: Pageable
    ): Page<Book> {
        val result = if (reverse) {
            from(qBook)
                .where(
                    qBook.deleted.isFalse,
                    qBook.postUser.status.eq(User.Status.ACTIVE),
                    qBook.thumbsDown.lt(10),
                    qBook.postUser.oid.eq(userOid)
                )
                .orderBy(
                    *sort.map {
                        OrderSpecifier(
                            if (it.isAscending) Order.ASC else Order.DESC,
                            Expressions.path(String::class.java, qBook, it.property)
                        )
                    }
                        .toList().toTypedArray()
                )
                .orderBy(
                    qBook.createdTime.desc()
                )
                .fetchAll()
        } else {
            from(qBook)
                .where(
                    qBook.deleted.isFalse,
                    qBook.postUser.status.eq(User.Status.ACTIVE),
                    qBook.thumbsDown.lt(10)
                )
                .orderBy(
                    *sort.map {
                        OrderSpecifier(
                            if (it.isAscending) Order.ASC else Order.DESC,
                            Expressions.path(String::class.java, qBook, it.property)
                        )
                    }
                        .toList().toTypedArray()
                )
                .orderBy(
                    qBook.createdTime.asc()
                )
                .fetchAll()

        }


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
