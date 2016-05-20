# JwIUTils

Java Utilities Library.

# Functional Improvements

###### Binary Operators

BiBinaryOperator
StackBiBinaryOperator

###### Collectors

BiCollector

###### Comparators

BiComparator

###### Consumers

TriConsumer

###### Functions

BiToDoubleFunction
BiToIntFunction
BiToLongFunction

QuadFunction
TriFunction

## Bi Stream

Light implementation of Java 8 Streams for things like Map.

**This implementation don't works like Java 8 Streams**

### Map Stream

Implementation of **BiStream**

##### Creating Map Stream

BiStream<K, V> MapStream.of(Map<K, V>)

##### Examples

Example 1:
```java
// Priority Person Map
Map<Priority, Person> personMap = new HashMap<>();

// Put persons
personMap.put(Priority.LOW, new Person("Jay", 33));
personMap.put(Priority.HIGH, new Person("Amy", 15));
personMap.put(Priority.NORMAL, new Person("Mark", 19));

// Create stream
BiStream<Priority, Person> biStream = MapStream.of(personMap);

// Print elements

biStream.forEach(Readme::print); // Terminal Operation

System.out.println();
System.out.println("Sort by priority");

// Sort by priority
MapStream.of(personMap)
// Compare priorities
.sorted((priority, person, priority2, person2) -> priority.compareTo(priority2))
// Print
.forEach(Readme::print);


System.out.println();
System.out.println("Sort by age");

// Sort by age
MapStream.of(personMap)
// Compare ages
.sorted((priority, person, priority2, person2) -> Integer.compare(person.getAge(), person2.getAge()))
// Print
.forEach(Readme::print);

System.out.println();
System.out.println("Sort by name");

// Sort by name
MapStream.of(personMap)
// Compare Person names
.sorted((priority, person, priority2, person2) -> person.getName().compareTo(person2.getName()))
// Print
.forEach(Readme::print);
```

#### Java Stream vs Bi Streams

Java Stream only run operations when you call a Terminal Operation

Jw BiStreams run operations when you call them.

Jw BiStreams have Terminal Operations too, but the Jw BiStreams Terminal Operations only close the stream.

Test code:

```java
static final Map<Integer, String> map = new HashMap<>();

static {
    map.put(11, "B");
    map.put(12, "C");
    map.put(10, "A");
}

@Test
public void javaStream() {
    System.out.println("Java Stream");

    map.entrySet().stream().filter(integerStringEntry -> {
        printStack(Reflection.getCallInformations());

        return integerStringEntry.getKey() < 0;
    }).forEach(System.out::println);
}

@Test
public void jwBiStream() {
    System.out.println("Jw BiStream");

    MapStream.of(map).filter((integer, s) -> {
        printStack(Reflection.getCallInformations());

        return integer < 0;
    }).forEach(JavaStreamvBiStream::print);
}

private static void print(Integer integer, String s) {
    System.out.printf("Integer = %d, String = %s%n", integer, s);
}

private static void printStack(StackTraceElement[] callInformations) {
    for (StackTraceElement callInformation : callInformations) {
        System.out.println(callInformation);
    }
}
```

###### Stack of JavaStream

```
...
com.github.jonathanxd.iutils.JavaStreamvBiStream.lambda$javaStream$0(JavaStreamvBiStream.java:29)
java.util.stream.ReferencePipeline$2$1.accept(ReferencePipeline.java:174)
java.util.HashMap$EntrySpliterator.forEachRemaining(HashMap.java:1691)
...
com.github.jonathanxd.iutils.JavaStreamvBiStream.javaStream(JavaStreamvBiStream.java:32)
```

**forEachRemaining** Called filter.

###### Stack of Jw BiStream

```
...
com.github.jonathanxd.iutils.JavaStreamvBiStream.lambda$jwBiStream$1(JavaStreamvBiStream.java:40)
com.github.jonathanxd.iutils.function.stream.walkable.WalkableNodeBiStream.filter(WalkableNodeBiStream.java:102)
com.github.jonathanxd.iutils.JavaStreamvBiStream.jwBiStream(JavaStreamvBiStream.java:39)
...

```

**WalkableNodeBiStream.filter** is called when you call the **filter** method.

**BiStream doens't supports parallel operations.**

#### Cons and Pros of BiStream

BiStream is a sugar for a Java Stream of Entry<K, V>, but, BiStream is developed with focus in maps, BiStream is more faster than Java Stream.

Pros:
- Performance
- Uses Bi Functions
- Focused on Maps

Cons:
- No Parallel Operations
- And, there is not a really Stream (doesn't match Stream specifications).
