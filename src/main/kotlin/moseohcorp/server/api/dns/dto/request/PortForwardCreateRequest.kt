package moseohcorp.server.api.dns.dto.request

import jakarta.validation.constraints.Positive

data class PortForwardCreateRequest(
    @field:Positive
    val port: Int
)