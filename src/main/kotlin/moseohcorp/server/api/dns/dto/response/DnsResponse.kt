package moseohcorp.server.api.dns.dto.response

data class DnsResponse(
    val domain: String,
    val ipAddress: String,
    val adminEmail: String,
    val serial: String,
    val refresh: Int,
    val retry: Int,
    val expire: Int,
    val minimum: Int,
)