package kr.co.apexsoft.fw.domain.repository.book

import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.Expressions
import kr.co.apexsoft.fw.domain.model.book.Book
import kr.co.apexsoft.fw.domain.model.book.QBook
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils

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
                qBook.oid.eq(bookOid)
            )
            .fetchOne()
    }


}
