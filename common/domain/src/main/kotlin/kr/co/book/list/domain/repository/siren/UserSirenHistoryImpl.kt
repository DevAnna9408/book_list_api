package kr.co.book.list.domain.repository.siren

import kr.co.book.list.domain.model.user.QUserSirenHistory
import kr.co.book.list.domain.model.user.UserSirenHistory
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import java.time.LocalDate

class UserSirenHistoryImpl: QuerydslRepositorySupport(UserSirenHistory::class.java), UserSirenHistoryRepositoryCustom {

    private val qUserSirenHistory = QUserSirenHistory.userSirenHistory
    override fun findSirenCountByUserOid(userOid: Long): Long {
        return from(qUserSirenHistory)
            .where(
                qUserSirenHistory.sirenUser.oid.eq(userOid),
                qUserSirenHistory.sirenDate.eq(LocalDate.now())
            )
            .fetchCount()
    }

}
