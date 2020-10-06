package com.atomfinger.touuid.kotlin.demo

import com.atomfinger.touuid.extension.toUUID
import com.atomfinger.touuid.extension.toUUIDs
import com.atomfinger.touuid.extension.uuids
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class ToUUIDTest {

    @Test
    fun `How to generate an UUID based on a single Integer`() {
        val expected = "00000000-0000-0000-0000-000000000001"
        val actual = 1.toUUID().toString()
        assertEquals(expected, actual)
    }

    @Test
    fun `How to generate a list of UUIDs from a list of Integers`() {
        val expected = getUuidExpectedResults()
        val actual = listOf(1, 2, 3, 4, 5).toUUIDs().map { it.toString() }
        assertEquals(expected, actual)
    }

    @Test
    fun `How to generate UUIDs from a sequence`() {
        val expected = getUuidExpectedResults()
        val actual = uuids().take(5).map { it.toString() }.toList()
        assertEquals(expected, actual)
    }

    @Test
    fun `How to generate a list of UUIDs based on a range of Integers`() {
        val expected = getUuidExpectedResults()
        val actual = (1..5).toUUIDs().map { it.toString() }
        assertEquals(expected, actual)
    }

    private fun getUuidExpectedResults(): List<String> = listOf(
        "00000000-0000-0000-0000-000000000001",
        "00000000-0000-0000-0000-000000000002",
        "00000000-0000-0000-0000-000000000003",
        "00000000-0000-0000-0000-000000000004",
        "00000000-0000-0000-0000-000000000005"
    )
}