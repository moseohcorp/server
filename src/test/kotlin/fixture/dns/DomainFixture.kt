package fixture.dns

import moseohcorp.server.api.dns.dto.request.DomainCreateRequest
import moseohcorp.server.api.dns.dto.request.DomainUpdateRequest
import moseohcorp.server.domain.dns.repository.entity.Domain

object DomainFixture {
    fun domain(
        name: String = "domain",
        ipAddress: String = "123.123.123.123",
        adminEmail: String = "azqazq195@gmail.com",
    ) = Domain(
        name = name,
        ipAddress = ipAddress,
        adminEmail = adminEmail
    )

    fun domainCreateRequest(
        name: String = "domain",
        ipAddress: String = "123.123.123.123",
        adminEmail: String = "azqazq195@gmail.com",
    ) = DomainCreateRequest(
        name = name,
        ipAddress = ipAddress,
        adminEmail = adminEmail
    )

    fun domainUpdateRequest(
        domain: String = "domain",
        ipAddress: String = "123.123.123.123",
        adminEmail: String = "azqazq195@gmail.com",
    ) = DomainUpdateRequest(
        name = domain,
        ipAddress = ipAddress,
        adminEmail = adminEmail
    )
}