package kr.co.book.list.lib.security

import kr.co.book.list.domain.model.Role
import kr.co.book.list.lib.utils.MessageUtil
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.core.context.SecurityContextHolder

object SecurityUtil {

    private fun authUser(): AuthUser {
        return SecurityContextHolder.getContext().authentication.principal as? AuthUser
            ?: throw RuntimeException(MessageUtil.getMessage("UNAUTHENTICATED_USER"))
    }


    fun checkUserOid(userOid: Long) {
        val authUser = authUser()
        if (authUser.isFreePass()) return
        if (authUser.userOid != userOid) {
            throw AccessDeniedException(MessageUtil.getMessage("UNAUTHORIZED_ACCESS"))
        }
    }

    fun checkManagerRole() {
        val authUser = authUser()
        if (authUser.isFreePass()) return
        if (!authUser.roles.any { it.getCode() == Role.ROLE_MANAGER.getCode() }) {
            throw AccessDeniedException(MessageUtil.getMessage("ROLE_NOT_FOUND"))
        }

    }

    fun currentUserId(): String {
        val authUser = authUser()
        return authUser.userId
    }

}
