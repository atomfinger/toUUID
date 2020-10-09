package io.github.atomfinger.touuid

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.UUID.fromString

class UUIDsTest {

    @Test
    fun toUUID_isValidInt_UUIDCorrectlyGenerated() {
        assertEquals(fromString("00000000-0000-0000-0000-000000000001"), UUIDs.toUUID(1))
    }

    @Test
    fun toUUID_intIsMax_UUIDCorrectlyGenerated() {
        assertEquals(fromString("00000000-0000-0000-0000-002147483647"), UUIDs.toUUID(Int.MAX_VALUE))
    }

    @Test
    fun toUUID_intIsNegative_zeroUUIDReturned() {
        assertEquals(fromString("00000000-0000-0000-0000-000000000000"), UUIDs.toUUID(-1))
    }

    @Test
    fun toUUIDsFromRange_rangeIsValid_correctUUIDsReturned() {
        val actual = UUIDs.toUUIDsFromRange(1, 5)
        assertEquals(uuids(), actual)
    }

    @Test
    fun toUUIDsFromRange_invalidRange_emptyListReturned() {
        assertTrue(UUIDs.toUUIDsFromRange(5, 1).isEmpty())
    }

    @Test
    fun toUUIDs_passingIntsAsVararg_correctUUIDsReturned() {
        val actual = UUIDs.toUUIDs(1, 2, 3, 4, 5)
        assertEquals(uuids(), actual)
    }

    @Test
    fun toUUIDs_passingIntsAsCollection_correctUUIDsReturned() {
        val input = listOf(1, 2, 3, 4, 5)
        val actual = UUIDs.toUUIDs(input)
        assertEquals(uuids(), actual)
    }

    @Test
    fun toUUIDs_collectionContainsNull_nullPointerExceptionThrown() {
        val input = listOf(1, null)
        assertThrows(NullPointerException::class.java) { UUIDs.toUUIDs(input) }
    }

    private fun uuids() = listOf(
        fromString("00000000-0000-0000-0000-000000000001"),
        fromString("00000000-0000-0000-0000-000000000002"),
        fromString("00000000-0000-0000-0000-000000000003"),
        fromString("00000000-0000-0000-0000-000000000004"),
        fromString("00000000-0000-0000-0000-000000000005")
    )
}