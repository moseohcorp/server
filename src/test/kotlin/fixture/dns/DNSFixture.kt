package fixture.dns

import moseohcorp.server.api.dns.dto.request.DNSCreateRequest
import moseohcorp.server.api.dns.dto.request.DNSUpdateRequest
import moseohcorp.server.domain.dns.repository.entity.DNS

object DNSFixture {
    fun dns(
        domain: String = "domain",
        ipAddress: String = "123.123.123.123",
        adminEmail: String = "azqazq195@gmail.com",
    ) = DNS(
        domain = domain,
        ipAddress = ipAddress,
        adminEmail = adminEmail
    )

    fun dnsCreateRequest(
        domain: String = "domain",
        ipAddress: String = "123.123.123.123",
        adminEmail: String = "azqazq195@gmail.com",
    ) = DNSCreateRequest(
        domain = domain,
        ipAddress = ipAddress,
        adminEmail = adminEmail
    )

    fun dnsUpdateRequest(
        domain: String = "domain",
        ipAddress: String = "123.123.123.123",
        adminEmail: String = "azqazq195@gmail.com",
    ) = DNSUpdateRequest(
        domain = domain,
        ipAddress = ipAddress,
        adminEmail = adminEmail
    )
}