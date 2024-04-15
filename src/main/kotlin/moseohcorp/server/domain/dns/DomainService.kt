package moseohcorp.server.domain.dns

import moseohcorp.server.api.dns.dto.request.*
import moseohcorp.server.domain.dns.exception.DomainNotFoundException
import moseohcorp.server.domain.dns.exception.PortForwardNotFoundException
import moseohcorp.server.domain.dns.exception.RecordNotFoundException
import moseohcorp.server.domain.dns.repository.*
import moseohcorp.server.domain.dns.repository.entity.Domain
import moseohcorp.server.domain.dns.repository.entity.PortForward
import moseohcorp.server.domain.dns.repository.entity.Record
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DomainService(
    private val domainRepository: DomainRepository,
    private val recordRepository: RecordRepository,
) {
    @Transactional
    fun create(request: DomainCreateRequest) {
        val domain = Domain.of(request)
        domainRepository.save(domain)
    }

    @Transactional
    fun update(dnsId: Long, request: DomainUpdateRequest) {
        val dns = domainRepository.getEntityById(dnsId)
        dns.update(request)
        domainRepository.save(dns)
    }

    @Transactional
    fun delete(dnsId: Long) {
        check(domainRepository.existsById(dnsId)) { DomainNotFoundException() }
        domainRepository.deleteById(dnsId)
    }

    @Transactional
    fun addRecord(dnsId: Long, request: RecordCreateRequest) {
        val dns = domainRepository.getEntityById(dnsId)
        val record = Record.of(request)
        dns.add(record)
        domainRepository.save(dns)
    }

    @Transactional
    fun updateRecord(recordId: Long, request: RecordUpdateRequest) {
        val record = recordRepository.getEntityById(recordId)
        record.update(request)
        recordRepository.save(record)
    }

    @Transactional
    fun removeRecord(recordId: Long) {
        check(recordRepository.existsById(recordId)) { RecordNotFoundException() }
        recordRepository.deleteById(recordId)
    }

    @Transactional
    fun addPortForward(recordId: Long, request: PortForwardCreateRequest) {
        val record = recordRepository.getEntityById(recordId)
        val portForward = PortForward.of(request)
        record.add(portForward)
        recordRepository.save(record)
    }

    @Transactional
    fun updatePortForward(recordId: Long, request: PortForwardUpdateRequest) {
        val record = recordRepository.getEntityById(recordId)
        record.portForward?.update(request) ?: throw PortForwardNotFoundException()
        recordRepository.save(record)
    }

    @Transactional
    fun removePortForward(recordId: Long) {
        val record = recordRepository.getEntityById(recordId)
        record.portForward ?: throw PortForwardNotFoundException()
        record.removePortForward()
        recordRepository.save(record)
    }
}