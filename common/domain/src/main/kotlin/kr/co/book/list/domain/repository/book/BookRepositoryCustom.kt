package kr.co.book.list.domain.repository.book

import kr.co.book.list.domain.model.book.Book
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

interface BookRepositoryCustom {
    fun getAllBookList(
        pageable: Pageable): Page<Book>
    fun getAllBookListReversed(
        pageable: Pageable): Page<Book>
    fun getAllBookListByThumbsUp(
        pageable: Pageable): Page<Book>
    fun getAllBookListByThumbsDown(
        pageable: Pageable): Page<Book>
    fun getAllMyBookList(
        userOid: Long,
        pageable: Pageable): Page<Book>
    fun getByOid(bookOid: Long): Book
    fun getListByUserOid(userOid: Long): List<Book>
    fun checkByUserOidAndBookOid(userOid: Long, bookOid: Long): Long
    fun checkAlreadyPost(userOid: Long): Long
}
