![Build](https://github.com/atomfinger/toUUID/workflows/Build/badge.svg)

<img src="/images/logo.png" width="350">

.toUUID() is a tiny library for quickly generating UUIDs in automated tests for Java and Kotlin.

## Why .toUUID()?

Developers care about clean code, and automated tests are no exceptions. We should apply the same professional attitudes to our automated tests as we do our production code.
One of the commonly accepted principles is that unit tests should be easy to read and understand. Having UUIDs with hardcoded values such as `1b1d3a74-748a-4394-a9fb-88145de16d31` breaks the flow for the reader and makes the test harder to understand the code.

It is effortless to generate a random UUID in Java and Kotlin, but it is harder to generate a simple one, such as:  
`00000000-0000-0000-0000-000000000001`

To generate a simple UUID like the one above, traditionally, we must write something like this:

```java
UUID simpleUuid = UUID.fromString("00000000-0000-0000-0000-000000000001");
```

The issue with generating UUIDs this way is:

- It is ugly
- It harms readability in multiple ways
- It is tedious generating more than one UUID

.toUUID() attempts to mitigate this problem by being able to generate simple UUIDs based on integers.

## How .toUUID() works

.toUUID() takes the integer and puts it at the back of the UUID. Which is why the number:  
`1`  
turns into the UUID:  
`00000000-0000-0000-0000-000000000001`.

Here's some more examples:  
|Number input | UUID output |
|:---:|:---|
|`2`|`00000000-0000-0000-0000-000000000002`|
|`5`|`00000000-0000-0000-0000-000000000005`|
|`10`|`00000000-0000-0000-0000-000000000010`|
|`55`|`00000000-0000-0000-0000-000000000055`|
|`100`|`00000000-0000-0000-0000-000000000100`|
|`100000`|`00000000-0000-0000-0000-000000100000`|

## .toUUID() examples in Java

.toUUID() is first and foremost a Kotlin project. Still, one of the goals was to keep it free for any unnecessary dependencies, which is why Java users should use the [UUIDs class](src/main/java/com/atomfinger/touuid/UUIDs.java) to avoid having to deal with any extra dependencies.

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

If you are still hesitant, feel free to copy the relevant code from the [UUIDs class](src/main/java/com/atomfinger/touuid/UUIDs.java) if you're a Java developer, or the [ToUUIDExtensions script](src/main/kotlin/com/atomfinger/touuid/ToUUIDExtensions.kt) if you are a Kotlin developer.

## Contact

you can contact me through my [website](https://jmgundersen.com) or the social links found on said website.
