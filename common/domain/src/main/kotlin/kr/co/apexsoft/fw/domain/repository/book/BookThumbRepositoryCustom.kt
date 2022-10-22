package kr.co.apexsoft.fw.domain.repository.book

import kr.co.apexsoft.fw.domain.model.book.BookThumb

interface BookThumbRepositoryCustom {

    fun getByUserOidAndUp(userOid: Long, bookOid: Long): Long
    fun getByUserOidAndDown(userOid: Long, bookOid: Long): Long
    fun getAllByBookOid(bookOid: Long): List<BookThumb>

}
