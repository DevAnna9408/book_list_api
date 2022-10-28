package kr.co.book.list.domain.repository.book

import kr.co.book.list.domain.model.book.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface BookRepositoryCustom {
    fun getAllBookList(
        sortParam: Boolean,
        reverse: Boolean,
        sort: Sort,
        pageable: Pageable): Page<Book>
    fun getAllMyBookList(
        userOid: Long,
        sortParam: Boolean,
        reverse: Boolean,
        sort: Sort,
        pageable: Pageable): Page<Book>
    fun getByOid(bookOid: Long): Book
    fun getListByUserOid(userOid: Long): List<Book>
    fun checkByUserOidAndBookOid(userOid: Long, bookOid: Long): Long
}
