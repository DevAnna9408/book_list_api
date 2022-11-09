package kr.co.book.list.domain.model.user

import kr.co.book.list.domain._common.AbstractEntity
import kr.co.book.list.domain._common.EnumModel
import kr.co.book.list.domain.converter.RoleEnumToListConvert
import kr.co.book.list.domain.model.LockReason
import kr.co.book.list.domain.model.Role
import javax.persistence.*

/**
 * 회원
 */
@Entity
@Table(
    name = "B_USER",
    indexes = [
        Index(name = "IDX_USER__USER_ID", columnList = "USER_ID", unique = true),
    ]
)
class User(
    oid: Long? = null, //pk

    @Column(name = "USER_ID")
    val userId: String, //수정불가

    @Column(name = "nickName")
    private var nickName: String,

    @Column(name = "PASSWORD")
    private var password: String,

    @Column(name = "ROLES")
    @Convert(converter = RoleEnumToListConvert::class)
    private var roles: List<Role>,

    @Column(name = "QUESTION")
    private val question: String,

    @Column(name = "ANSWER")
    private val answer: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private var status: Status = Status.ACTIVE, // 사용자 상태 코드

    @Column(name = "FAIL_CNT")
    var failCnt: Int = 0,// 로그인 실패횟수

    @Column(name = "LOCK_COUNT")
    private var lockCount: Int = 0,// 사용자 신고 or 관리자 규제에 의한 잠금카운트

    @Enumerated(EnumType.STRING)
    @Column(name = "LOCK_REASON")
    var lockReason: LockReason? = null,

    @Column(name = "LOCK_YN")
    private var locked: Boolean = false//계정잠김

) : AbstractEntity(oid) {

    enum class Status(private val korName: String, private val engName: String) : EnumModel {

        ACTIVE("활성", "ACTIVE"),
        WITHDRAW("탈퇴", "WITHDRAW"),
        INACTIVE("비활성", "INACTIVE");

        override fun getKorName(): String {
            return korName
        }

        override fun getEngName(): String {
            return engName
        }

        override fun getCode(): String {
            return name
        }
    }

    fun changePassword(password: String) {
        this.password = password
    }


    fun updateWith(n: NewValue) {
        if (!n.password.isNullOrBlank()) this.password = n.password
        this.status = n.status
        this.failCnt = n.failCnt
        this.locked = n.locked
        this.roles = n.roles
    }

    fun deleteUser() {
        this.status = Status.WITHDRAW
    }

    fun lockUser () {
        this.locked = true
        this.lockCount = 3
//        this.status = Status.INACTIVE
    }


    data class NewValue(
        val password: String? = null,
        val roles: MutableList<Role> = mutableListOf(Role.ROLE_USER),
        var status: Status = Status.ACTIVE,
        val failCnt: Int = 0,
        val locked: Boolean = false
    )

    fun reset() {
        this.status = Status.ACTIVE
        this.failCnt = 0
        this.locked = false
    }

    fun updateLockCount(): Int {
        this.lockCount += 1
        return this.lockCount
    }

    fun answer() = answer
    fun question()  =question
    fun nickName() = nickName
    fun password() = password
    fun status() = status
    fun failCnt() = failCnt
    fun locked() = locked
    fun role() = roles
    fun lockReason() = lockReason
    fun checkActiveUser(): Boolean {
        return status().equals(Status.ACTIVE)
    }

    fun unlock() {
        this.failCnt = 0
        this.locked = false
    }

    // 캡챠
    fun checkLock (failMaxCnt: Int)  {
        this.failCnt += 1
        if (this.failCnt > failMaxCnt) {
            this.locked = true
        }
    }

    fun updateUserNickName(nickName: String?) {
        if (!nickName.isNullOrBlank()) this.nickName = nickName
    }
}
