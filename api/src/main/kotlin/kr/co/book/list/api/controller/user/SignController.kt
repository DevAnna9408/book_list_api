package kr.co.book.list.api.controller.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.book.list.api.dto.user.*
import kr.co.book.list.api.service.command.user.UserCommandService
import kr.co.book.list.api.service.command.user.UserLoginService
import kr.co.book.list.domain._common.EnumMapper
import kr.co.book.list.domain._common.EnumValue
import kr.co.book.list.lib.error.InvalidException
import kr.co.book.list.lib.security.SecurityUtil
import kr.co.book.list.lib.utils.MessageUtil
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

    @Operation(summary = "역할 목록 조회")
    @GetMapping("/roles")
    fun getRoles(): ResponseEntity<Map<String, List<EnumValue>?>> {
        return ResponseEntity.ok(enumMapper["ROLE"])
        // return ResponseEntity.ok(Role.values().map { EnumValue(it) })
    }

    @Operation(summary = "비밀번호 질문 찾기")
    @GetMapping("/find-password")
    fun findQuestion(
        @RequestParam("userId") userId: String,
        @RequestParam("nickName") nickName: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok(userCommandService.findQuestion(userId, nickName))
    }

    @Operation(summary = "비밀번호 질문에 대한 답변")
    @PostMapping("/answer-password")
    fun answerPassword(
        @RequestParam("userId") userId: String,
        @RequestParam("nickName") nickName: String,
        @RequestParam("answer") answer: String
    ): ResponseEntity<Boolean> {
        return ResponseEntity.ok(userCommandService.answerPassword(userId, nickName, answer))
    }

    @Operation(summary = "비밀번호 재설정")
    @PutMapping("/change-password/{userId}")
    fun changePassword(
        @PathVariable("userId") userId: String,
        @RequestBody passwordIn: PasswordIn
    ) : ResponseEntity<Nothing> {
        userCommandService.changePasswordAfterFind(userId, passwordIn)
        return ResponseEntity.noContent().build()
    }

//
//    @Operation(summary = "비밀번호 찾기 이후 비밀번호 재설정")
//    @PatchMapping("/{userId}/change-password-after-find")
//    fun changePasswordAfterFind(
//        @PathVariable("userId") userId: String,
//        @RequestBody passwordIn: PasswordIn
//    ): ResponseEntity<Nothing> {
//        userCommandService.changePasswordAfterFind(userId, passwordIn)
//        return ResponseEntity.noContent().build()
//    }



}
