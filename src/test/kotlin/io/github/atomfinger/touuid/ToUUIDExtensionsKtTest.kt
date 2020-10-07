package io.github.atomfinger.touuid

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*
import java.util.UUID.fromString

internal class ToUUIDExtensionsKtTest {

    @Test
    fun toUUID_isValidInt_UUIDCorrectlyGenerated() {
        assertEquals(fromString("00000000-0000-0000-0000-000000000001"), 1.toUUID())
    }

    @Test
    fun toUUID_intIsMax_UUIDCorrectlyGenerated() {
        assertEquals(fromString("00000000-0000-0000-0000-002147483647"), Int.MAX_VALUE.toUUID())
    }

    @Test
    fun toUUID_intIsNegative_zeroUUIDReturned() {
        assertEquals(fromString("00000000-0000-0000-0000-000000000000"), (-1).toUUID())
    }

    @Test
    fun toUUIDs_rangeIsValid_correctUUIDsReturned() {
        assertEquals(expectedListResult(), (1..5).toUUIDs())
    }

    @Test
    fun toUUIDs_passingIntsAsCollection_correctUUIDsReturned() {
        assertEquals(expectedListResult(), listOf(1, 2, 3, 4, 5).toUUIDs())
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
