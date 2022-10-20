package kr.co.apexsoft.fw.api.controller.user

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.apexsoft.fw.api.dto.user.UserSimpleOut
import kr.co.apexsoft.fw.api.dto.user.UserUpdateIn
import kr.co.apexsoft.fw.api.service.command.user.UserCommandService
import kr.co.apexsoft.fw.lib.security.SecurityUtil
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 관리 API")
@RequestMapping("/api/users")
@RestController
class UserController(
    private val userCommandService: UserCommandService,
) {

    @Operation(summary = "비밀번호 재설정")
    @PatchMapping("/{oid}/reset-password")
    fun resetPassword(@PathVariable("oid") oid: Long): ResponseEntity<String> {
        SecurityUtil.checkUserOid(oid)
        return ResponseEntity.ok(userCommandService.resetPassword(oid))
    }

//    @Operation(summary = "회원 정보 수정")
//    @PutMapping("/{oid}")
//    fun save(
//        @PathVariable("oid") oid: Long,
//        @RequestBody userUpdateIn: UserUpdateIn
//    ): ResponseEntity<UserSimpleOut> {
//        SecurityUtil.checkUserOid(oid)
//        return ResponseEntity.ok(userCommandService.saveUser(oid, userUpdateIn))
//    }

}
