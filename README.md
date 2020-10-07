

![Build](https://github.com/atomfinger/toUUID/workflows/Build/badge.svg)

![](/images/logo.png)

toUUID is a small, easy to use, library for generating UUIDs in unit and integration tests.

## Why toUUID

UUIDs are often used in distributed system, but they complicate writing test cases. How often have we not seen this in our tests:

```java
UUID firstId = UUID.fromString("00000000-0000-0000-0000-000000000001");
UUID secondId = UUID.fromString("00000000-0000-0000-0000-000000000002");
//...
```

In tests we often like to use easy to read UUIDs, but there's no easy and clean way of writing these UUIDs in a repeatable fashion. Or, it wasn'nt, but not it is.

## toUUID with Java

## toUUID with Kotlin

### Generate based on an integers
```kotlin
import com.atomfinger.touuid.extension.toUUID

fun printUUID() {
    println(1.toUUID().toString())
}
//Output:
//00000000-0000-0000-0000-000000000001
```

### Generate from a list of integers
```kotlin 
import com.atomfinger.touuid.extension.toUUIDs

fun printUUIDs() {
    listOf(1, 2, 3, 4, 5).toUUIDs().forEach { println(it.toString()) }
}
//Output:
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

### Generate based on a range of integers
```kotlin
import com.atomfinger.touuid.extension.toUUIDs
         
fun printUUIDs() {
    (1..5).toUUIDs().forEach { println(it.toString()) }
}
//Output:
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

### Generate based on a sequence of integers 

```kotlin 
import com.atomfinger.touuid.extension.uuids

fun printUUIDs() {
    uuids().take(5).forEach { println(it.toString()) }
}
//Output:
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```