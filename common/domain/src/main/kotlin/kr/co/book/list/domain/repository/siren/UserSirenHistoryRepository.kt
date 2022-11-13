package kr.co.book.list.domain.repository.siren

import kr.co.book.list.domain.model.user.UserSirenHistory
import org.springframework.data.jpa.repository.JpaRepository

interface UserSirenHistoryRepository: JpaRepository<UserSirenHistory, Long>, UserSirenHistoryRepositoryCustom {
}
