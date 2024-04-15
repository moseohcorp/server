package moseohcorp.server.api.dns.repository

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
class DomainQueryRepository {

}