package kr.co.book.list.domain.repository.book

import kr.co.book.list.domain.model.book.BookThumb

interface BookThumbRepositoryCustom {

    fun getByUserOidAndUp(userOid: Long, bookOid: Long): Long
    fun getByUserOidAndDown(userOid: Long, bookOid: Long): Long
    fun getAllByBookOid(bookOid: Long): List<BookThumb>

}
