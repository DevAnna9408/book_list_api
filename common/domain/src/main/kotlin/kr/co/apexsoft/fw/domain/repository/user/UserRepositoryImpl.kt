package kr.co.apexsoft.fw.domain.repository.user

import com.querydsl.core.types.dsl.BooleanExpression
import kr.co.apexsoft.fw.domain._common.DomainMessageUtil
import kr.co.apexsoft.fw.domain._common.containsName
import kr.co.apexsoft.fw.domain.exception.DomainEntityNotFoundException
import kr.co.apexsoft.fw.domain.model.user.User
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.data.support.PageableExecutionUtils
import java.util.*

class UserRepositoryImpl : QuerydslRepositorySupport(User::class.java), UserRepositoryCustom {

    private val qUser = kr.co.apexsoft.fw.domain.model.user.QUser.user

    override fun getByOid(oid: Long): User {
        return from(qUser)
            .where(
                isActive(),
                qUser.oid.eq(oid)
            )
            .fetchOne() ?: throw DomainEntityNotFoundException(oid, User::class, DomainMessageUtil.getMessage("USER_NOT_FOUND"))
    }

    override fun getByUserId(userId: String): User {
        return from(qUser)
            .where(
                isActive(),
                qUser.userId.eq(userId)
            )
            .fetchOne() ?: throw DomainEntityNotFoundException(userId, User::class, DomainMessageUtil.getMessage("USER_NOT_FOUND"))
    }

    override fun searchUsers(
        name: String?,
        pageable: Pageable
    ): Page<User> {
        val result = from(qUser)
            .where(
                isActive(),
            )
            .fetchAll()
        val pagedResult = querydsl!!.applyPagination(pageable, result).fetch() ?: emptyList()
        return PageableExecutionUtils.getPage(pagedResult, pageable) { result.fetchCount() }
    }

    override fun findByUserId(userId: String): Optional<User> {
        return Optional.ofNullable(from(qUser)
            .where(
                isActive(),
                qUser.userId.eq(userId),
            )
            .fetchOne())
    }

    private fun isActive(): BooleanExpression? {
        return  qUser.status.eq(User.Status.ACTIVE)
    }
}
