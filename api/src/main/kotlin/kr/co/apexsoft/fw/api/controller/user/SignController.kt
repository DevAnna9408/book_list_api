package kr.co.apexsoft.fw.api.controller.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.apexsoft.fw.api.dto.user.*
import kr.co.apexsoft.fw.api.service.command.user.UserCommandService
import kr.co.apexsoft.fw.api.service.command.user.UserLoginService
import kr.co.apexsoft.fw.domain._common.EnumMapper
import kr.co.apexsoft.fw.domain._common.EnumValue
import kr.co.apexsoft.fw.lib.error.InvalidException
import kr.co.apexsoft.fw.lib.security.SecurityUtil
import kr.co.apexsoft.fw.lib.utils.MessageUtil
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "회원 관리 API")
@RequestMapping("/api")
@RestController
class SignController(
    private val userCommandService: UserCommandService,
    private val userLoginService: UserLoginService,
    private val enumMapper: EnumMapper,
) {
    @Operation(summary = "회원 가입")
    @PostMapping("/sign-up")
    fun createMember(@RequestBody signUpIn: SignUpIn): ResponseEntity<UserSimpleOut> {
        val memberOut = userCommandService.createUser(signUpIn)
        return ResponseEntity.ok(memberOut)
    }

    @Operation(summary = "로그인")
    @PostMapping("/sign-in")
    fun login(@Valid @RequestBody signIn: SignInIn, bindingResult: BindingResult): ResponseEntity<SignInOut> {
        if (bindingResult.hasErrors()) throw InvalidException(MessageUtil.getMessage("INVALID_USER_INFO"), bindingResult)
        return ResponseEntity.ok(userLoginService.login(signIn))
    }


    @Operation(summary = "캡챠 성공 후처리")
    @GetMapping("/captcha/success")
    fun resetPasswordCnt(@RequestParam("userId") userId: String): ResponseEntity<UserOut>? {
        return ResponseEntity.ok(userCommandService.unlockUser(userId))
    }

    @Operation(summary = "비밀번호 찾기")
    @GetMapping("/find/password")
    fun findPassword(@RequestParam userId: String) {
        userCommandService.findPassword(userId)
    }

    @Operation(summary = "초기 비밀번호 변경")
    @PatchMapping("/{oid}/change-init-password")
    fun changeInitPassword(
        @PathVariable("oid") oid: Long,
        @RequestBody passwordIn: PasswordIn
    ): ResponseEntity<Nothing> {
        SecurityUtil.checkUserOid(oid)
        userCommandService.changePassword(oid, passwordIn)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "비밀번호 찾기 이후 비밀번호 재설정")
    @PatchMapping("/{userId}/change-password-after-find")
    fun changePasswordAfterFind(
        @PathVariable("userId") userId: String,
        @RequestBody passwordIn: PasswordIn
    ): ResponseEntity<Nothing> {
        userCommandService.changePasswordAfterFind(userId, passwordIn)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "역할 목록 조회")
    @GetMapping("/roles")
    fun getRoles(): ResponseEntity<Map<String, List<EnumValue>?>> {
        return ResponseEntity.ok(enumMapper["ROLE"])
        // return ResponseEntity.ok(Role.values().map { EnumValue(it) })
    }


//    @Operation(summary = "이메일, 아이디 중복체크")
//    @GetMapping("/sign-up/check")
//    fun isUse(
//        @RequestParam type: String,
//        @RequestParam value: String
//    ): ResponseEntity<Boolean?>? {
//        return ResponseEntity.ok(this.userCommandService.isUse(type, value))
//    }
}
