package moseohcorp.server.domain.dns.repository

import moseohcorp.server.domain.dns.exception.DNSNotFoundException
import moseohcorp.server.domain.dns.repository.entity.DNS
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface DNSRepository : JpaRepository<DNS, Long>

fun DNSRepository.getEntityById(id: Long): DNS =
    findById(id).orElseThrow(::DNSNotFoundException)