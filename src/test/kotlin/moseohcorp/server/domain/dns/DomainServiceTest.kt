package moseohcorp.server.domain.dns

import fixture.dns.DomainFixture
import fixture.dns.PortForwardFixture
import fixture.dns.RecordFixture
import helper.ServiceTest
import io.kotest.matchers.shouldBe
import io.mockk.*
import moseohcorp.server.domain.dns.exception.PortForwardNotFoundException
import moseohcorp.server.domain.dns.repository.DomainRepository
import moseohcorp.server.domain.dns.repository.RecordRepository
import moseohcorp.server.domain.dns.repository.entity.Domain
import moseohcorp.server.domain.dns.repository.entity.PortForward
import moseohcorp.server.domain.dns.repository.entity.Record
import moseohcorp.server.domain.dns.repository.getEntityById

class DomainServiceTest : ServiceTest({
    val domainRepository = mockk<DomainRepository>()
    val recordRepository = mockk<RecordRepository>()
    val domainService = DomainService(domainRepository, recordRepository)

    mockkObject(Domain.Companion)
    mockkObject(Record.Companion)
    mockkObject(PortForward.Companion)

    Given("create") {
        val domain = DomainFixture.domain()
        val request = DomainFixture.domainCreateRequest()

        When("요청을 한다면") {
            every { Domain.of(request) } returns domain
            every { domainRepository.save(domain) } returns domain

            domainService.create(request)

            Then("수행한다.") {
                verify(exactly = 1) { Domain.of(request) }
                verify(exactly = 1) { domainRepository.save(domain) }
            }
        }
    }

    Given("update") {
        val domain = DomainFixture.domain()
        val request = DomainFixture.domainUpdateRequest(domain = "updated")
        val updatedDns = DomainFixture.domain()
        updatedDns.update(request)

        When("요청을 한다면") {
            every { domainRepository.getEntityById(domain.id) } returns domain
            every { domainRepository.save(domain) } returns domain

            domainService.update(domain.id, request)

            Then("업데이트 된다.") {
                domain shouldBe updatedDns

                verify(exactly = 1) { domainRepository.getEntityById(domain.id) }
                verify(exactly = 1) { domainRepository.save(domain) }
            }
        }
    }

    Given("delete") {
        val domainId: Long = 1

        When("요청을 한다면") {
            every { domainRepository.existsById(domainId) } returns true
            every { domainRepository.deleteById(domainId) } just runs

            domainService.delete(domainId)

            Then("수행한다.") {
                verify(exactly = 1) { domainRepository.existsById(domainId) }
                verify(exactly = 1) { domainRepository.deleteById(domainId) }
            }
        }
    }

    Given("addRecord") {
        val domain = DomainFixture.domain()
        val record = RecordFixture.record()
        val request = RecordFixture.recordCreateRequest()
        val added = DomainFixture.domain()
        added.add(record)

        When("요청을 한다면") {
            every { Record.of(request) } returns record
            every { domainRepository.getEntityById(domain.id) } returns domain
            every { domainRepository.save(domain) } returns domain

            domainService.addRecord(domain.id, request)

            Then("수행한다.") {
                domain shouldBe added

                verify(exactly = 1) { domainRepository.getEntityById(domain.id) }
                verify(exactly = 1) { Record.of(request) }
                verify(exactly = 1) { domainRepository.save(domain) }
            }
        }
    }

    Given("updateRecord") {
        val record = RecordFixture.record()
        val request = RecordFixture.recordUpdateRequest(subDomain = "updated")
        val updated = RecordFixture.record()
        updated.update(request)

        When("요청을 한다면") {
            every { recordRepository.getEntityById(record.id) } returns record
            every { recordRepository.save(record) } returns record

            domainService.updateRecord(record.id, request)

            Then("수행한다.") {
                record shouldBe updated

                verify(exactly = 1) { recordRepository.getEntityById(record.id) }
                verify(exactly = 1) { recordRepository.save(record) }
            }
        }
    }

    Given("removeRecord") {
        val recordId = 1L

        When("요청을 한다면") {
            every { recordRepository.existsById(recordId) } returns true
            every { recordRepository.deleteById(recordId) } just runs

            domainService.removeRecord(recordId)

            Then("수행한다.") {
                verify(exactly = 1) { recordRepository.existsById(recordId) }
                verify(exactly = 1) { recordRepository.deleteById(recordId) }
            }
        }
    }

    Given("addPortForward") {
        val record = RecordFixture.record()
        val portForward = PortForwardFixture.portForward()
        val request = PortForwardFixture.portForwardCreateRequest()
        val added = RecordFixture.record()
        added.add(portForward)

        When("요청을 한다면") {
            every { recordRepository.getEntityById(record.id) } returns record
            every { PortForward.of(request) } returns portForward
            every { recordRepository.save(record) } returns record

            domainService.addPortForward(record.id, request)

            Then("수행한다.") {
                record shouldBe added

                verify(exactly = 1) { recordRepository.getEntityById(record.id) }
                verify(exactly = 1) { PortForward.of(request) }
                verify(exactly = 1) { recordRepository.save(record) }
            }
        }
    }

    Given("updatePortForward") {
        val record = RecordFixture.record()
        record.add(PortForwardFixture.portForward())
        val request = PortForwardFixture.portForwardUpdateRequest(port = 4444)
        val updated = RecordFixture.record()
        updated.add(PortForwardFixture.portForward())
        updated.portForward?.update(request) ?: throw PortForwardNotFoundException()

        When("요청을 한다면") {
            every { recordRepository.getEntityById(record.id) } returns record
            every { recordRepository.save(record) } returns record

            domainService.updatePortForward(record.id, request)

            Then("수행한다.") {
                record shouldBe updated

                verify(exactly = 1) { recordRepository.getEntityById(record.id) }
                verify(exactly = 1) { recordRepository.save(record) }
            }
        }
    }

    Given("removePortForward") {
        val record = RecordFixture.record()
        record.add(PortForwardFixture.portForward())
        val removed = RecordFixture.record()

        When("요청을 한다면") {
            every { recordRepository.getEntityById(record.id) } returns record
            every { recordRepository.save(record) } returns record

            domainService.removePortForward(record.id)

            Then("수행한다.") {
                record shouldBe removed

                verify(exactly = 1) { recordRepository.getEntityById(record.id) }
                verify(exactly = 1) { recordRepository.save(record) }
            }
        }
    }
})
