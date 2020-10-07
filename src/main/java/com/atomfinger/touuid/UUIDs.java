package com.atomfinger.touuid;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UUIDs {

    public static UUID fromInt(int positiveNumber) {
        return toUUID(positiveNumber);
    }

    public static List<UUID> fromInts(int... positiveNumbers) {
        return Arrays.stream(positiveNumbers).mapToObj(UUIDs::toUUID).collect(Collectors.toList());
    }

    public static List<UUID> fromInts(Collection<Integer> integers) {
        return integers.stream().map(UUIDs::toUUID).collect(Collectors.toList());
    }

    public static List<UUID> fromRange(int startInclusive, int endInclusive) {
        return IntStream.rangeClosed(startInclusive, endInclusive).mapToObj(UUIDs::toUUID).collect(Collectors.toList());
    }

    private static List<UUID> toUUIDs(Collection<Integer> integers) {
        return integers.stream().map(UUIDs::toUUID).collect(Collectors.toList());
    }

    private static UUID toUUID(Integer integer) {
        return UUID.fromString("00000000-0000-0000-0000-" + getUUIDStringPart(Math.max(integer, 0)));
    }

    private static String getUUIDStringPart(Integer integer) {
        return ("000000000000" + integer.toString()).substring(integer.toString().length());
    }
}
