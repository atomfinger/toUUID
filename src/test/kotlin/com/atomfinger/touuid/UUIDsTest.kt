package com.atomfinger.touuid

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID.fromString

class UUIDsTest {

    @Test
    fun fromInt_isValidInt_UUIDCorrectlyGenerated() {
        assertEquals(fromString("00000000-0000-0000-0000-000000000001"), UUIDs.fromInt(1))
    }

    @Test
    fun fromInt_intIsMax_UUIDCorrectlyGenerated() {
        assertEquals(fromString("00000000-0000-0000-0000-002147483647"), UUIDs.fromInt(Int.MAX_VALUE))
    }

    @Test
    fun fromInt_intIsNegative_zeroUUIDReturned() {
        assertEquals(fromString("00000000-0000-0000-0000-000000000000"), UUIDs.fromInt(-1))
    }

    @Test
    fun fromRange_rangeIsValid_correctUUIDsReturned() {
        val actual = UUIDs.fromRange(1, 5)
        assertEquals(uuids(), actual)
    }

    @Test
    fun fromRange_invalidRange_emptyListReturned() {
        assertTrue(UUIDs.fromRange(5, 1).isEmpty())
    }

    @Test
    fun fromInts_passingIntsAsVararg_correctUUIDsReturned() {
        val actual = UUIDs.fromInts(1, 2, 3, 4, 5)
        assertEquals(uuids(), actual)
    }

    @Test
    fun fromInts_passingIntsAsCollection_correctUUIDsReturned() {
        val input = listOf(1, 2, 3, 4, 5)
        val actual = UUIDs.fromInts(input)
        assertEquals(uuids(), actual)
    }

    @Test
    fun fromInts_collectionContainsNull_nullPointerExceptionThrown() {
        val input = listOf(1, null)
        assertThrows(NullPointerException::class.java) { UUIDs.fromInts(input) }
    }

    private fun uuids() = listOf(
        fromString("00000000-0000-0000-0000-000000000001"),
        fromString("00000000-0000-0000-0000-000000000002"),
        fromString("00000000-0000-0000-0000-000000000003"),
        fromString("00000000-0000-0000-0000-000000000004"),
        fromString("00000000-0000-0000-0000-000000000005")
    )
}