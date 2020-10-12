package io.github.atomfinger.touuid

import java.util.*

/**
 * Converts the extended integer to a UUID
 *
 * 1 is converted to "00000000-0000-0000-0000-000000000001"
 * 10 is converted to "00000000-0000-0000-0000-000000000010"
 * 15 is converted to "00000000-0000-0000-0000-000000000015"
 * and so forth.
 *
 * A negative integer will result in a null UUID:
 * -1 is converted to "00000000-0000-0000-0000-000000000000"
 *
 *  EXAMPLE:
 *   1.toUUID()
 *
 * @return Converted UUID
 */
fun Int.toUUID(): UUID = UUIDs.toUUID(this)

/**
 * Converts a collection of integers to a list of UUID
 *
 *  EXAMPLE:
 *   listOf(1, 2, 3).toUUIDs()
 *
 *  @return List of UUIDs
 */
fun Collection<Int>.toUUIDs(): List<UUID> = this.map { it.toUUID() }

/**
 * Yields as many UUIDs as needed.
 *
 * EXAMPLE:
 *  uuids().take(5)
 *
 * WARNING:
 * While this function technically allows you to generate a couple of billions of UUIDs,
 * that is not a supported use case. The indented use case is to create the UUIDs needed to satisfy a test case.
 *
 */
fun uuids() = sequence {
    (1..Int.MAX_VALUE).forEach { i -> yield(i.toUUID()) }
}

/**
 * Converts a range of integers to UUIDs
 *
 * EXAMPLE:
 *  (1..5).toUUIDs()
 *
 * WARNING:
 * While this function technically allows you to generate a couple of billions of UUIDs,
 * that is not a supported use case. The indented use case is to create the UUIDs needed to satisfy a test case.
 *
 *  @return List of UUIDs
 */
fun IntRange.toUUIDs(): List<UUID> = this.toList().toUUIDs()
