package kr.co.book.list.api.controller.admin

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.book.list.api.service.command.book.BookCommandService
import kr.co.book.list.api.service.command.user.UserCommandService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "관리자 API")
@RequestMapping("/api/admin")
@RestController
class AdminController(

    private val bookCommandService: BookCommandService,
    private val userCommandService: UserCommandService

    ) {

    @Operation(summary = "책 삭제")
    @DeleteMapping("/book")
    fun deleteBookByAdmin(
        @RequestParam("userOid") userOid: Long,
        @RequestParam("bookOid") bookOid: Long
    ): ResponseEntity<Nothing> {
        bookCommandService.deleteBookByAdmin(userOid, bookOid)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "유저 제재")
    @DeleteMapping("/user")
    fun deleteUserByAdmin(
        @RequestParam("userOid") userOid: Long
    ) : ResponseEntity<Nothing> {
        userCommandService.deleteUserByAdmin(userOid)
        return ResponseEntity.noContent().build()

    }

}
