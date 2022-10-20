package kr.co.apexsoft.fw.api.service.command.user

import kr.co.apexsoft.fw.api.dto.user.SignInIn
import kr.co.apexsoft.fw.api.dto.user.SignInOut
import kr.co.apexsoft.fw.domain.model.user.User
import kr.co.apexsoft.fw.domain.repository.user.UserRepository
import kr.co.apexsoft.fw.lib.error.UnauthenticatedAccessException
import kr.co.apexsoft.fw.lib.security.SignInUser
import kr.co.apexsoft.fw.lib.security.jwt.JwtGenerator
import kr.co.apexsoft.fw.lib.utils.MessageUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.*
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class UserLoginService(
    private val authManager: AuthenticationManager,
    private val jwtGenerator: JwtGenerator,
    private val userRepository: UserRepository,
    @Value("\${login.failMaxCnt}")
    private val failMaxCnt: Int

    ) {
    //비민번호 틀렸을 경우는 Update 가 이뤄나야 하므로 예외처리
    @Transactional(noRollbackFor = [BadCredentialsException::class])
    fun login(signIn: SignInIn): SignInOut {
        val authenticate: Authentication = try {
            authManager.authenticate(UsernamePasswordAuthenticationToken(signIn.userId, signIn.password))
        } catch (e: InternalAuthenticationServiceException) { // 존재하지 않는 사용자
            throw  InternalAuthenticationServiceException(MessageUtil.getMessage("USER_NOT_FOUND"))
        } catch (e: DisabledException) {  // 유효한 회원이 아님
            throw  DisabledException(MessageUtil.getMessage("LOGIN_FAIL"))
        } catch (e: BadCredentialsException) {
            this.failPassword(signIn.userId)
            throw  BadCredentialsException(MessageUtil.getMessage("INCORRECT_PASSWORD"))
        } catch (e: LockedException) {    // 계정 잠김
            throw  LockedException(MessageUtil.getMessage("ADDITIONAL_AUTH"))
        } catch (e: UnauthenticatedAccessException) {
            throw  UnauthenticatedAccessException();
        }
        val signInUser = authenticate.principal as SignInUser
        return SignInOut.from(signInUser, jwtGenerator.generateUserToken(signInUser))
    }

    fun failPassword(userId:String) {
        val dbUser = userRepository.getByUserId(userId)
        dbUser.checkLock(failMaxCnt)
    }
}
