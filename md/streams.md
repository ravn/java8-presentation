Streams - Iterators on steroids
===

The streams abstraction introduced in Java 8 is just another way to process a data
structure one item at a time with the added benefit that it is easy to chain operations
together (like UNIX pipes), and collect the result at the end (like in SQL).


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


Producers:
---

Most collections have had the `stream()` method added, but an infinite FIXME can be implemented.  

FIXME: int sum = Arrays.stream(myIntArray).sum();

Collecting:
---
A collector is responsible for collecting the elements in the stream into a datastructure
which is then returned.  This is typically a List, a Collection or joined into a String.

    List<String> la = Arrays.asList("ad", "bc", "ba", "ac");
    System.out.println(la.stream().map(s -> s.startsWith("a")).collect(Collectors.toList()));
    // [true, false, false, true]
    System.out.println(la.stream().collect(Collectors.joining(", ")));
    // ad, bc, ba, ac

    Map<String, String> m = la.stream().collect(
            Collectors.groupingBy(e -> e.substring(0,1),
            Collectors.joining("+")));
    System.out.println(Arrays.toString(m.entrySet().toArray()));
    // [a=ad+ac, b=bc+ba]

Static imports may shorten this a bit.


Selecting elements:
---

The `filter(java.util.function.Predicate<T>)` method on a Stream, filters out those
elements for which the predicate returns false.

    List<String> la = Arrays.asList("ad", "bc", "ba", "ac");
    la.stream().filter(s -> s.startsWith("a")).forEach(e -> System.out.println(e));
    // ad
    // ac
    
Note that the `e->System.out.println(e)` lambda expression can be replaced with the
`System.out::println` method reference, like this:

    la.stream().filter(s -> s.startsWith("a")).forEach(System.out::println);
    
FIXME: TakeAny, TakeOne. FIXME: HEAD?


Transforming:
---
The `map(Function<T,R>)` allows for converting a `Stream<T>` to a `Stream<R>`.



Sorting:
---

The `sorted()` method allows for sorting the stream elements at this point, either 
according to their natural order if `Comparable` or by providing a Comparator.

Note that this will cause all elements from upstream to be collected here and sorted, before 
providing the sorted elements again downstream breaking the lazy evaluation.




Peeking:
---
You may need to just _look_ at an element for any reason, usually debugging.  Use
the `peek(Consumer<? super T> action)` method for this.



Use `peek(System.out::println)` to peek FIXME. 







