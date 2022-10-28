package kr.co.book.list.api.service.command.user

import kr.co.book.list.api.dto.user.*
import kr.co.book.list.domain.model.user.User
import kr.co.book.list.domain.repository.user.UserRepository
import kr.co.book.list.lib.security.SecurityUtil
import kr.co.book.list.lib.security.jwt.JwtGenerator
import kr.co.book.list.lib.utils.MessageUtil
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
@Transactional
class UserCommandService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
 /*   @Autowired
    private var resourceLoader: ResourceLoader,*/

    @org.springframework.beans.factory.annotation .Value("\${front.url}")
    private val frontUrl: String,

//    private val mailService: MailService,
) {
    fun createUser(signUpIn: SignUpIn): UserSimpleOut {
        val user: User = try {
            userRepository.save(signUpIn.toEntity(passwordEncoder))
        } catch (e: DataIntegrityViolationException) {
            // index 이름 및 DataIntegrityViolationException 구현에 의존하므로 좋은 방식은 아니지만,
            // userId, email 각각에 대해 쿼리를 2번 날릴 필요가 없는 장점이 있고, 핵심 비즈 로직도 아니므로 편리하게 적용
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
            throw RuntimeException(MessageUtil.getMessage("DELETE_USER") + " " + MessageUtil.getMessage("JUST_MSG"))
        }
    }


//    fun changePasswordAfterFind(userId: String, passwordIn: PasswordIn) {
//        val dbUser = userRepository.getByUserId(userId)
//        dbUser.changePassword(passwordEncoder.encode(passwordIn.newPassword))
//    }

    /**
     * 비밀번호 찾기
     */
//    fun findPassword(userId: String) {
//        val user = userRepository.getByUserId(userId)
//       /* sendEmail(user.userId, user.email())*/
//    }


    /**
     * 메일로 비밀번호 찾기 링크 전송
     */
/*    private fun sendEmail(userId: String, email: String) {
        val dto =  MailDto(java.util.List.of(email), "비밀번호 찾기 안내 메일입니다. This is Your password.",
                "find-password.html",Map.of("url", getModifyPwdUrl(userId, email)), resourceLoader)

        mailService.send(dto)
    }*/

//    private fun getModifyPwdUrl(userId: String, email: String): String {
//        val expireDate = LocalDateTime.now().plusMinutes(30).format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
//        return frontUrl + "/user/modify/pwd/after/find/" + userId + "/" + expireDate + "/" + getHash(userId, email)
//    }

    /**
     * 이메일 hash
     */
//    private fun getHash(userId: String, email: String): String {
//        //return HashUtil.generateHash("$userId:$email")
//        return "TODO: "
//    }

}
