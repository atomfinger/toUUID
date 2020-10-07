![Build](https://github.com/atomfinger/toUUID/workflows/Build/badge.svg)

<img src="/images/logo.png" width="350">

.toUUID() is a small, easy to use, library for generating UUIDs in unit and integration tests.

## Why .toUUID()


## How .toUUID() works

## .toUUID() examples in Java

.toUUID() library was written in Kotlin, but one of the goals was to keep it free for any unnecessary dependencies, which is why Java users should use the [UUIDs class](src/main/java/com/atomfinger/touuid/UUIDs.java).

**UUID from a single integer:**

```java
UUID uuid = UUIDs.fromInt(1);
System.out.println(uuid.toString());
//Output:
//00000000-0000-0000-0000-000000000001
```

**UUID from a list of integers:**

```java
List<UUID> uuids = UUIDs.fromInts(Arrays.asList(1, 2, 3, 4, 5));
uuids.forEach((it) -> System.out.println(it.toString()));
//Output:
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

**UUID from varargs:**

```java
List<UUID> uuids = UUIDs.fromInts(1, 2, 3, 4, 5);
uuids.forEach((it) -> System.out.println(it.toString()));
//Output:
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

**UUID from range of integers:**

```Java
List<UUID> uuids = UUIDs.fromRange(1, 5);
uuids.forEach((it) -> System.out.println(it.toString()));
//Output:
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

## .toUUID() examples in Kotlin
The kotlin implementation uses extension functions to make UUID creation prettier and easier. 

**Generate based on an integers:**

```kotlin
val uuid = 1.toUUID()
println(uuid.toString())
//Output:
//00000000-0000-0000-0000-000000000001
```

**Generate from a list of integers:**

```kotlin
val uuids = listOf(1, 2, 3, 4, 5).toUUIDs()
uuids.forEach { println(it.toString()) }
//Output:
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

**Generate based on a range of integers:**

```kotlin
val uuids = (1..5).toUUIDs()
uuids.forEach { println(it.toString()) }
//Output:
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

**Generate based on a sequence of integers:**

```kotlin
val uuids = uuids().take(5)
uuids.forEach { println(it.toString()) }
//Output:
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

## Why a whole library?

Granted, a whole extra dependency for this feature might not be worth it for everyone. Do consider that this dependency should only ever be included into the test scope of your application; therefore it won't ever touch the running production code, so .toUUID() can be an accessible way of generating UUIDs.

If you are still hesitant, feel free to code the relevant code from the [UUIDs class](src/main/java/com/atomfinger/touuid/UUIDs.java) if you're a Java developer, or the [ToUUIDExtensions script](src/main/kotlin/com/atomfinger/touuid/ToUUIDExtensions.kt) if you are a Kotlin developer.

## Contact
you can contact me through my [website](https://jmgundersen.com) or the social links found on said website.