package com.atomfinger.touuid

import com.atomfinger.touuid.extension.toUUID
import com.atomfinger.touuid.extension.toUUIDs

class UUIDConverter {

    companion object {
        @JvmStatic fun uuidFromInt(int: Int) = int.toUUID()

        @JvmStatic fun uuidsFromInts(vararg ints: Int) = ints.asList().toUUIDs()

        @JvmStatic fun uuidsFromInts(ints: Collection<Int>) = ints.toUUIDs()

        @JvmStatic fun uuidsFromInts(range: IntRange) = range.toUUIDs()
    }
}