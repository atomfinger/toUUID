package com.atomfinger.touuid.kotlin.demo

import com.atomfinger.touuid.toUUID
import com.atomfinger.touuid.toUUIDs
import com.atomfinger.touuid.uuids
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.UUID.fromString


class ToUUIDTest {

    @Test
    fun `How to generate an UUID based on a single Integer`() {
        val expected = fromString("00000000-0000-0000-0000-000000000001")
        val actual = 1.toUUID()
        assertEquals(expected, actual)
    }

    @Test
    fun `How to generate a list of UUIDs from a list of Integers`() {
        val expected = getUuidExpectedResults()
        val actual = listOf(1, 2, 3, 4, 5).toUUIDs()
        assertEquals(expected, actual)
    }

    @Test
    fun `How to generate UUIDs from a sequence`() {
        val expected = getUuidExpectedResults()
        val actual = uuids().take(5).toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `How to generate a list of UUIDs based on a range of Integers`() {
        val expected = getUuidExpectedResults()
        val actual = (1..5).toUUIDs()
        assertEquals(expected, actual)
    }

    private fun getUuidExpectedResults() = listOf(
        fromString("00000000-0000-0000-0000-000000000001"),
        fromString("00000000-0000-0000-0000-000000000002"),
        fromString("00000000-0000-0000-0000-000000000003"),
        fromString("00000000-0000-0000-0000-000000000004"),
        fromString("00000000-0000-0000-0000-000000000005")
    )
}