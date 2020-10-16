# Why toUUID

## Table of contents

- [Why toUUID](#why-touuid)
  - [Table of contents](#table-of-contents)
  - [Introduction](#introduction)
    - [Testing is contextual](#testing-is-contextual)
    - [The setup](#the-setup)
      - [What we're writing tests for](#what-were-writing-tests-for)
  - [toUUID vs UUID.randomUUID()](#touuid-vs-uuidrandomuuid)
    - [Simple unit tests](#simple-unit-tests)
    - [Tests working with lists of data](#tests-working-with-lists-of-data)
    - [Tests which works with datasets](#tests-which-works-with-datasets)
      - [Value proposition of toUUID vs random UUIDs](#value-proposition-of-touuid-vs-random-uuids)
  - [toUUID vs new UUID(...)](#touuid-vs-new-uuid)
    - [Value proposition of toUUID vs new UUID()](#value-proposition-of-touuid-vs-new-uuid)
  - [toUUID vs UUID.fromString(...)](#touuid-vs-uuidfromstring)
    - [Static UUIDs](#static-uuids)
    - [Value proposition of toUUID vs UUID.fromString(...)](#value-proposition-of-touuid-vs-uuidfromstring)
  - [Where does it not make sense to use toUUID?](#where-does-it-not-make-sense-to-use-touuid)
  - [Misc](#misc)
    - [Random UUIDs can help to spot errors which don’t come up during regular testing](#random-uuids-can-help-to-spot-errors-which-dont-come-up-during-regular-testing)
    - [UUIDs are supposed to be random; it doesn’t make sense that they should be sequential](#uuids-are-supposed-to-be-random-it-doesnt-make-sense-that-they-should-be-sequential)
    - [Random values can discover unexpected errors in your code. This library makes automated tests less valuable](#random-values-can-discover-unexpected-errors-in-your-code-this-library-makes-automated-tests-less-valuable)
    - [Why not use UUIDs.of()/[insert naming convention]?](#why-not-use-uuidsofinsert-naming-convention)
    - [What if I mock the behaviour of UUID.randomUUID()?](#what-if-i-mock-the-behaviour-of-uuidrandomuuid)
  - [Conclusions](#conclusions)

## Introduction

When toUUID was first revealed, the most repeated feedback was that people didn’t seem to understand what the big deal was, or what the added value were, especially when compared to more traditional ways of generating UUIDs. It became clear that the benefit of toUUID isn’t obvious as initially assumed, which is why this document exists.

The goal of this document is to explain how toUUID brings value and where it doesn’t.

### Testing is contextual

Before going into concrete examples, we should probably discuss the fact that there are many wildly different views on how to conduct automated testing, and on what is appropriate or not. The point of toUUID isn’t to explicitly favour one over the other, rather be a tool available to developers which sees a use case for it in their test portfolio. It might be in unit, integration or system testing; or it might be in extensive E2E tests.

### The setup

Before continuing to the comparisons we should take a look at what we're working with.

To clean up the tests examples a little, we are using some extra libraries:

- [Lombok](https://projectlombok.org/): To generate getters, setters and constructors
- [AssertJ](https://joel-costigliola.github.io/assertj/): Makes the assertions slightly prettier
- [Mockito](https://site.mockito.org/): To mock values

The usage of these libraries doesn’t mean that toUUID only work with them. These libraries are just the ones decided to use when writing these examples. The points made about object creation, assertions and Mockito will mostly be the universal no matter which library is used.

#### What we're writing tests for

To make these examples a little more realistic, assume that we have a layered based architecture in a service for multiple schools that holds records for students.

A student class looks like this:

```java
@Data
@AllArgsConstructor
public class Student {
   private UUID studentId;
   private String school;
   private String course;
   private int graduationYear;
}
```

For this example we have two classes which we care about:

- StudentService
- StudentDao

The interface for the `StudentService` looks like this:

```java
public interface StudentService {

   /**
   * Finds a student that matches the given studentId \* @param studentId
   * @return
   */
   Student findStudent(UUID studentId);

   /**
   * Lists all students that takes a particular course at a particular school \* @param school
   * @param course
   * @return
   */
   List<Student> findStudentBySchoolAndCourse(String school, String course);
}
```

While the `StudentDao` class looks like this:

```java
public interface StudentDao {

    /**
     * Lists all students from a particular school
     * @param school
     * @return
     */
    List<Student> listStudentsBySchool(String school);

    /**
     * Lists students from a particular school, ordered by graduation year
     * @param school
     * @return
     */
    List<Student> listStudentsBySchoolOrderedByGraduationYear(String school);

    /**
     * Finds a partcular student based on its studentIs
     * @param studentId
     * @return
     */
    Student findStudent(UUID studentId);
}
```

Note that it doesn’t matter if one uses a traditional DAO, a Spring JPA repository or some other mechanic for getting data from a database. The fundamentals on how to test this DB layer will remain the same as far as toUUID is concerned.

## toUUID vs UUID.randomUUID()

The most common argument against toUUID is that toUUID solves a problem which doesn't exist, as random UUIDs are easy to use in tests without making the test flaky or unpredictable. The reasoning behind this argument is entirely correct, as testing with random values is entirely feasible (and not that difficult), but it is also missing the point of toUUID.

toUUID isn’t claiming that one cannot make useful tests with random values, instead toUUID says that there, mostly, isn't a need to use random UUIDs in tests.

### Simple unit tests

Tests that are already very easy to write is probably a category where toUUID shines the least. There not an objective improvement using toUUID over regular random UUIDs in these scenarios.

Consider the following test:

```java
Student student = new Student(UUID.randomUUID(), "Harvard", "CS");
when(mockedStudentDao.findStudent(student.studentId)).thenReturn(student);
Student result = studentService.findStudent(student.studentId);
assertThat(result).isEqualTo(student);
```

alternatively, some might have written the test above as such:

```java
UUID studentId = UUID.randomUUID();
Student student = new Student(studentId, "Harvard", "CS");
when(mockedStudentDao.findStudent(studentId)).thenReturn(student);
Student result = studentService.findStudent(studentId);
assertThat(result).isEqualTo(student);
```

The test above we see that a random UUID is generated and held as a variable. If we take a look at the same test written with toUUID:

```java
Student student = new Student(toUUID(1), "Harvard", "CS");
when(mockedStudentDao.findStudent(toUUID(1))).thenReturn(student);
Student result = studentService.findStudent(toUUID(1));
assertThat(result).isEqualTo(student);
```

or alternatively:

```java
UUID studentId = toUUID(1);
Student student = new Student(studentId, "Harvard", "CS");
when(mockedStudentDao.findStudent(studentId)).thenReturn(student);
Student result = studentService.findStudent(studentId);
assertThat(result).isEqualTo(student);
```

These scenarios show that there's no direct benefit of toUUID vs a random UUID. Neither approach is meaningfully more complex than the other. Some might prefer calling the `mockedStudentDao` with an actual value, but that is subjective. If the entire use case is just simple tests, like the ones above, then toUUID is more an aesthetic choice rather than a tool which adds much value.

### Tests working with lists of data

While toUUID matches random UUIDs in simple scenarios, it does better when we have to deal with multiple UUIDs, especially when we have to assert with lists. Assume that we have been tasked with writing tests for the `listStudentsBySchool` method in `StudentService` class.

A test with toUUID could look like the following:

```java
// arrange
List<Student> students = Arrays.asList(
    new Student(toUUID(1), "Harvard", "CS", 1994),
    new Student(toUUID(2), "Harvard", "Fine art", 1994), //<- Student to be filtered out
    new Student(toUUID(3), "Harvard", "CS", 1994),
    new Student(toUUID(4), "Harvard", "CS", 1994));
when(mockedStudentDao.listStudentsBySchool("Harvard")).thenReturn(students);

// act
List<Student> result = studentService.findStudentBySchoolAndCourse("Harvard", "CS");

// assert
assertThat(result)
   .extracting("studentId")
   .containsExactlyInAnyOrderElementsOf(toUUIDs(1, 3, 4));
```

Now consider how one would get the same result using randomly generated UUIDs:

```java
// arrange
List<Student> csStudents = Arrays.asList(
   new Student(UUID.randomUUID(), "Harvard", "CS", 1994),
   new Student(UUID.randomUUID(), "Harvard", "CS", 1994),
   new Student(UUID.randomUUID(), "Harvard", "CS", 1994));
ArrayList<Student> students = new ArrayList<>(csStudents);
students.add(1, new Student(UUID.randomUUID(), "Harvard", "Fine art", 1994)); //<- Student to be filtered out
when(mockedStudentDao.listStudentsBySchool("Harvard")).thenReturn(students);

// act
List<Student> result = studentService.findStudentBySchoolAndCourse("Harvard", "CS");

// assert
assertThat(result).containsExactlyInAnyOrderElementsOf(csStudents);
```

Neither of these two tests is majorly different in implementation and mostly comes down to preference. While the toUUID implementation has its test data nicely aligned, we will see later that this is not the primary benefit of toUUID, and there are instances where we want to separate the input and expected output.

The primary benefit toUUID has over random UUIDs is that it makes it easier to map error messages back to the source. Consider this output from the toUUID implementation:

```
Expecting:
  <[00000000-0000-0000-0000-000000000001,
    00000000-0000-0000-0000-000000000002,
    00000000-0000-0000-0000-000000000003,
    00000000-0000-0000-0000-000000000004]>
to contain exactly in any order:
  <[00000000-0000-0000-0000-000000000001,
    00000000-0000-0000-0000-000000000003,
    00000000-0000-0000-0000-000000000004]>
but the following elements were unexpected:
  <[00000000-0000-0000-0000-000000000002]>
```

If we look at the printout, we can easily see that it is the student with `studentId` `00000000-0000-0000-0000-000000000002` were wrongly included. It doesn’t matter whether we’re working with four students or 10000 students, finding the exact student when using deterministic values is easier.

If we look at the output from the test using random UUIDs, we get the following:

```
Expecting:
  <[Student(studentId=b8cebb33-24d3-4999-94f8-ac5f9a512343, school=Harvard, course=CS, graduationYear=1994),
    Student(studentId=3f21c74c-c8c6-440f-aef0-9638dbb5abbc, school=Harvard, course=Fine art, graduationYear=1994),
    Student(studentId=e949d779-4ee9-4684-bad3-6ad7a99a74eb, school=Harvard, course=CS, graduationYear=1994),
    Student(studentId=df78fdc8-61ad-4b30-a08a-e5a0acb69a44, school=Harvard, course=CS, graduationYear=1994)]>
to contain exactly in any order:
  <[Student(studentId=b8cebb33-24d3-4999-94f8-ac5f9a512343, school=Harvard, course=CS, graduationYear=1994),
    Student(studentId=e949d779-4ee9-4684-bad3-6ad7a99a74eb, school=Harvard, course=CS, graduationYear=1994),
    Student(studentId=df78fdc8-61ad-4b30-a08a-e5a0acb69a44, school=Harvard, course=CS, graduationYear=1994)]>
but the following elements were unexpected:
  <[Student(studentId=3f21c74c-c8c6-440f-aef0-9638dbb5abbc, school=Harvard, course=Fine art, graduationYear=1994)]>
```

Let's for a moment appreciate that this test is tiny in scope and had only a single element that should be excluded. If this test fails, it is straightforward to identify which piece of data that is wrong. Consider how this would look if the list contained 10/20/30/etc students rather than just four. Ask yourself which approach makes it easier to identify the source of the error.

The reason we don't assert on the UUIDs directly in our random UUID test is that we can't do so in any meaningful way. It doesn't make sense to assert on random values as they cannot be mapped back to the source if something goes wrong, so we're forced to match the whole object. We know that our implementation of `listStudentsBySchool` filters on a list provided by `StudentDao`, so in this scenario, we only care whether or not the correct object has been excluded. What we see with the previous tests is that we are forced to asserting on other values, often whole objects, where one identifier would be enough when dealing with random values; alternatively save the state of these random values. Both approaches are unnecessary in this situation.

The more complex dataset, models and logic are, the more difficult it becomes to identify which exact object that is causing the issue. Using toUUID can make this process easier and scales better as complexity grows.

Some might look at the previous example and claim false equivalency. After all, the random UUID test does verify the whole object, while the toUUID version doesn't. There are often good reasons for ascertaining the state of an object vs just a single field. While this exact scenario doesn't require it, here is the toUUID version implemented in the same way as random UUID version:

```java
// arrange
List<Student> csStudents = Arrays.asList(
    new Student(toUUID(1), "Harvard", "CS", 1994),
    new Student(toUUID(2), "Harvard", "CS", 1994),
    new Student(toUUID(3), "Harvard", "CS", 1994));
ArrayList<Student> students = new ArrayList<>(csStudents);
students.add(1, new Student(toUUID(4), "Harvard", "Fine art", 1994)); //<- Student to be filtered out
when(mockedStudentDao.listStudentsBySchool("Harvard")).thenReturn(students);

// act
List<Student> result = studentService.findStudentBySchoolAndCourse("Harvard", "CS");

// assert
assertThat(result).containsExactlyInAnyOrderElementsOf(csStudents);
```

Which generates the following output:

```
Expecting:
  <[Student(studentId=00000000-0000-0000-0000-000000000001, school=Harvard, course=CS, graduationYear=1994),
    Student(studentId=00000000-0000-0000-0000-000000000004, school=Harvard, course=Fine art, graduationYear=1994),
    Student(studentId=00000000-0000-0000-0000-000000000002, school=Harvard, course=CS, graduationYear=1994),
    Student(studentId=00000000-0000-0000-0000-000000000003, school=Harvard, course=CS, graduationYear=1994)]>
to contain exactly in any order:
  <[Student(studentId=00000000-0000-0000-0000-000000000001, school=Harvard, course=CS, graduationYear=1994),
    Student(studentId=00000000-0000-0000-0000-000000000002, school=Harvard, course=CS, graduationYear=1994),
    Student(studentId=00000000-0000-0000-0000-000000000003, school=Harvard, course=CS, graduationYear=1994)]>
but the following elements were unexpected:
  <[Student(studentId=00000000-0000-0000-0000-000000000004, school=Harvard, course=Fine art, graduationYear=1994)]>
```

The main difference now is that `studentId` isn't just wasting space; instead, the `studentId` is something we can use to identify which exact student that is wrongly included or missing. To find the original piece of data, we only need to look at the `studentId`, rather than finding something in the list of other properties which can be used to identify a student. While our `Student` class only has four properties, consider for a moment that entities often has substantially more. When writing tests like this, the main benefit of toUUID is that the first field is a deterministic value which can be used to identify the source.

### Tests which works with datasets

If there is one lesson to take from the previous section, it is that deterministic IDs allow us to identify the source of the data using the ID. This will also be one of the main benefits when working with larger datasets as well. In this scenario, we will have a look at integration tests, specifically for the database layer. In this scenario, we have been tasked with implementing tests for the `listStudentsBySchoolOrderedByGraduationYear` method.

To make such a test work we must then insert a dataset in some way. There's essentially two ways of inserting dataset into a database for test purposes:

1. Inserting the data manually
2. Inserting the data from a SQL or XML file

Inserting the data manually will result in the same situation as our previous example.

Inserting the data from a file however provides some challenges. Assume that we end up with a table with the following rows:

| student_id                           | school  | course   | graduation_year |
| ------------------------------------ | ------- | -------- | --------------- |
| 00000000-0000-0000-0000-000000000001 | Harvard | CS       | 1994            |
| 00000000-0000-0000-0000-000000000002 | Yale    | CS       | 1995            |
| 00000000-0000-0000-0000-000000000003 | Harvard | Fine art | 1995            |
| 00000000-0000-0000-0000-000000000004 | Harvard | CS       | 1996            |

_(For the tests using random UUIDs I will assume that the `student_id` row contains a randomly generated UUID)_

One limitation forced upon us is that we cannot use the `student_id` to verify which students that's returned from the query. After all, random UUIDs are non-deterministic, so we must find ways of ascertaining the `listStudentsBySchoolOrderedByGraduationYear` query without checking the ID.

The first type of test can verify that we are indeed returning the expected number of items, and confirm that all of the returned students have gone to Harvard:

```java
List<Student> result = studentDao.listStudentsBySchoolOrderedByGraduationYear("Harvard");
assertThat(result)
    .hasSize(3)
    .extracting(school)
    .allMatch("Harvard");
```

If the test above fails, we get the following errors:

```
Expected size:<3> but was:<4> in:
<[Student(studentId=c2591a3c-4453-499a-8296-9e3de4ac8a60, school=Harvard, course=Fine art, graduationYear=1995),
    Student(studentId=342bf42b-ec18-48e4-a0f5-1657676bd8a6, school=Harvard, course=CS, graduationYear=1994),
    Student(studentId=4bec8586-33ec-480a-a105-ab9148711d0b, school=Yale, course=CS, graduationYear=1995),
    Student(studentId=4d681230-482e-4dc0-a141-49c2e18ba1ae, school=Harvard, course=CS, graduationYear=1996)]>
```

Or if we get the appropriate number of students, but one is going to a different school:

```
Expecting ArrayList:
  <["Harvard", "Yale", "Harvard"]>
to contain only:
  <["Harvard"]>
but the following element(s) were unexpected:
  <["Yale"]>
```

We are lucky that we are dealing with such a simple model for this example, as that helps us to identify that the second record is the odd one out, but do keep in mind that reading these error messages gets more complicated the bigger a model is. The first error message might give us a decent amount of information about what went wrong, but I’d argue that it is unnecessarily difficult to identify which record is the one that is causing issues. The critical, identifiable, bits of information is hidden in the middle of the error under the `school` property.  Such unintuitive messages might be okay for this test, but consider that most systems have more functionality and tests than our example, so this problem will only get worse as a system grows in size and complexity.

The second error message is much more problematic. Since we are, essentially, doing multiple asserts in a single test, it means that this error will only show up when the number of items asserts passes. This means that we don’t have the printout of the first test if this error shows up. It can be challenging to figure out which exact student was wrongly returned based on the second error alone, significantly more difficult when datasets grow bigger.

Another thing to consider is that `listStudentsBySchoolOrderedByGraduationYear` also has a specific order to it, `GraduationYear`. This is also something we must verify. In this scenario, we're forced to write this requirement as a separate test:

```java
List<Student> result = studentDao.listStudentsBySchoolOrderedByGraduationYear("Harvard");
assertThat(result)
    .extracting(graduationYear)
    .containsExactly(1994, 1995, 1996);
```

This test may generate an error like this:

```
Actual and expected have the same elements but not in the same order, at index 0 actual element was:
  <1995>
whereas expected element was:
  <1994>
```

With this error, we run into the same challenge as to where we checked the school. It can be challenging to trace the error back to where the exact students who are either missing or wrongly included.

When using toUUID, we can easily encapsulate both of these test cases into a single test:

```java
List<Student> result = studentDao.listStudentsBySchoolOrderedByGraduationYear("Harvard");
assertThat(result)
    .extracting(studentId)
    .containsExactly(toUUIDS(1, 3, 4));
```

The test above is much shorter than the other two, but the result is the same as the two combined. We know which order these students should be returned in, so we check the order of the IDs. If the order is out of place, or there is a student too much or too little, then an assertion error will be thrown.

Let's consider what the output of this test looks like:

Too many elements returned:

```
Expecting:
  <[00000000-0000-0000-0000-000000000001,
    00000000-0000-0000-0000-000000000003,
    00000000-0000-0000-0000-000000000003,
    00000000-0000-0000-0000-000000000004]>
to contain exactly (and in same order):
  <[00000000-0000-0000-0000-000000000001,
    00000000-0000-0000-0000-000000000003,
    00000000-0000-0000-0000-000000000004]>
but some elements were not expected:
  <[00000000-0000-0000-0000-000000000003]>
```

fewer elements than expected returned:

```
Expecting:
  <[00000000-0000-0000-0000-000000000001, 00000000-0000-0000-0000-000000000004]>
to contain exactly (and in same order):
  <[00000000-0000-0000-0000-000000000001,
    00000000-0000-0000-0000-000000000003,
    00000000-0000-0000-0000-000000000004]>
but could not find the following elements:
  <[00000000-0000-0000-0000-000000000003]>
```

Elements out of order:

```
Actual and expected have the same elements but not in the same order, at index 0 actual element was:
  <00000000-0000-0000-0000-000000000003>
whereas expected element was:
  <00000000-0000-0000-0000-000000000001>
```

By looking at the error messages, we can always see which exact student that is either missing, wrongly included or that is out of order. All three requirements of the method we’re verifying give a clean printout which tells the developer precisely what is needed: Which data that is wrong and why. We also achieve this with toUUID with a single test and less code.

The only way to achieve similar results without toUUID is by doing the following:

```java
List<Student> expected = Arrays.asList(
   new Student(null, "Harvard", "Fine art", 1995),
   new Student(null, "Harvard", "CS", 1994),
   new Student(null, "Harvard", "CS", 1996));
List<Student> result = studentDao.listStudentsBySchoolOrderedByGraduationYear("Harvard");
assertThat(result)
   .usingElementComparatorIgnoringFields("studentId")
   .containsExactlyElementsOf(expected);
```

Which returns assertion messages such as:

```
Expecting:
  <[Student(studentId=9b98ca65-ee88-4398-b02e-9e8880a8e254, school=Harvard, course=Fine art, graduationYear=1995),
    Student(studentId=92d6f0a7-9d63-4ac1-a348-41d54a77537d, school=Yale, course=CS, graduationYear=1995),
    Student(studentId=d154648c-a3e6-4216-938f-2243f3b3e61a, school=Harvard, course=CS, graduationYear=1996)]>
to contain exactly (and in same order):
  <[Student(studentId=44ab4f0f-f816-4a2b-bff0-d9bd58199d04, school=Harvard, course=CS, graduationYear=1994),
    Student(studentId=bb309fa7-f1ee-4b6f-a1d3-7c34eafd9e80, school=Harvard, course=Fine art, graduationYear=1995),
    Student(studentId=4bce79e5-4459-4298-9375-95af9bb90011, school=Harvard, course=CS, graduationYear=1996)]>
but some elements were not found:
  <[Student(studentId=44ab4f0f-f816-4a2b-bff0-d9bd58199d04, school=Harvard, course=CS, graduationYear=1994)]>
and others were not expected:
  <[Student(studentId=92d6f0a7-9d63-4ac1-a348-41d54a77537d, school=Yale, course=CS, graduationYear=1995)]>
when comparing values using field/property by field/property comparator on all fields/properties except ["studentId"]
Comparators used:
- for elements fields (by type): {Double -> DoubleComparator[precision=1.0E-15], Float -> FloatComparator[precision=1.0E-6]}
- for elements (by type): {Double -> DoubleComparator[precision=1.0E-15], Float -> FloatComparator[precision=1.0E-6]}
```

This approach might seem fair for smaller models, like the one used in this example, but it doesn’t scale that well. Another issue with this approach is that it requires more code to achieve similar results. Every time we add a new student to our dataset, which changes the result for this test means that we must also hardcode a new student in our tests.

Another thing to consider is that the test works with two different sets of UUIDs. Consider this line:
```
but some elements were not found:
  <[Student(studentId=44ab4f0f-f816-4a2b-bff0-d9bd58199d04, school=Harvard, course=CS, graduationYear=1994)]>
```

The UUID presented in the message above doesn't exist in our dataset. It quickly becomes confusing when some IDs are present in the dataset, while others are randomly generated.

There's an argument to why we might want to assert on whole objects rather than a specific field, and toUUID doesn't prevent developers from doing so. In those instances, just like with our previous section's list test, toUUID makes it easier to identify the source record while the exact implementation looks very similar to the one implemented using random UUIDs.

#### Value proposition of toUUID vs random UUIDs

- toUUID makes it easier to identify the records that triggered the test fault
- toUUID gives developers other ways of expressing their tests
- toUUID allows developers to write tests without holding a state of non-deterministic values
- toUUID can cut down on the amount of code required when testing against a dataset

## toUUID vs new UUID(...)

The UUID constructor takes to longs, and we can manipulate this to generate simple UUIDs as well:

```java
UUID simpleUUID = new UUID(0, 1); //00000000-0000-0000-0000-000000000001
```

As with random UUIDs, this approach can also get the job done. The main limitation of this approach is that when we go higher than the value nine, we have to deal with hex values.

The main benefit of toUUID vs `new UUID()` is that toUUID allows you to use the whole integer range, which makes it more suitable in situations like various integration tests where one might want to deal with larger datasets.

### Value proposition of toUUID vs new UUID()

- toUUID scales past nine values without forcing users to use hex

## toUUID vs UUID.fromString(...)

`UUID.FromString()` is a good method when one wants a concrete UUID value:

```java
UUID simpleUUID = UUID.fromString("00000000-0000-0000-0000-000000000001");
```

toUUID and UUID.fromString() is, essentially, ending with the same result. In terms of results, these two approaches are identical.

The main difference between the two approaches lies in readability and intricacy:

- _Readability:_ While the assertion printouts of UUID.fromString() can be as pretty as the developer chooses it to be, it also clutters the code. Our human eyes tend to favour repeating symbols, and UUID.fromString() tend to take focus away from the rest of the test, especially when dealing with multiple UUIDs.
- _Intricacy:_ While the value itself is easy enough, it is not very intuitive to write for a developer. To get a valid UUID, a developer must remember where to put the dashes and how many zeros there are in the string. Neither of which is very intuitive. This tends not to be a massive issue as developer copies and pasts these IDs around to avoid writing it manually.

### Static UUIDs

There is another approach to the UUID.fromString() formula, and that is by making them static in a test class:

```java
class UUIDs {
   public static UUID UUID_1 = UUID.fromString("00000000-0000-0000-0000-000000000001");
   public static UUID UUID_2 = UUID.fromString("00000000-0000-0000-0000-000000000002");
   public static UUID UUID_3 = UUID.fromString("00000000-0000-0000-0000-000000000003");
   // etc
}
```

Making UUIDs static this way will get the job done. The only downsides compared to toUUID is:

- Not dynamic. If you want a new UUID, you have to add it yourself
- Not able to create UUIDs in ranges and will require more space when making a list of UUIDs
- Some might object to the naming of these variables

These downsides might not be something which everyone has an objection against, and that is fine. The main difference is whether one wants a general static approach or a dynamic one.

### Value proposition of toUUID vs UUID.fromString(...)

- toUUID is shorter
- toUUID is dynamic

## Where does it not make sense to use toUUID?

Where toUUID falls short is also by design: It is terrible at being unique. Since toUUID promises no real uniqueness, it also means that toUUID cannot be trusted in situations where we want to avoid collisions. Situations like concurrent E2E tests or simultaneously running integration tests are not suitable for toUUID.

toUUID is also not good at situations where non-deterministic values are required.

## Misc

These are some other concerns which have been leveraged at toUUID which warrants a discussion. View this section as a catch-all for whatever argument that has not already been in the previous sections.

### Random UUIDs can help to spot errors which don’t come up during regular testing

Spotting random edge case tests with UUIDs is rarely the case. First of all, let’s agree that random values can help to uncover edge cases in a system’s logic, but business logic is very rarely directly tied to arbitrary values, even less so when it comes to identifiers like UUID. The majority of systems out there sees UUID as a value, but very rarely they have any logic tied to that value (other than passing it around).

After all, UUIDs are inherently random, which means it doesn’t make sense to have business logic tied to specific UUID values. Most systems use UUIDs as an ID for some entity, which is an appropriate use case. Since the business logic is unlikely to do anything with the UUID, other than a single ID for an entity, then it generally doesn’t matter whether or not the UUID was initially randomly generated or not.

### UUIDs are supposed to be random; it doesn’t make sense that they should be sequential

UUIDs are indeed supposed to be random in the context of a unique identifier. However, tests have special code with special rules. Best practices for production code might not be the best practices for test code. After all, developers tend to mock behaviour even though it would be considered bad practise doing so in production code. We sometimes even mock static behaviour which changes bytecode on the fly, which would be a terrible way of breaking encapsulation in production.

Developers break these rules to get code into a test harness, or to make tests easier to manage. While UUIDs are supposed to be random in production, does not automatically mean that UUIDs must be random in during test.

Whether or not toUUID justifies breaking how UUIDs usually works is up to each developer to decide for themselves. The gain is simpler, dynamic and reusable UUIDs, which for some might be worth breaking a few rules for. Consider that toUUID isn’t breaking any rules that UUID.fromString() isn’t already breaking.

The same goes for whether or not it makes sense for a given implementation to use deterministic values or not; this is something the developer must decide.

### Random values can discover unexpected errors in your code. This library makes automated tests less valuable

Yes, but only if there's logic tied to the exact value of a UUID. The lifespan of UUIDs tend to be the following:

- Being passed around as an identifier of an entity
- Being passed to the database to create, save, update or read an entity (or as a list of entities)
- Being written to file or returned from service to serve as an identifier for an entity

The long and short of it is that UUIDs tend to be passed around, and its value rarely gets touched directly.

We might connect entities using UUIDs, but there is often very little logic tied to the specific value itself. This means that most code supports the whole range of UUIDs existing UUIDs, at which point it doesn't matter what value a given UUID has generated. In regular code, one will never be able to uncover an error due to a randomly generated UUID.

For the vast majority of code, the only time a randomly generated UUID in tests can fail is when there's specific logic dealing with the UUID value (at which point toUUID isn't suitable), or when it tries to access an entity which doesn't exist.

The point is, random vs static values doesn't matter if one doesn't apply logic to the values. If there's nothing that transforms UUIDs or does some calculations based on UUIDs, then it doesn't matter what specific value that a UUID holds. It does not matter whether it was hardcoded or not.

### Why not use UUIDs.of()/[insert naming convention]?

Java has a bunch of different patterns when it comes to naming:

- `.valueOf()` like in [String.ValueOf()](<https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/String.html#valueOf(boolean)>)
- `.of()` like in [EnumSet.of()](<https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/EnumSet.html#of(E)>)
- `toSomething()` like in [Object.toString()](<https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/Object.html#toString()>)

When picking a pattern it should make contextual sense.

If we go with `.valueOf()` the result would be this:

```java
UUID uuid = UUIDs.valueOf(1);
```

This is fine, but if we statically import this method then it doesn't make sense:

```java
UUID uuid = valueOf(1);
```

While it might fit a Java pattern, it doesn’t make sense in this case. The class name `UUIDs` makes it seem like we are generating more than a single UUID. Even worse, it is easy to read this naming as a UUID generator where we can specify the number of UUIDs we want, rather than an integer to UUID converter.

`of()` introduces similar concerns as `.valueOf()`.

`toUUID` however does work both in static and non-static situations (though static is preferred):

```java
UUID uuid = UUIDs.toUUID(1);
UUID uuids = UUIDs.toUUIDs(1, 2);

//...

UUID uuid = toUUID(1);
UUID uuids = toUUIDs(1, 2);
```

The added benefit is that it is clear when we want to generate a single UUID, and when we want to create multiple ones.

### What if I mock the behaviour of UUID.randomUUID()?

While that would work, it is essentially just moving the issue. The UUID still must be created in some way. Consider that `.randomUUID()` is a static method, which means that one has to use a very invasive type of mocking to make it happen, like Jmockit or PowerMock. While these types of mocking libraries are exceptional in their own right, they do break encapsulation entirely as it rewrites the bytecode on the fly. This might be acceptable to some; to others, it might not be. I don't think it is worth overriding `.randomUUID()` as there are plenty of more straightforward and less invasive options such as `toUUID`, `randomUUID()` and static UUIDs.


## Conclusions

One of the goals of toUUID is to be the one-stop-shop for UUID generation of deterministic UUIDs. Some find the underlying purpose of toUUID to be flawed, but throughout this text, we have seen some clear benefits of deterministic values in the context of automated testing.

If you still don't see the value of toUUID, then that is fine. This library isn't what defines you as a good or a bad developer; instead, toUUID is a tool which some might want to use to write, what they deem to be, cleaner tests. 

If you see a use-case for toUUID, then great! If you have any issues or suggestions for improvements, [do get in touch](jmgundersen.net)!