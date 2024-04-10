package moseohcorp.server.api.dns.dto.request

import jakarta.validation.constraints.Positive

data class PortForwardUpdateRequest(
    @field:Positive
    val port: Int
)