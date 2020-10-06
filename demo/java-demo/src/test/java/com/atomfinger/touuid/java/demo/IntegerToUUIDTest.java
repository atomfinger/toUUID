package com.atomfinger.touuid.java.demo;

import kotlin.ranges.IntRange;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.atomfinger.touuid.UUIDConverter.uuidFromInt;
import static com.atomfinger.touuid.UUIDConverter.uuidsFromInts;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerToUUIDTest {

    @Test
    @DisplayName("How to generate an UUID based on a single Integer")
    public void test_single_integer() {
        String expected = "00000000-0000-0000-0000-000000000001";
        String actual = uuidFromInt(1).toString();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("How to generate a list of UUIDs from a list of Integers")
    public void test_from_list_of_integers() {
        List<String> expected = getUuidExpectedResults();
        List<UUID> actual = uuidsFromInts(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(expected, getUuidStrings(actual));
    }

    @Test
    @DisplayName("How to generate a list of UUIDs through varargs")
    public void test_from_integer_varargs() {
        List<String> expected = getUuidExpectedResults();
        List<UUID> actual = uuidsFromInts(1, 2, 3, 4, 5);
        assertEquals(expected, getUuidStrings(actual));
    }

    @Test
    @DisplayName("How to generate a list of UUIDs based on a range of Integers")
    public void test_range_of_integers() {
        List<String> expected = getUuidExpectedResults();
        List<UUID> actual = uuidsFromInts(new IntRange(1, 5));
        assertEquals(expected, getUuidStrings(actual));
    }

    private List<String> getUuidStrings(List<UUID> uuids) {
        return uuids.stream().map(UUID::toString).collect(Collectors.toList());
    }

    private List<String> getUuidExpectedResults() {
        return Arrays.asList("00000000-0000-0000-0000-000000000001",
                "00000000-0000-0000-0000-000000000002",
                "00000000-0000-0000-0000-000000000003",
                "00000000-0000-0000-0000-000000000004",
                "00000000-0000-0000-0000-000000000005");
    }
}