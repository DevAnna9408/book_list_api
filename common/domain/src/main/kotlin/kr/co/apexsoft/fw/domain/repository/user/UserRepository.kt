package kr.co.apexsoft.fw.domain.repository.user

import kr.co.apexsoft.fw.domain.model.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>, UserRepositoryCustom {
}
