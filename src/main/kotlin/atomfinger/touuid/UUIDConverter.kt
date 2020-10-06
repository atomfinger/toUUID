package atomfinger.touuid

import atomfinger.touuid.extension.toUUID
import atomfinger.touuid.extension.toUUIDs

class UUIDConverter {

    companion object {
        fun fromInt(int: Int) = int.toUUID()

        fun fromInts(vararg ints: Int) = ints.asList().toUUIDs()

        fun fromInts(ints: Collection<Int>) = ints.toUUIDs()
    }
}