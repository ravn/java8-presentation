interfaces
===

default methods
---

Interfaces have had a reputation for being set in stone for a very long time.
It was impossible to add new methods without breaking existing code as these
new methods needed to be implemented too.

This has now been solved using "default methods".  A default method has an
implementation directly in the interface, and does not _have_ to be
implemented (but can) when instantiated.  

An example:   `java.util.List` has so far needed to be sorted with
Collections.sort(list) but now has a sort(..) method (which takes a Comparator)
which is implemented as a default method in the interface.

        List<String> l = Arrays.asList("abc", "Bc", "a");
        l.sort(Comparator.naturalOrder());
        System.out.println(l); // [Bc, a, abc]
        
The default implementation in `java.util.List` looks like:

    default void sort(Comparator<? super E> c) {
        Object[] a = this.toArray();
        Arrays.sort(a, (Comparator) c);
        ListIterator<E> i = this.listIterator();
        for (Object e : a) {
            i.next();
            i.set((E) e);
        }
    }


Note 1: This allows inheriting code in a given class from more places than just
the super class in the same way as Traits do in Scala.

Note 2: Implementing multiple interfaces defining the exact same default 
method is a compilation error.

Note 3: You cannot define variables in an interface so you cannot keep state in
default methods except by passing in a state keeping object.

static methods:
---
This works just like static methods on classes.  The benefit is that they 
are available just by implementing the class.

A good example is the new `Comparator.comparing` class which makes it easy to 
create a comparator by providing a method reference or lambda expression which
returns a Comparable.

        List<String> l = Arrays.asList("abc", "Bc", "a");
        l.sort(Comparator.comparing(String::length));
        System.out.println(l); // [a, Bc, abc]


A more complex Comparator working on two fields could look like (here to sort a deck
of cards descending by rank and ordering same ranks by suit):

        myDeck.sort(
            Comparator.comparing(Card::getRank)
                .reversed()
                .thenComparing(Comparator.comparing(Card::getSuit)));
                
(example stolen from Oracle tutorial https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html )
