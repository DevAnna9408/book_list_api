package kr.co.book.list.domain.model.bookmark

import kr.co.book.list.domain._common.AbstractEntity
import kr.co.book.list.domain.model.book.Book
import kr.co.book.list.domain.model.user.User
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
