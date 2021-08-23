package io.github.atomfinger.touuid;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UUIDs {

    private UUIDs() {
    }

    /**
     * Converts an int to a UUID
     * <p>
     * 1 is converted to "00000000-0000-0000-0000-000000000001"
     * 10 is converted to "00000000-0000-0000-0000-000000000010"
     * 15 is converted to "00000000-0000-0000-0000-000000000015"
     * and so forth.
     * <p>
     * A negative integer will result in a null UUID:
     * -1 is converted to "00000000-0000-0000-0000-000000000000"
     * <p>
     * Example:
     * UUIDs.toUUID(1);
     *
     * @param number: The int to convert
     * @return the generated UUID
     */
    public static UUID toUUID(int number) {
        long leastSignificantBits = Long.parseLong(Integer.toString(Math.max(number, 0)), 16) & 281474976710655L;
        return new UUID(0, leastSignificantBits);
    }

    /**
     * Generates a list of UUIDs based on int inputs
     * <p>
     * Example:
     * UUIDs.toUUIDs(1, 2, 3);
     *
     * @param positiveNumbers: The numbers to convert
     * @return List of generated UUIDs
     */
    public static List<UUID> toUUIDs(int... positiveNumbers) {
        return Arrays.stream(positiveNumbers).mapToObj(UUIDs::toUUID).collect(Collectors.toList());
    }

    /**
     * Generates a list of UUIDs based on a collection of integers
     * <p>
     * Example:
     * UUIDs.toUUIDs(Arrays.asList(1, 2, 3));
     *
     * @param integers: The numbers to convert
     * @return List of generated UUIDs
     */
    public static List<UUID> toUUIDs(Collection<Integer> integers) {
        return integers.stream().map(UUIDs::toUUID).collect(Collectors.toList());
    }

    /**
     * Generates a list of UUIDs based on an inclusive range of ints
     * <p>
     * Example:
     * UUIDs.toUUIDsFromRange(1, 3); //Will generate 3 UUIDs
     *
     * @param startInclusive: Start of range
     * @param endInclusive:   End of range
     * @return List of generated UUIDs
     */
    public static List<UUID> toUUIDsFromRange(int startInclusive, int endInclusive) {
        return IntStream.rangeClosed(startInclusive, endInclusive).mapToObj(UUIDs::toUUID).collect(Collectors.toList());
    }
}
