package com.atomfinger.touuid

import java.util.*

private const val totalLength = 12
private const val paddingCharacter = '0'

private fun generateUUIDString(i: Int) =
    "00000000-0000-0000-0000-${i.toString().padStart(totalLength, paddingCharacter)}"

fun Int.toUUID(): UUID = UUID.fromString(generateUUIDString(maxOf(this, 0)))

fun Collection<Int>.toUUIDs() = this.map { it.toUUID() }

fun uuids() = sequence {
    for (i in (1..Int.MAX_VALUE))
        yield(i.toUUID())
}

fun IntRange.toUUIDs(): List<UUID> = this.toList().toUUIDs()

