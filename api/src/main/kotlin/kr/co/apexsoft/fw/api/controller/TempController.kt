package kr.co.apexsoft.fw.api.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "임시-controller", description = "프레임워크 권한 테스트를 위한 컨트롤러 ")
@RestController
class TempController() {

    @Operation(summary = "admin 시큐리티 확인")
    @GetMapping("/api/admin")
    fun admin(): ResponseEntity<String> {
        return ResponseEntity.ok("admin 시큐리티 확인")
    }

    @Operation(summary = "admin 개인정보 접속 인터셉터확인_쿼리")
    @GetMapping("/api/admin/users/all")
    fun adminLog(): ResponseEntity<String> {
        return ResponseEntity.ok("admin 개인정보 접속 인터셉터확인")
    }

    @Operation(summary = "admin 개인정보 접속 인터셉터확인_다운")
    @GetMapping("/api/admin/down/excel")
    fun down(): ResponseEntity<String> {
        return ResponseEntity.ok("admin 개인정보 접속 인터셉터확인_다운")
    }


}
