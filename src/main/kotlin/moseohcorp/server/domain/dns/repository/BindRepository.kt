package moseohcorp.server.domain.dns.repository

import moseohcorp.server.domain.dns.exception.BindNotFoundException
import moseohcorp.server.domain.dns.repository.entity.Bind
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BindRepository : JpaRepository<Bind, Long>

fun BindRepository.getEntityById(id: Long): Bind =
    findById(id).orElseThrow(::BindNotFoundException)