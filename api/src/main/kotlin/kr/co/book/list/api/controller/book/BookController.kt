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
    ): ResponseEntity<Nothing> {
        bookCommandService.thumbsUp(userOid, bookOid)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "비추천")
    @PutMapping("/thumbs-down")
    fun thumbsDown(
        @RequestParam ("userOid") userOid: Long,
        @RequestParam ("bookOid") bookOid: Long
    ): ResponseEntity<Nothing> {
        bookCommandService.thumbsDown(userOid, bookOid)
        return ResponseEntity.noContent().build()
    }

    @Operation(summary = "모든 책 목록 조회")
    @GetMapping("/list")
    fun getAllBookList(
        @RequestParam("userOid") userOid: Long,
        @RequestParam("sortParam") sortParam: Boolean,
        @RequestParam("reverse") reverse: Boolean,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<Page<BookOut>> {
        val sort = when(sortParam) {
            true -> Sort.by(Sort.Order.asc("thumbsUp"))
            else -> Sort.by(Sort.Order.asc("thumbsDown"))
        }
        return ResponseEntity.ok(bookQueryService.getAllBookList(
            userOid,
            sortParam,
            reverse,
            sort,
            pageable
        ))
    }

    @Operation(summary = "내가 쓴 책 목록 조회")
    @GetMapping("/my-list")
    fun getAllMyBookList(
        @RequestParam("userOid") userOid: Long,
        @RequestParam("sortParam") sortParam: Boolean,
        @RequestParam("reverse") reverse: Boolean,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<Page<BookOut>> {
        val sort = when(sortParam) {
            true -> Sort.by(Sort.Order.asc("thumbsUp"))
            else -> Sort.by(Sort.Order.asc("thumbsDown"))
        }
        return ResponseEntity.ok(bookQueryService.getAllMyBookList(
            userOid,
            sortParam,
            reverse,
            sort,
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