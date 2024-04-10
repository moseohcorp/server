package moseohcorp.server.domain.dns

import moseohcorp.server.api.dns.dto.request.BindCreateRequest
import moseohcorp.server.api.dns.dto.request.BindUpdateRequest
import moseohcorp.server.api.dns.dto.request.PortForwardCreateRequest
import moseohcorp.server.api.dns.dto.request.PortForwardUpdateRequest
import moseohcorp.server.domain.dns.exception.BindNotFoundException
import moseohcorp.server.domain.dns.exception.PortForwardNotFoundException
import moseohcorp.server.domain.dns.repository.BindRepository
import moseohcorp.server.domain.dns.repository.entity.Bind
import moseohcorp.server.domain.dns.repository.entity.PortForward
import moseohcorp.server.domain.dns.repository.getEntityById
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BindService(
    private val bindRepository: BindRepository,
) {
    @Transactional
    fun create(request: BindCreateRequest) {
        val bind = Bind.of(request)
        bindRepository.save(bind)
    }

    @Transactional
    fun update(id: Long, request: BindUpdateRequest) {
        val bind = bindRepository.getEntityById(id)
        bind.update(request)
        bindRepository.save(bind)
    }

    @Transactional
    fun delete(id: Long) {
        check(bindRepository.existsById(id)) { BindNotFoundException() }
        bindRepository.deleteById(id)
    }

    @Transactional
    fun addPortForward(bindId: Long, request: PortForwardCreateRequest) {
        val bind = bindRepository.getEntityById(bindId)
        val portForward = PortForward.of(request)
        bind.update(portForward)
        bindRepository.save(bind)
    }

    @Transactional
    fun updatePortForward(bindId: Long, request: PortForwardUpdateRequest) {
        val bind = bindRepository.getEntityById(bindId)
        bind.portForward?.update(request) ?: throw PortForwardNotFoundException()
        bindRepository.save(bind)
    }

    @Transactional
    fun removePortForward(bindId: Long) {
        val bind = bindRepository.getEntityById(bindId)
        bind.portForward ?: throw PortForwardNotFoundException()
        bind.removePortForward()
        bindRepository.save(bind)
    }
}