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


Use `peek(System.out::println)` to peek FIXME. 