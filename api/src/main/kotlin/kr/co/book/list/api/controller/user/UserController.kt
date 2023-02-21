package kr.co.book.list.api.controller.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.book.list.api.dto.user.UserSimpleOut
import kr.co.book.list.api.service.command.user.UserCommandService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 관리 API", description = "회원정보가 저장되었거나, 인증/인가 후")
@RequestMapping("/api/users")
@RestController
class UserController(
    private val userCommandService: UserCommandService,
) {

    @Operation(summary = "회원 탈퇴")
    @DeleteMapping("/{userOid}")
    fun deleteUser(
        @PathVariable("userOid") userOid: Long
    ): ResponseEntity<Nothing> {
        userCommandService.deleteUser(userOid)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "사용자 닉네임 수정")
    @PatchMapping("/nick-name/{userOid}")
    fun updateUserId(
        @PathVariable("userOid") userOid: Long,
        @RequestParam("nickName") nickName: String?
    ): ResponseEntity<UserSimpleOut> {
        return ResponseEntity.ok(userCommandService.updateNickName(userOid, nickName))
    }

}
