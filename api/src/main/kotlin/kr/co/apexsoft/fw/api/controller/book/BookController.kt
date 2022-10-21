package kr.co.apexsoft.fw.api.controller.book

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.apexsoft.fw.api.dto.book.BookIn
import kr.co.apexsoft.fw.api.dto.book.BookOut
import kr.co.apexsoft.fw.api.service.command.book.BookCommandService
import kr.co.apexsoft.fw.api.service.query.book.BookQueryService
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

    @Operation(summary = "모든 책 목록 조회")
    @GetMapping("/list")
    fun getAllBookList(
        @RequestParam("sortParam") sortParam: Boolean,
        @RequestParam("reverse") reverse: Boolean,
        @ParameterObject pageable: Pageable
    ): ResponseEntity<Page<BookOut>> {
        val sort = when(sortParam) {
            true -> Sort.by(Sort.Order.asc("thumbsUp"))
            else -> Sort.by(Sort.Order.asc("thumbsDown"))
        }
        return ResponseEntity.ok(bookQueryService.getAllBookList(
            sortParam,
            reverse,
            sort,
            pageable
        ))
    }
}
