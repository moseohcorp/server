package moseohcorp.server.domain.dns.repository

import moseohcorp.server.domain.dns.exception.RecordNotFoundException
import moseohcorp.server.domain.dns.repository.entity.Record
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecordRepository : JpaRepository<Record, Long>

fun RecordRepository.getEntityById(id: Long): Record =
    findById(id).orElseThrow(::RecordNotFoundException)