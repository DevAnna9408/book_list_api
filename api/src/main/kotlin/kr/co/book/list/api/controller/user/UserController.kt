package kr.co.book.list.api.controller.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.book.list.api.dto.user.UserSimpleOut
import kr.co.book.list.api.dto.user.UserUpdateIn
import kr.co.book.list.api.service.command.user.UserCommandService
import kr.co.book.list.lib.security.SecurityUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 관리 API")
@RequestMapping("/api/users")
@RestController
class UserController(
    private val userCommandService: UserCommandService,
) {

    @Operation(summary = "회원탈퇴")
    @DeleteMapping("/{userOid}")
    fun deleteUser(
        @PathVariable("userOid") userOid: Long
    ): ResponseEntity<Nothing> {
        SecurityUtil.checkUserOid(userOid)
        userCommandService.deleteUser(userOid)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "사용자 닉네임 수정")
    @PatchMapping("/nick-name/{userOid}")
    fun updateUserId(
        @PathVariable("userOid") userOid: Long,
        @RequestParam("nickName") nickName: String?
    ): ResponseEntity<UserSimpleOut> {
        SecurityUtil.checkUserOid(userOid)
        return ResponseEntity.ok(userCommandService.updateNickName(userOid, nickName))
    }

}
