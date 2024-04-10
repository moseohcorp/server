package moseohcorp.server.api.dns

import jakarta.validation.Valid
import moseohcorp.server._common.dto.ApiResponse
import moseohcorp.server.api.dns.dto.request.*
import moseohcorp.server.domain.dns.BindService
import moseohcorp.server.domain.dns.DNSService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/bind")
class BindController(
    private val bindService: BindService,
) {
    @PostMapping("")
    fun createBind(
        @RequestBody @Valid request: BindCreateRequest
    ): ResponseEntity<ApiResponse> {
        bindService.create(request)
        return ApiResponse.of(status = HttpStatus.CREATED)
    }

    @PutMapping("/{bindId}")
    fun updateBind(
        @PathVariable bindId: Long,
        @RequestBody @Valid request: BindUpdateRequest,
    ): ResponseEntity<ApiResponse> {
        bindService.update(bindId, request)
        return ApiResponse.of(status = HttpStatus.OK)
    }

    @DeleteMapping("/{bindId}")
    fun deleteBind(
        @PathVariable bindId: Long,
    ): ResponseEntity<ApiResponse> {
        bindService.delete(bindId)
        return ApiResponse.of(status = HttpStatus.OK)
    }

    @PostMapping("/{bindId}/port-forward")
    fun addPortForward(
        @PathVariable bindId: Long,
        @RequestBody @Valid request: PortForwardCreateRequest,
    ): ResponseEntity<ApiResponse> {
        bindService.addPortForward(bindId, request)
        return ApiResponse.of(status = HttpStatus.CREATED)
    }

    @PutMapping("/{bindId}/port-forward")
    fun updatePortForward(
        @PathVariable bindId: Long,
        @RequestBody @Valid request: PortForwardUpdateRequest,
    ): ResponseEntity<ApiResponse> {
        bindService.updatePortForward(bindId, request)
        return ApiResponse.of(status = HttpStatus.OK)
    }

    @DeleteMapping("/{bindId}/port-forward")
    fun removePortForward(
        @PathVariable bindId: Long,
        ): ResponseEntity<ApiResponse> {
        bindService.removePortForward(bindId)
        return ApiResponse.of(status = HttpStatus.OK)
    }
}