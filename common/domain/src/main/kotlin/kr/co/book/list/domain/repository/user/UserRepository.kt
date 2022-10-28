package kr.co.book.list.domain.repository.user

import kr.co.book.list.domain.model.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {
}
