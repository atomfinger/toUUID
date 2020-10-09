package com.atomfinger.touuid.java.demo;

//import kotlin.ranges.IntRange;

import io.github.atomfinger.touuid.UUIDs;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static io.github.atomfinger.touuid.UUIDs.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class IntegerToUUIDTest {

    @Test
    @DisplayName("How to generate an UUID based on a single Integer")
    public void test_single_integer() {
        UUID expected = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID actual = toUUID(1);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("How to generate a list of UUIDs from a list of Integers")
    public void test_from_list_of_integers() {
        List<UUID> expected = getUuidExpectedResults();
        List<UUID> actual = toUUIDs(Arrays.asList(1, 2, 3, 4, 5));
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("How to generate a list of UUIDs through varargs")
    public void test_from_integer_varargs() {
        List<UUID> expected = getUuidExpectedResults();
        List<UUID> actual = toUUIDs(1, 2, 3, 4, 5);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("How to generate a list of UUIDs based on a range of Integers")
    public void test_range_of_integers() {
        List<UUID> expected = getUuidExpectedResults();
        List<UUID> actual = toUUIDsFromRange(1, 5);
        assertEquals(expected, actual);
    }

    private List<UUID> getUuidExpectedResults() {
        return Arrays.asList(
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                UUID.fromString("00000000-0000-0000-0000-000000000002"),
                UUID.fromString("00000000-0000-0000-0000-000000000003"),
                UUID.fromString("00000000-0000-0000-0000-000000000004"),
                UUID.fromString("00000000-0000-0000-0000-000000000005"));
    }
}