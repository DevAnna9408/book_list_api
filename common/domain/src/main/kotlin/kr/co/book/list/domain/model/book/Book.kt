package kr.co.book.list.domain.model.book

import kr.co.book.list.domain._common.AbstractEntity
import kr.co.book.list.domain.converter.BooleanToYNConverter
import kr.co.book.list.domain.model.bookmark.Bookmark
import kr.co.book.list.domain.model.user.User
import kr.co.book.list.domain.model.user.UserSirenHistory
import javax.persistence.*

@Entity
@Table(name = "B_BOOK")
class Book (

    oid: Long? = null,

    @Column(name = "CONTENT")
    private val content: String,

    @Column(name = "AUTHOR")
    private val author: String,

    @Column(name = "TITLE")
    private val title: String,

    @Column(name = "THUMBS_UP")
    private var thumbsUp: Int = 0,

    @Column(name = "THUMBS_DOWN")
    private var thumbsDown: Int = 0,

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "DELETED")
    private var deleted: Boolean = false,

    @Column(name = "SIREN_COUNT")
    private var sirenCount: Int = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_USER")
    private val postUser: User,

    @OneToMany(mappedBy = "thumbBook", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val bookThumbs:MutableList<BookThumb> = mutableListOf(),

    @OneToMany(mappedBy = "book", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val bookMarks: MutableList<Bookmark> = mutableListOf(),

    @OneToMany(mappedBy = "sirenedBook", cascade = [CascadeType.ALL], orphanRemoval = true)
    private val sirenedBooks: MutableList<UserSirenHistory> = mutableListOf()

    ) : AbstractEntity(oid) {

    fun content() = content
    fun author() = author
    fun title() = title
    fun thumbsUp() = thumbsUp
    fun thumbsDown() = thumbsDown
    fun deleted() = deleted
    fun postUser() = postUser
    fun sirenCount() = sirenCount

    fun sirenBook() {
        this.sirenCount += 1
        if (this.sirenCount >= 10) deleteBook()
    }

    fun _thumbsUp() {
        this.thumbsUp += 1
    }

    fun _thumbsDown() {
        this.thumbsDown += 1
        if (this.thumbsDown == 10) this.deleted = true
    }

    fun deleteBook() {
        this.deleted = true
    }

}
