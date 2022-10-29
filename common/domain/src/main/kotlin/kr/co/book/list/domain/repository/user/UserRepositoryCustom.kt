package kr.co.book.list.domain.repository.user

import kr.co.book.list.domain.model.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface UserRepositoryCustom {

    fun getByOid(oid: Long): User
    fun getByUserId(userId: String) : User
    fun searchUsers(
        name: String?,
        pageable: Pageable
    ): Page<User>
    fun findByUserId(userId: String): Optional<User>
    fun getByUserIdAndNickName(userId: String, nickName: String): User

}
