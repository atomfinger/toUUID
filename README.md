# toUUID

toUUID is a small, easy to use, library for generating UUIDs in unit and integration tests.

## Why toUUID

UUIDs are often used in distributed system, but they complicate writing test cases. How often have we not seen this in our tests:

```java
UUID firstId = UUID.fromString("00000000-0000-0000-0000-000000000001");
UUID secondId = UUID.fromString("00000000-0000-0000-0000-000000000002");
//...
```

In tests we often like to use easy to read UUIDs, but there's no easy and clean way of writing these UUIDs in a repeatable fashion. Or, it wasn'nt, but not it is.

## How toUUID works

## How to use

### Generate a single UUID

#### Kotlin

```kotlin
val id = 1.toUUID()
println(id.toString())
//00000000-0000-0000-0000-000000000001 
```

#### Java

```Java
UUID id = TestUUID.fromInt(1);
System.out.println(id);
//00000000-0000-0000-0000-000000000001 
```

### Generate a list of UUIDs

#### Kotlin

```kotlin
val uuids = listOf(1, 2, 3, 4, 5).toUUIDs()
for (uuid in uuids)
  println(uuid.toString())
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

#### Java
```Java
UUID uuids = TestUUID.fromInts(Arrays.asList(1, 2, 3, 4, 5));
for (uuid in uuids)
  System.out.println(uuid.toString());
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

## Generate a range of UUIDs
```kotlin
val uuids = (1...5).toUUIDs()
for (uuid in uuids)
  println(uuid.toString())
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```

## Dynamically generate a set of UUIDs

```kotlin
val uuids = UUID.take(5).toList()
for (uuid in uuids)
  println(uuid.toString())
//00000000-0000-0000-0000-000000000001
//00000000-0000-0000-0000-000000000002
//00000000-0000-0000-0000-000000000003
//00000000-0000-0000-0000-000000000004
//00000000-0000-0000-0000-000000000005
```