package kr.co.book.list.api.service.command.user

import kr.co.book.list.api.dto.user.*
import kr.co.book.list.domain.model.LockReason
import kr.co.book.list.domain.model.user.User
import kr.co.book.list.domain.repository.book.BookRepository
import kr.co.book.list.domain.repository.user.UserRepository
import kr.co.book.list.lib.security.SecurityUtil
import kr.co.book.list.lib.utils.MessageUtil
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class UserCommandService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val bookRepository: BookRepository

) {
    fun createUser(signUpIn: SignUpIn): UserSimpleOut {
        val user: User = try {
            userRepository.save(signUpIn.toEntity(passwordEncoder))
        } catch (e: DataIntegrityViolationException) {

            /**
             * index 이름 및 DataIntegrityViolationException 구현에 의존하므로 좋은 방식은 아니지만,
             * userId, email 각각에 대해 쿼리를 2번 날릴 필요가 없는 장점이 있고, 핵심 비즈 로직도 아니므로 편리하게 적용
             **/

            val defaultMsg = "계정을 생성할 수 없습니다. 관리자에게 문의해주세요."
            val msg = e.message ?: throw RuntimeException(defaultMsg)
            when {
                msg.contains("USER_ID") -> throw IllegalArgumentException("이미 사용 중인 사용자 아이디 입니다.")
                else -> throw RuntimeException(defaultMsg)
            }
        }
        return UserSimpleOut.fromEntity(user)
    }

    fun updateNickName(userOid: Long, nickName: String?): UserSimpleOut? {
        SecurityUtil.checkUserOid(userOid)
        val dbUser = userRepository.getByOid(userOid)
        if (nickName!!.length > 20) throw RuntimeException("프로필명은 20자 이하로 작성해 주세요 :)")

        try {
            dbUser.updateUserNickName(nickName)
        } catch (
            e: RuntimeException
        ) {
            e.stackTrace.toString()
            throw RuntimeException(MessageUtil.getMessage("ERROR"))
        }

        return UserSimpleOut.fromEntity(userRepository.getByOid(userOid))

    }

    fun deleteUser(userOid: Long) {
        SecurityUtil.checkUserOid(userOid)
        val dbUser = userRepository.getByOid(userOid)
        try {
            dbUser.deleteUser()
        } catch (
            e: RuntimeException
        ) {
            e.stackTrace.toString()
            throw RuntimeException(MessageUtil.getMessage("DELETE_USER") + " " + MessageUtil.getMessage("ASK_FOR_DEVELOPER"))
        }
    }

    /**
     * 비밀번호 찾기 등
     */
    fun findQuestion(userId: String, nickName: String): String? {
        return userRepository.getByUserIdAndNickName(userId, nickName)?.question()
    }

    fun answerPassword(userId: String, nickName: String, answer: String): Boolean {
        val dbUser = userRepository.getByUserIdAndNickName(userId, nickName)
        return dbUser?.answer() == answer.replace(" ", "")
    }

    fun changePasswordAfterFind(userId: String, passwordIn: PasswordIn) {
        val dbUser = userRepository.getByUserId(userId)
        dbUser.changePassword(passwordEncoder.encode(passwordIn.newPassword))
        dbUser.reset()
    }

    fun updateUserLockCount(userOid: Long) {
        val dbUser = userRepository.getByOid(userOid)
        if (dbUser.updateLockCount() >= 3) {
            lockUser(dbUser, LockReason.ACCUMULATED_REPORTS)
        }
    }

    fun lockUser(user: User, reason: LockReason) {
        user.lockReason = reason
        user.lockUser()
        val bookList = bookRepository.getListByUserOid(user.oid!!)
        bookRepository.deleteAll(bookList)
    }

    fun deleteUserByAdmin(userOid: Long) {
        SecurityUtil.checkManagerRole()
        val dbUser = userRepository.getByOid(userOid)
        lockUser(dbUser, LockReason.ACCUMULATED_REPORTS)
    }

}
