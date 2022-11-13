package kr.co.book.list.domain.model.user

import kr.co.book.list.domain._common.AbstractEntity
import kr.co.book.list.domain.model.book.Book
import java.time.LocalDate
import javax.persistence.*

@Entity
@Table(name = "B_USER_SIREN_HISTORY")
class UserSirenHistory(

    oid: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SITEN_USER")
    private val sirenUser: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SIRENED_USER")
    private val sirenedUser: User,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SIRENED_BOOK")
    private val sirenedBook: Book,

    @Column(name = "REASON")
    private val reason: String,

    @Column(name = "SIREN_DATE")
    private val sirenDate: LocalDate = LocalDate.now()

) : AbstractEntity(oid) {

    fun sirenUser() = sirenUser
    fun sirenedUser() = sirenedUser
    fun sirenedBook() = sirenedBook
    fun reason() = reason
    fun sirenDate() = sirenDate

}
