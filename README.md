<div align="center">
<img src="/images/logo-medium.png" width="400">

![Github CI Status](https://github.com/atomfinger/toUUID/workflows/Main%20build/badge.svg)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/io.github.atomfinger/atomfinger-touuid/badge.svg)](https://maven-badges.herokuapp.com/maven-central/io.github.atomfinger/atomfinger-touuid)

_A tiny library for quickly generating UUIDs in automated tests for Java and Kotlin._
-----
</div>

---
<br/>

 Table of content:
- [Install](#install)
- [Examples](#examples)
  - [Java](#java)
  - [Kotlin](#kotlin)
- [Why .toUUID()?](#why-touuid)
  - [How .toUUID() works](#how-touuid-works)
- [Demo projects](#demo-projects)
- [How to build](#how-to-build)
- [Contact](#contact)

# Install

**Maven**

```xml
<dependency>
    <groupId>io.github.atomfinger</groupId>
    <artifactId>atomfinger-touuid</artifactId>
    <version>1.0.0</version>
    <scope>test</scope>
</dependency>
```

**Gradle**

(Using the Maven Central Repository)

```yml
testCompile group: 'io.github.atomfinger', name: 'atomfinger-touuid', version: '1.0.0'
```

# Examples

## Java

.toUUID() is first and foremost a Kotlin project. Still, one of the goals was to keep it free for any unnecessary dependencies, which is why Java users should use the [UUIDs class](src/main/java/io/github/atomfinger/touuid/UUIDs.java) to avoid having to deal with any extra dependencies.

UUID from a single integer:

```java
import static io.github.atomfinger.touuid.UUIDs.*;

UUID uuid = toUUID(1);
System.out.println(uuid.toString());
//Output:
//00000000-0000-0000-0000-000000000001
```

UUID from a list of integers:

```java
import static io.github.atomfinger.touuid.UUIDs.*;

List<UUID> uuids = toUUIDs(Arrays.asList(1, 2, 3, 4, 5));
uuids.forEach((it) -> System.out.println(it.toString()));
//Output:
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

UUID from varargs:

```java
import static io.github.atomfinger.touuid.UUIDs.*;

List<UUID> uuids = toUUIDs(1, 2, 3, 4, 5);
uuids.forEach((it) -> System.out.println(it.toString()));
//Output:
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

UUID from range of integers:

```Java
import static io.github.atomfinger.touuid.UUIDs.*;

List<UUID> uuids = toUUIDsFromRange(1, 5);
uuids.forEach((it) -> System.out.println(it.toString()));
//Output:
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

## Kotlin

Kotlin has access to all the feature Java has, but also has the addition of extension functions.

Generate based on an integers:

```kotlin
val uuid = 1.toUUID()
println(uuid.toString())
//Output:
//00000000-0000-0000-0000-000000000001
```

Generate from a list of integers:

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

Generate based on a range of integers:

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

Generate based on a sequence of integers:

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

# Why .toUUID()?

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
|2|`00000000-0000-0000-0000-000000000002`|
|5|`00000000-0000-0000-0000-000000000005`|
|10|`00000000-0000-0000-0000-000000000010`|
|55|`00000000-0000-0000-0000-000000000055`|
|100|`00000000-0000-0000-0000-000000000100`|
|100000|`00000000-0000-0000-0000-000000100000`|



# Demo projects

Both Kotlin and Java has demo projects:

- [Kotlin demo project](demo/kotlin-demo/)
- [Java demo project](demo/java-demo/)

# How to build

.toUUID is a standard Maven application:

1. Clone project
1. Run `mvn clean install` in the project folder

Requirements:

- Java 1.8 or higher
- the Kotlin compiler

# Contact

you can contact me through my [website](https://jmgundersen.com) or the social links found on said website. Don't hesitate to ask if there are any questions :)
