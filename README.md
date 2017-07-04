# JwIUtils [![](https://jitpack.io/v/JonathanxD/JwIUtils.svg)](https://jitpack.io/#JonathanxD/JwIUtils)

Java Utilities Library.

Provides a collection of common utilities for Java. (I add features when I need them, but you can send requests `:D`)

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

## Predicates

BiDoublePredicate `(double, double)`

BiIntPredicate `(int, int)`

BiLongPredicate `(long, long)`

DoubleObjBiPredicate `(double, T)`

IntObjBiPredicate `(int, T)`

LongObjBiPredicate `(long, T)`

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


#### Cons and Pros of BiStream

BiStream is a sugar for a Java Stream of Entry<K, V>, but, BiStream is developed with focus in maps, BiStream is more faster than Java Stream.

Pros:
- Performance
- Uses Bi Functions
- Focused on Maps

Cons:
- No Parallel Operations (supported via `Stream` wrapping)
- And, there is not a really Stream (doesn't match Stream specifications).

# View Collections (since 4.3.0)

In 4.3 we introduced view collections, view collections are collection wrappers which wrap actions to original list.

## Supported collection types

- Collection
- List
- Set

## Normal collection views

```
Collection<String> s = new ArrayList<String>();
s.add("A"); s.add("B"); s.add("C");

Collection<String> view = ViewCollections.collection(s);

view.forEach(System.out::println); // Prints: A, B and C.

s.add("Y");

view.forEach(System.out::println); // Prints: A, B, C and Y.
```

## Mapped collection views

```
Collection<List<String>> s = new ArrayList<List<String>>();
s.add(CollectionsUtils.listOf("A", "B", "C")); 
s.add(CollectionsUtils.listOf("D", "E", "F")); 
s.add(CollectionsUtils.listOf("G", "H", "I"));

Collection<String> view = ViewCollections.collection(s, 
        value -> value.listIterator(),
        iter, value -> ((ListIterator<?>) iter).add(value));

view.forEach(System.out::println); // Prints: A, B, C, D, E, F, G, H and I.

s.add("Y");

view.forEach(System.out::println); // Prints: A, B, C, D, E, F, G, H, I and Y.
```

The mapper function is called when a value is accessed, and it should return a `Iterator`.

The write function receives the iterator used to access the value and the value to add.