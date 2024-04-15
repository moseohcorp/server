package moseohcorp.server.domain.dns

import moseohcorp.server.api.dns.dto.request.*
import moseohcorp.server.domain.dns.exception.DNSNotFoundException
import moseohcorp.server.domain.dns.exception.PortForwardNotFoundException
import moseohcorp.server.domain.dns.exception.RecordNotFoundException
import moseohcorp.server.domain.dns.repository.*
import moseohcorp.server.domain.dns.repository.entity.DNS
import moseohcorp.server.domain.dns.repository.entity.PortForward
import moseohcorp.server.domain.dns.repository.entity.Record
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class DNSService(
    private val dnsRepository: DNSRepository,
    private val recordRepository: RecordRepository,
) {
    @Transactional
    fun create(request: DNSCreateRequest) {
        val dns = DNS.of(request)
        dnsRepository.save(dns)
    }

    @Transactional
    fun update(dnsId: Long, request: DNSUpdateRequest) {
        val dns = dnsRepository.getEntityById(dnsId)
        dns.update(request)
        dnsRepository.save(dns)
    }

    @Transactional
    fun delete(dnsId: Long) {
        check(dnsRepository.existsById(dnsId)) { DNSNotFoundException() }
        dnsRepository.deleteById(dnsId)
    }

    @Transactional
    fun addRecord(dnsId: Long, request: RecordCreateRequest) {
        val dns = dnsRepository.getEntityById(dnsId)
        val record = Record.of(request)
        dns.add(record)
        dnsRepository.save(dns)
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