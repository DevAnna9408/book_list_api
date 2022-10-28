package kr.co.book.list.domain.repository.book

import kr.co.book.list.domain.model.book.BookThumb
import kr.co.book.list.domain.model.book.QBookThumb
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport

class BookThumbRepositoryImpl: QuerydslRepositorySupport(BookThumb::class.java), BookThumbRepositoryCustom {

    private val qBookThumb = QBookThumb.bookThumb
    override fun getByUserOidAndUp(userOid: Long, bookOid: Long): Long {
        return from(qBookThumb)
            .where(
                qBookThumb.thumbUser.oid.eq(userOid),
                qBookThumb.thumbBook.oid.eq(bookOid),
                qBookThumb.thumbYn.isTrue
            )
            .fetchCount()
    }

    override fun getByUserOidAndDown(userOid: Long, bookOid: Long): Long {
        return from(qBookThumb)
            .where(
                qBookThumb.thumbUser.oid.eq(userOid),
                qBookThumb.thumbBook.oid.eq(bookOid),
                qBookThumb.thumbYn.isFalse
            )
            .fetchCount()
    }

    override fun getAllByBookOid(bookOid: Long): List<BookThumb> {
        return from(qBookThumb)
            .where(qBookThumb.thumbBook.oid.eq(bookOid))
            .fetch()
    }

}
