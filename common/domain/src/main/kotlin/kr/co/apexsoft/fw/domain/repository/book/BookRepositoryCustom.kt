package kr.co.apexsoft.fw.domain.repository.book

import kr.co.apexsoft.fw.domain.model.book.Book

interface BookRepositoryCustom {
    fun getAllBookList(): List<Book>
}
