package moseohcorp.server.api.dns.dto.response

data class DomainResponse(
    val name: String,
    val ipAddress: String,
    val adminEmail: String,
    val serial: String,
    val refresh: Int,
    val retry: Int,
    val expire: Int,
    val minimum: Int,
)