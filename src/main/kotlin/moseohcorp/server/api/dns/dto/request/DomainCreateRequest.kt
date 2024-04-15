package moseohcorp.server.api.dns.dto.request

import jakarta.validation.constraints.NotBlank

data class DomainCreateRequest(
    @field:NotBlank
    val name: String,

    @field:NotBlank
    val ipAddress: String,

    @field:NotBlank
    val adminEmail: String,
)