package atomfinger.touuid.extension

import atomfinger.touuid.NumberOutOfBoundsException
import java.util.*

private const val totalLength = 32
private const val paddingCharacter = '0'
private const val splitCharacter = '-'

private fun dashIndexes() = listOf(8, 13, 17, 22)

private fun generateUUIDString(i: Int) =
        StringBuilder(i.toString().padStart(totalLength, paddingCharacter)).apply {
            for (index in dashIndexes())
                this.insert(index, splitCharacter)
        }.toString()

fun Int.toUUID(): UUID =
        if (this >= 0)
            UUID.fromString(generateUUIDString(this))
        else
            throw NumberOutOfBoundsException("Integer must be a positive number")

fun Collection<Int>.toUUIDs() = this.map { it.toUUID() }

fun uuids() = sequence {
    for ( i in (1..Int.MAX_VALUE))
        yield(i.toUUID())
}

fun IntRange.toUUIDs(): List<UUID> = this.toList().toUUIDs()

