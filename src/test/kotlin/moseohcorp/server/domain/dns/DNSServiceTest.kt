package moseohcorp.server.domain.dns

import fixture.dns.DNSFixture
import fixture.dns.PortForwardFixture
import fixture.dns.RecordFixture
import helper.ServiceTest
import io.kotest.matchers.shouldBe
import io.mockk.*
import moseohcorp.server.domain.dns.exception.PortForwardNotFoundException
import moseohcorp.server.domain.dns.repository.DNSRepository
import moseohcorp.server.domain.dns.repository.RecordRepository
import moseohcorp.server.domain.dns.repository.entity.DNS
import moseohcorp.server.domain.dns.repository.entity.PortForward
import moseohcorp.server.domain.dns.repository.entity.Record
import moseohcorp.server.domain.dns.repository.getEntityById

class DNSServiceTest : ServiceTest({
    val dnsRepository = mockk<DNSRepository>()
    val recordRepository = mockk<RecordRepository>()
    val dnsService = DNSService(dnsRepository, recordRepository)

    mockkObject(DNS.Companion)
    mockkObject(Record.Companion)
    mockkObject(PortForward.Companion)

    Given("create") {
        val dns = DNSFixture.dns()
        val request = DNSFixture.dnsCreateRequest()

        When("요청을 한다면") {
            every { DNS.of(request) } returns dns
            every { dnsRepository.save(dns) } returns dns

            dnsService.create(request)

            Then("수행한다.") {
                verify(exactly = 1) { DNS.of(request) }
                verify(exactly = 1) { dnsRepository.save(dns) }
            }
        }
    }

    Given("update") {
        val dns = DNSFixture.dns()
        val request = DNSFixture.dnsUpdateRequest(domain = "updated")
        val updatedDns = DNSFixture.dns()
        updatedDns.update(request)

        When("요청을 한다면") {
            every { dnsRepository.getEntityById(dns.id) } returns dns
            every { dnsRepository.save(dns) } returns dns

            dnsService.update(dns.id, request)

            Then("업데이트 된다.") {
                dns shouldBe updatedDns

                verify(exactly = 1) { dnsRepository.getEntityById(dns.id) }
                verify(exactly = 1) { dnsRepository.save(dns) }
            }
        }
    }

    Given("delete") {
        val dnsId: Long = 1

        When("요청을 한다면") {
            every { dnsRepository.existsById(dnsId) } returns true
            every { dnsRepository.deleteById(dnsId) } just runs

            dnsService.delete(dnsId)

            Then("수행한다.") {
                verify(exactly = 1) { dnsRepository.existsById(dnsId) }
                verify(exactly = 1) { dnsRepository.deleteById(dnsId) }
            }
        }
    }

    Given("addRecord") {
        val dns = DNSFixture.dns()
        val record = RecordFixture.record()
        val request = RecordFixture.recordCreateRequest()
        val added = DNSFixture.dns()
        added.add(record)

        When("요청을 한다면") {
            every { Record.of(request) } returns record
            every { dnsRepository.getEntityById(dns.id) } returns dns
            every { dnsRepository.save(dns) } returns dns

            dnsService.addRecord(dns.id, request)

            Then("수행한다.") {
                dns shouldBe added

                verify(exactly = 1) { dnsRepository.getEntityById(dns.id) }
                verify(exactly = 1) { Record.of(request) }
                verify(exactly = 1) { dnsRepository.save(dns) }
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

            dnsService.updateRecord(record.id, request)

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

            dnsService.removeRecord(recordId)

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

            dnsService.addPortForward(record.id, request)

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

            dnsService.updatePortForward(record.id, request)

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

            dnsService.removePortForward(record.id)

            Then("수행한다.") {
                record shouldBe removed

                verify(exactly = 1) { recordRepository.getEntityById(record.id) }
                verify(exactly = 1) { recordRepository.save(record) }
            }
        }
    }
})
