<div align="center">
<img src="/images/logo-medium.png" width="400">

![Main CI](https://github.com/atomfinger/toUUID/workflows/Main%20CI/badge.svg)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.atomfinger/atomfinger-touuid?color=dark-green&logoHeight=50)](https://search.maven.org/artifact/io.github.atomfinger/atomfinger-touuid/1.0.0/jar)

_A tiny library for generating UUIDs in automated tests for Java and Kotlin_

</div>

<br/>

 Table of contents:
- [Install](#install)
- [Examples](#examples)
  - [Java](#java)
  - [Kotlin](#kotlin)
- [Why toUUID?](#why-touuid)
- [How toUUID works](#how-touuid-works)
  - [:warning: Never use toUUID in production code :warning:](#warning-never-use-touuid-in-production-code-warning)
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

toUUID is first and foremost a Kotlin project. Still, one of the goals was to keep it free for any unnecessary dependencies, which is why Java users should use the [UUIDs class](src/main/java/io/github/atomfinger/touuid/UUIDs.java) to avoid having to deal with any extra dependencies.

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

Generate based on an integer:

```kotlin
val uuid = 1.toUuid()
println(uuid.toString())
//Output:
//00000000-0000-0000-0000-000000000001
```

Generate from a list of integers:

```kotlin
val uuids = listOf(1, 2, 3, 4, 5).toUuids()
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
val uuids = (1..5).toUuids()
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

# Why toUUID?

[Check out the writeup on why toUUID() is worth it](why_touuid.md)

# How toUUID works

toUUID takes the integer and puts it at the back of the UUID. Which is why the number:  
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

## :warning: Never use toUUID in production code :warning:

toUUID is not a replacement for how one normally generates UUIDs in production code. There are a [few versions of UUID](https://en.wikipedia.org/wiki/Universally_unique_identifier#Versions) which all have different algorithms attached to them, and this library bypasses all that. While toUUID generates technically valid UUIDs it does not create a UUID which is suitable for production (even if a random number is passed).

toUUID is to be used in automated testing or to generate a repeatable set of human-readable UUIDs.

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
