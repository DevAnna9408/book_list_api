package kr.co.apexsoft.fw.api.controller.bookmark

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.apexsoft.fw.api.dto.bookmark.BookmarkOut
import kr.co.apexsoft.fw.api.service.command.bookmark.BookmarkCommandService
import kr.co.apexsoft.fw.api.service.query.bookmark.BookmarkQueryService
import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "책갈피 API")
@RestController
@RequestMapping("/api/bookmark")
class BookmarkController (

    private val bookmarkQueryService: BookmarkQueryService,
    private val bookmarkCommandService: BookmarkCommandService

        ) {

    @Operation(summary = "책갈피 등록")
    @PostMapping
    fun createBookmark(
        @RequestParam("userOid") userOid: Long,
        @RequestParam("bookOid") bookOid: Long
    ): ResponseEntity<Nothing> {
        bookmarkCommandService.createBookmark(userOid, bookOid)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "책갈피 삭제")
    @DeleteMapping
    fun deleteBookmark(
        @RequestParam("userOid") userOid: Long,
        @RequestParam("bookOid") bookOid: Long
    ): ResponseEntity<Nothing> {
        bookmarkCommandService.deleteBookmark(userOid, bookOid)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "내가 책갈피 한 목록 조회")
    @GetMapping
    fun getMyBookmark (
        @RequestParam("userOid") userOid: Long,
        @RequestParam("isWritten") isWritten: Boolean,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<Page<BookmarkOut>> {
        return ResponseEntity.ok(bookmarkQueryService.getMyBookmark(
            userOid,
            isWritten,
            pageable
            ))
    }

    @Operation(summary = "로그인 유저 책갈피한 책 oid 조회")
    @GetMapping("/book-oids")
    fun getBookOidsInBookmark(
        @RequestParam("userOid") userOid: Long
    ): ResponseEntity<List<Long>> {
        return ResponseEntity.ok(bookmarkQueryService.getBookOidsInBookmark(userOid))
    }

}
