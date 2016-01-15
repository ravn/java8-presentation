Streams - SQL on collections
===

The streams abstraction introduced in Java 8 is Oracles implementation
of "map-reduce" in Java inspired by functional programming.  
It is just another way to process a data
structure one item at a time with the added benefit that it is easy to chain operations
together (like UNIX pipes), and reduce the result at the end (like in SQL).


Iterators (FIXME: 1995?) - process a data structure one item at a time.

        Collection<String> foo = new ArrayList<String>();
        Iterator<String> it = foo.iterator();
        while (it.hasNext()) {
            doStuff(it.next());
        }

`foreach` (FIXME: Java 5 2001?) - syntactic sugar on Iterators to make it resemble
a traditional for-loop.  Intellij knows how to convert the iterator-while to a foreach.

        Collection<String> foo = new ArrayList<String>();
        for (String s : foo) {
            doStuff(s);
        }

streams (Java 8 - 2014) - 

        l.stream().forEach(new Consumer<String>() {
            @Override
            public void accept(String e) {
                doStuff(e);
            }
        });

or

        foo.stream().forEach(e -> doStuff(e));
        
Note that the code now works with one element at a time and the actual loops are hidden 
in the library routines.

collection.stream()..collect(..) -  "SELECT ... FROM collection [GROUP BY ...]"
---

    List<String> l = Arrays.asList("abc", "Bc", "a");
    Stream<String> s = l.stream();

After processing the stream must end with a terminal operation, which frequently means reducing into a result
using the `.collect(...)` method.  (For very advanced uses by functional purists `.reduce(...)` can be used).


    List<String> la = Arrays.asList("ad", "bc", "ba", "ac");
    System.out.println(la.stream().collect(Collectors.toList()));
    // [ad, bc, ba, ac]
    System.out.println(la.stream().collect(Collectors.joining("/")));
    // ad/bc/ba/ac
    System.out.println(la.stream().collect(Collectors.toCollection(() -> new TreeSet<String>())));
    // [ac, ad, ba, bc]
    System.out.println(la.stream().collect(Collectors.groupingBy(e -> e.substring(0, 1))));
    // {a=[ad, ac], b=[bc, ba]}
    Map<String, String> m = la.stream().collect(
            Collectors.groupingBy(e -> e.substring(0,1),
            Collectors.joining("+")));
    System.out.println(Arrays.toString(m.entrySet().toArray()));
    // [a=ad+ac, b=bc+ba]

Various helpers:

    IntStream si = Arrays.stream(new int[] {1, 2, 3});
    LongStream sl = Arrays.stream(new long[] {1, 2, 3});
    DoubleStream sd = Arrays.stream(new double[] {1, 2, 3});

    System.out.println(Stream.of(1, 2, 3, 4).collect(Collectors.toList()));
    // [1, 2, 3, 4]

    System.out.println(IntStream.range(1, 10).boxed().collect(Collectors.toList()));
    // [1, 2, 3, 4, 5, 6, 7, 8, 9]
    System.out.println(IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList()));
    // [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

    new BufferedReader(...).lines()
    Files.lines(Path.get("..."), Charset.defaultCharSet())


Note: The `parallelStream()` method allows for transparently distributing the stream processing
over all cores.  Read up on the design issues before using!

Note:  By default streams are lazily evaluated.  This mean that the terminating code
asks "up the stream" for elements which are then generated as needed (as opposed to all the elements
being generated upstream at once).  Note that some operators like `sorted()` and `distinct()` require
all the elements to produce their result.

filter() - "WHERE..."
---

Select only some elements for further processing.  Takes a 
`java.util.function.Predicate<T>` argument which returns true if the element
is to be used, and false if it is to be ignored.

    List<String> la = Arrays.asList("ad", "bc", "ba", "ac");
    la.stream().filter(s -> s.startsWith("a")).forEach(e -> System.out.println(e));
    // ad
    // ac
    
Note that the `e->System.out.println(e)` lambda expression can be replaced with the
`System.out::println` method reference, like this:

    la.stream().filter(s -> s.startsWith("a")).forEach(System.out::println);

distinct() - "DISTINCT ..."
---

Only return any given value once from a stream, eliminating duplicates.

    List<String> la = Arrays.asList("ad", "bc", "ba", "ad", "bc");
    System.out.println(la.stream().distinct().collect(Collectors.toList()));
    // [ad, bc, ba]

limit(..), skip(...) - "LIMIT x"
---

    List<String> la = Arrays.asList("ad", "bc", "ba", "ad", "bc");
    System.out.println(la.stream().limit(4).collect(Collectors.toList()));
    // [ad, bc, ba, ad]
    System.out.println(la.stream().skip(2).collect(Collectors.toList()));
    // [ba, ad, bc]

findFirst(), findAny() - "LIMIT 1"
---

If you just need the first object use `findFirst()`. If you don't care which object use `findAny()` (works well
in parallel).

Note, this is wrapped in an `Optional` as null is returned if none were available.

    List<String> la = Arrays.asList("ad", "bc", "ba", "ad", "bc");
    la.stream().filter((String e) -> e.startsWith("b")).findAny().ifPresent(System.out::println);
    // bc


sorted() - "ASC"
---
The `sorted()` method allows for sorting the stream elements at this point, either 
according to their natural order if `Comparable` or by providing a Comparator.

Note that this will cause all elements from upstream to be collected here and sorted, before 
providing the sorted elements again downstream breaking the lazy evaluation.


    List<String> la = Arrays.asList("ad", "bc", "ba", "ac");
    System.out.println(la.stream().sorted().collect(Collectors.toList()));
    // [ac, ad, ba, bc]


map(...) - "doStuff(a) AS b"
---

Mapping means "apply some java.util.function.Function<K,V>() to" the element currently
in the stream.  The function may return native types which are autoboxed unless
the appropriate `mapToLong()`/`mapToInt()`/`mapToDouble()` is used instead.

    List<String> la = Arrays.asList("ad", "bc", "ba", "ad", "bc");
    System.out.println(la.stream().map(e->e.toUpperCase()).collect(Collectors.toList()));
    // [AD, BC, BA, AD, BC]
    System.out.println(la.stream().mapToLong(String::length).sum());
    // 10

    List<String> la = Arrays.asList("ad", "bc", "ba", "ac");
    System.out.println(la.stream().map(s -> s.startsWith("a")).collect(Collectors.toList()));
    // [true, false, false, true]
    System.out.println(la.stream().map(s -> s.startsWith("a")).collect(Collectors.toList()));
    // [true, false, false, true]

anyMatch(...), allMatch(...), noneMatch(...)  - "EXISTS" (kind of)
---

Given a stream, 

* `anyMatch()` will return true if *any* element in the stream makes the
expression true, false otherwise.
* `allMatch()` will return true if *all* elements in the stream makes the
expression true, false otherwise.
* `noneMatch()` will return true if *no* element in the stream makes the
expression true, false otherwise.

This is an easy way to analyse a complete result.

Peeking:
---
It can be very helpful to peek at the current element while debugging.
Use the `peek(Consumer<? super T> action)` method for this.

    List<String> la = Arrays.asList("ad", "bc", "ba", "ad", "bc");
    la.stream().filter((String e) -> e.startsWith("b")).peek(System.out::println).sorted().forEach(e->{});
    // bc
    // ba
    // bc



Exceptions:
---

The standard interfaces intended for Streams do _not_ throw any exception!

It was either this or `throws Exception` as the Java type system does not support
generics in the `throws` part of the method.

The official Word Of God is that the way to do this, is to either wrap
in a RuntimeException or a custom exception.



