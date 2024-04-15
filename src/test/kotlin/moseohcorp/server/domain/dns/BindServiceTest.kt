package moseohcorp.server.domain.dns

import fixture.dns.BindFixture
import fixture.dns.PortForwardFixture
import helper.ServiceTest
import io.kotest.matchers.shouldBe
import io.mockk.*
import moseohcorp.server.domain.dns.exception.PortForwardNotFoundException
import moseohcorp.server.domain.dns.repository.BindRepository
import moseohcorp.server.domain.dns.repository.entity.Bind
import moseohcorp.server.domain.dns.repository.entity.PortForward
import moseohcorp.server.domain.dns.repository.getEntityById

class BindServiceTest : ServiceTest({
    val bindRepository = mockk<BindRepository>()
    val bindService = BindService(bindRepository)

    mockkObject(Bind.Companion)
    mockkObject(PortForward.Companion)

    Given("create") {
        val bind = BindFixture.bind()
        val request = BindFixture.bindCreateRequest()

        When("요청을 한다면") {
            every { Bind.of(request) } returns bind
            every { bindRepository.save(bind) } returns bind

            bindService.create(request)

            Then("수행한다.") {
                verify(exactly = 1) { Bind.of(request) }
                verify(exactly = 1) { bindRepository.save(bind) }
            }
        }
    }

    Given("update") {
        val bind = BindFixture.bind()
        val request = BindFixture.bindUpdateRequest(specificDomain = "updated")
        val updatedBind = BindFixture.bind()
        updatedBind.update(request)

        When("요청을 한다면") {
            every { bindRepository.getEntityById(bind.id) } returns bind
            every { bindRepository.save(bind) } returns bind

            bindService.update(bind.id, request)

            Then("수행한다.") {
                bind shouldBe updatedBind

                verify(exactly = 1) { bindRepository.getEntityById(bind.id) }
                verify(exactly = 1) { bindRepository.save(bind) }
            }
        }
    }

    Given("delete") {
        val bindId: Long = 1

        When("요청을 한다면") {
            every { bindRepository.existsById(bindId) } returns true
            every { bindRepository.deleteById(bindId) } just runs

            bindService.delete(bindId)

            Then("수행한다.") {
                verify(exactly = 1) { bindRepository.existsById(bindId) }
                verify(exactly = 1) { bindRepository.deleteById(bindId) }
            }
        }
    }

    Given("addPortForward") {
        val bind = BindFixture.bind()
        val portForward = PortForwardFixture.portForward()
        val request = PortForwardFixture.portForwardCreateRequest()
        val added = BindFixture.bind()
        added.add(portForward)

        When("요청을 한다면") {
            every { bindRepository.getEntityById(bind.id) } returns bind
            every { PortForward.of(request) } returns portForward
            every { bindRepository.save(bind) } returns bind

            bindService.addPortForward(bind.id, request)

            Then("수행한다.") {
                bind shouldBe added

                verify(exactly = 1) { bindRepository.getEntityById(bind.id) }
                verify(exactly = 1) { PortForward.of(request) }
                verify(exactly = 1) { bindRepository.save(bind) }
            }
        }
    }

    Given("updatePortForward") {
        val bind = BindFixture.bind()
        bind.add(PortForwardFixture.portForward())
        val request = PortForwardFixture.portForwardUpdateRequest(port = 4444)
        val updated = BindFixture.bind()
        updated.add(PortForwardFixture.portForward())
        updated.portForward?.update(request) ?: throw PortForwardNotFoundException()

        When("요청을 한다면") {
            every { bindRepository.getEntityById(bind.id) } returns bind
            every { bindRepository.save(bind) } returns bind

            bindService.updatePortForward(bind.id, request)

            Then("수행한다.") {
                bind shouldBe updated

                verify(exactly = 1) { bindRepository.getEntityById(bind.id) }
                verify(exactly = 1) { bindRepository.save(bind) }
            }
        }
    }

    Given("removePortForward") {
        val bind = BindFixture.bind()
        bind.add(PortForwardFixture.portForward())
        val removed = BindFixture.bind()

        When("요청을 한다면") {
            every { bindRepository.getEntityById(bind.id) } returns bind
            every { bindRepository.save(bind) } returns bind

            bindService.removePortForward(bind.id)

            Then("수행한다.") {
                bind shouldBe removed

                verify(exactly = 1) { bindRepository.getEntityById(bind.id) }
                verify(exactly = 1) { bindRepository.save(bind) }
            }
        }
    }
})