package helper

import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockkStatic
import java.time.LocalDateTime

abstract class ServiceTest(
    body: BehaviorSpec.() -> Unit = {}
) : BehaviorSpec({

    beforeTest {
        mockkStatic(LocalDateTime::class)
        every { LocalDateTime.now() } returns LocalDateTime.of(2020, 1, 1, 12, 0, 0)
    }

    afterTest {
        clearAllMocks()
    }

    body()
})