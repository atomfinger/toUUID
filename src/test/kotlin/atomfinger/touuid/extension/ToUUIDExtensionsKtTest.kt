package atomfinger.touuid.extension

import atomfinger.touuid.NumberOutOfBoundsException
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

internal class ToUUIDExtensionsKtTest {

    @Test
    fun toUUID_isValidInt_UUIDCorrectlyGenerated() {
        assert(1.toUUID().toString() == "00000000-0000-0000-0000-000000000001")
    }

    @Test
    fun toUUID_intIsMax_UUIDCorrectlyGenerated() {
        assert(Int.MAX_VALUE.toUUID().toString() == "00000000-0000-0000-0000-002147483647")
    }

    @Test
    fun toUUID_intIsNegative_exceptionThrown() {
        assertThrows(NumberOutOfBoundsException::class.java) { (-1).toUUID() }
    }
}
