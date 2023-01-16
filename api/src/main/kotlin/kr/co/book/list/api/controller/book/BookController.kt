package kr.co.book.list.api.controller.book

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.book.list.api.dto.book.BookIn
import kr.co.book.list.api.dto.book.BookOut
import kr.co.book.list.api.dto.book.PostCountAndThumbsUpOut
import kr.co.book.list.api.service.command.book.BookCommandService
import kr.co.book.list.api.service.query.book.BookQueryService
import org.springdoc.api.annotations.ParameterObject
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "책 API")
@RequestMapping("/api/book")
@RestController
class BookController (

    private val bookCommandService: BookCommandService,
    private val bookQueryService: BookQueryService

        ) {

    @Operation(summary = "책 10개이상 작성 체크")
    @GetMapping("/check")
    fun checkAlreadyPost(
        @RequestParam("userOid") userOid: Long
    ) : ResponseEntity<Nothing> {
        bookQueryService.checkAlreadyPost(userOid)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "책 작성")
    @PostMapping
    fun createBook(
        @RequestBody bookIn: BookIn
    ): ResponseEntity<Nothing> {
        bookCommandService.createBook(bookIn)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "책 삭제")
    @DeleteMapping
    fun deleteBook(
        @RequestParam("userOid") userOid: Long,
        @RequestParam("bookOid") bookOid: Long
    ): ResponseEntity<Nothing> {
        bookCommandService.deleteBook(userOid, bookOid)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "랜덤으로 책 불러오기")
    @GetMapping("/random")
    fun getRandomBook(
        @RequestParam("userOid") userOid: Long
    ): ResponseEntity<BookOut> {
        return ResponseEntity.ok(bookCommandService.getRandomBook(userOid))
    }

    @Operation(summary = "추천")
    @PutMapping("/thumbs-up")
    fun thumbsUp(
        @RequestParam ("userOid") userOid: Long,
        @RequestParam ("bookOid") bookOid: Long
    ): ResponseEntity<BookOut> {
        return ResponseEntity.ok(bookCommandService.thumbsUp(userOid, bookOid))
    }

    @Operation(summary = "비추천")
    @PutMapping("/thumbs-down")
    fun thumbsDown(
        @RequestParam ("userOid") userOid: Long,
        @RequestParam ("bookOid") bookOid: Long
    ): ResponseEntity<BookOut> {
        return ResponseEntity.ok(bookCommandService.thumbsDown(userOid, bookOid))
    }

    @Operation(summary = "오래된순 모든 책 목록 조회")
    @GetMapping("/list")
    fun getAllBookList(
        @RequestParam("userOid") userOid: Long,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<Page<BookOut>> {
        return ResponseEntity.ok(bookQueryService.getAllBookList(
            userOid,
            pageable
        ))
    }

    @Operation(summary = "최신순 모든 책 목록 조회")
    @GetMapping("/list/reverse")
    fun getAllBookListReversed(
        @RequestParam("userOid") userOid: Long,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<Page<BookOut>> {
        return ResponseEntity.ok(bookQueryService.getAllBookListReversed(
            userOid,
            pageable
        ))
    }

    @Operation(summary = "추천 많은순 모든 책 목록 조회")
    @GetMapping("/list/by-thumbs-up")
    fun getAllBookListByThumbsUp(
        @RequestParam("userOid") userOid: Long,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<Page<BookOut>> {
        return ResponseEntity.ok(bookQueryService.getAllBookListByThumbsUp(
            userOid,
            pageable
        ))
    }

    @Operation(summary = "추천 낮은순 모든 책 목록 조회")
    @GetMapping("/list/by-thumbs-down")
    fun getAllBookListByThumbsDown(
        @RequestParam("userOid") userOid: Long,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<Page<BookOut>> {
        return ResponseEntity.ok(bookQueryService.getAllBookListByThumbsDown(
            userOid,
            pageable
        ))
    }

    @Operation(summary = "내가 쓴 책 목록 조회")
    @GetMapping("/my-list")
    fun getAllMyBookList(
        @RequestParam("userOid") userOid: Long,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<Page<BookOut>> {
        return ResponseEntity.ok(bookQueryService.getAllMyBookList(
            userOid,
            pageable
        ))
    }

    @Operation(summary = "내가 쓴 글과 받은 추천 수 집계")
    @GetMapping("/post-count-and-thumb-up")
    fun getPostCountAndThumbsUp(
        @RequestParam("userOid") userOid: Long
    ): ResponseEntity<PostCountAndThumbsUpOut> {
        return ResponseEntity.ok(bookQueryService.getPostCountAndThumbsUp(userOid))
    }
}
