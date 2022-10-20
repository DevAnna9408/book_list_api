package kr.co.apexsoft.fw.domain.model.bookmark

import kr.co.apexsoft.fw.domain._common.AbstractEntity
import kr.co.apexsoft.fw.domain.model.book.Book
import kr.co.apexsoft.fw.domain.model.user.User
import javax.persistence.*

@Entity
@Table(name = "B_BOOKMARK")
class Bookmark (

    oid: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOKMARK_USER")
    private val bookmarkUser: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOOK")
    private val book: Book

        ) : AbstractEntity(oid){

    fun bookmarkUser() = bookmarkUser
    fun book() = book
}
