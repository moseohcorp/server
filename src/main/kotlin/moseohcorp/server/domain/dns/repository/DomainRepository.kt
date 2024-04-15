package moseohcorp.server.domain.dns.repository

import moseohcorp.server.domain.dns.exception.DomainNotFoundException
import moseohcorp.server.domain.dns.repository.entity.Domain
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface DomainRepository : JpaRepository<Domain, Long>

fun DomainRepository.getEntityById(id: Long): Domain =
    findById(id).orElseThrow(::DomainNotFoundException)