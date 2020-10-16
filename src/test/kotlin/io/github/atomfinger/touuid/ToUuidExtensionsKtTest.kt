package io.github.atomfinger.touuid

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID.fromString

internal class ToUuidExtensionsKtTest {

    @Test
    fun toUuid_isValidInt_UUIDCorrectlyGenerated() {
        assertEquals(fromString("00000000-0000-0000-0000-000000000001"), 1.toUuid())
    }

    @Test
    fun toUuid_intIsMax_UUIDCorrectlyGenerated() {
        assertEquals(fromString("00000000-0000-0000-0000-002147483647"), Int.MAX_VALUE.toUuid())
    }

    @Test
    fun toUuid_intIsNegative_zeroUUIDReturned() {
        assertEquals(fromString("00000000-0000-0000-0000-000000000000"), (-1).toUuid())
    }

    @Test
    fun toUuids_rangeIsValid_correctUUIDsReturned() {
        assertEquals(expectedListResult(), (1..5).toUuids())
    }

    @Test
    fun toUuids_passingIntsAsCollection_correctUUIDsReturned() {
        assertEquals(expectedListResult(), listOf(1, 2, 3, 4, 5).toUuids())
    }

    @Test
    fun uuids_takingFiveUUIDs_fiveReturned() {
        assertEquals(expectedListResult(), uuids().take(5).toList())
    }

    private fun expectedListResult() = listOf(
        fromString("00000000-0000-0000-0000-000000000001"),
        fromString("00000000-0000-0000-0000-000000000002"),
        fromString("00000000-0000-0000-0000-000000000003"),
        fromString("00000000-0000-0000-0000-000000000004"),
        fromString("00000000-0000-0000-0000-000000000005")
    )
}
