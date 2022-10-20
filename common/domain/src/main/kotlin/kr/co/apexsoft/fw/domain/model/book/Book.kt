package kr.co.apexsoft.fw.domain.model.book

import kr.co.apexsoft.fw.domain._common.AbstractEntity
import kr.co.apexsoft.fw.domain.converter.BooleanToYNConverter
import kr.co.apexsoft.fw.domain.model.bookmark.Bookmark
import kr.co.apexsoft.fw.domain.model.user.User
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
    private val thumbsUp: Int = 0,

    @Column(name = "THUMBS_DOWN")
    private val thumbsDown: Int = 0,

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "DELETED")
    private val deleted: Boolean = false,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "POST_USER")
    private val postUser: User,

    @OneToMany(mappedBy = "book")
    val bookmarkList: MutableList<Bookmark> = mutableListOf()

    ) : AbstractEntity(oid) {

    fun content() = content
    fun author() = author
    fun title() = title
    fun thumbsUp() = thumbsUp
    fun thumbsDown() = thumbsDown
    fun deleted() = deleted
    fun postUser() = postUser
    fun isMarked() = bookmarkList.find { it.bookmarkUser().oid == oid }

}
