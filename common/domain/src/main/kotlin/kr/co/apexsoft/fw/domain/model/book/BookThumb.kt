package kr.co.apexsoft.fw.domain.model.book

import kr.co.apexsoft.fw.domain._common.AbstractEntity
import kr.co.apexsoft.fw.domain.converter.BooleanToYNConverter
import kr.co.apexsoft.fw.domain.model.user.User
import javax.persistence.*

@Entity
@Table(name = "B_BOOK_THUMB")
class BookThumb (

    oid: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "THUMB_USER")
    private val thumbUser: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "THUMB_BOOK")
    private val thumbBook: Book,

    @Convert(converter = BooleanToYNConverter::class)
    @Column(name = "THUMB_YN")
    private val thumbYn: Boolean

    ): AbstractEntity (oid){

    fun thumbUser() = thumbUser
    fun thumbBook() = thumbBook
    fun thumbYn() = thumbYn

}
