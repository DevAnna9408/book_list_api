package kr.co.book.list.api.controller.siren

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kr.co.book.list.api.dto.siren.SirenIn
import kr.co.book.list.api.service.command.siren.SirenCommandService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/siren")
@Tag(name = "사용자 신고 API")
class SirenController(

    private val sirenCommandService: SirenCommandService

) {

    @Operation(summary = "사용자 신고")
    @PostMapping
    fun createSiren(
        @RequestBody sirenIn: SirenIn
    ): ResponseEntity<Nothing>{
        sirenCommandService.createSiren(sirenIn)
        return ResponseEntity.noContent().build()
    }

}
