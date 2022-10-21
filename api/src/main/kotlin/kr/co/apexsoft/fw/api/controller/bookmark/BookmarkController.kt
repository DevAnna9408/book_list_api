package kr.co.apexsoft.fw.api.controller.bookmark

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.apexsoft.fw.api.service.command.bookmark.BookmarkCommandService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Tag(name = "책갈피 API")
@RestController
@RequestMapping("/api/bookmark")
class BookmarkController (

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

}
