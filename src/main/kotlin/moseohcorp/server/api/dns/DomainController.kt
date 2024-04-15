package moseohcorp.server.api.dns

import jakarta.validation.Valid
import moseohcorp.server._common.dto.ApiResponse
import moseohcorp.server.api.dns.dto.request.*
import moseohcorp.server.domain.dns.DomainService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/domain")
class DomainController(
    private val domainService: DomainService,
) {
    @PostMapping("")
    fun create(
        @RequestBody @Valid request: DomainCreateRequest
    ): ResponseEntity<ApiResponse> {
        domainService.create(request)
        return ApiResponse.of(status = HttpStatus.CREATED)
    }

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid request: DomainUpdateRequest
    ): ResponseEntity<ApiResponse> {
        domainService.update(id, request)
        return ApiResponse.of(status = HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun delete(
        @PathVariable id: Long
    ): ResponseEntity<ApiResponse> {
        domainService.delete(id)
        return ApiResponse.of(status = HttpStatus.OK)
    }

    @PostMapping("/{dnsId}/record")
    fun createRecord(
        @PathVariable dnsId: Long,
        @RequestBody @Valid request: RecordCreateRequest
    ): ResponseEntity<ApiResponse> {
        domainService.addRecord(dnsId, request)
        return ApiResponse.of(status = HttpStatus.CREATED)
    }

    @PutMapping("/{dnsId}/record/{recordId}")
    fun updateRecord(
        @PathVariable dnsId: Long,
        @PathVariable recordId: Long,
        @RequestBody @Valid request: RecordUpdateRequest
    ): ResponseEntity<ApiResponse> {
        domainService.updateRecord(recordId, request)
        return ApiResponse.of(status = HttpStatus.OK)
    }

    @DeleteMapping("/{dnsId}/record/{recordId}")
    fun deleteRecord(
        @PathVariable dnsId: Long,
        @PathVariable recordId: Long,
    ): ResponseEntity<ApiResponse> {
        domainService.removeRecord(recordId)
        return ApiResponse.of(status = HttpStatus.OK)
    }

    @PostMapping("/{dnsId}/record/{recordId}/port-forward")
    fun addPortForward(
        @PathVariable dnsId: Long,
        @PathVariable recordId: Long,
        @RequestBody @Valid request: PortForwardCreateRequest,
    ): ResponseEntity<ApiResponse> {
        domainService.addPortForward(recordId, request)
        return ApiResponse.of(status = HttpStatus.CREATED)
    }

    @PutMapping("/{dnsId}/record/{recordId}/port-forward")
    fun updatePortForward(
        @PathVariable dnsId: Long,
        @PathVariable recordId: Long,
        @RequestBody @Valid request: PortForwardUpdateRequest,
    ): ResponseEntity<ApiResponse> {
        domainService.updatePortForward(recordId, request)
        return ApiResponse.of(status = HttpStatus.OK)
    }

    @DeleteMapping("/{dnsId}/record/{recordId}/port-forward")
    fun removePortForward(
        @PathVariable dnsId: Long,
        @PathVariable recordId: Long,
    ): ResponseEntity<ApiResponse> {
        domainService.removePortForward(recordId)
        return ApiResponse.of(status = HttpStatus.OK)
    }
}