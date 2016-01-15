DO NOT USE - ESSENTIAL IS IN streams.md
===
!

Functional style - providing methods to apply to data
===

The Java language was deliberately designed to resemble C, which in 
turn was designed to be a portable assembly language for making it
easier to port UNIX to a new computer.  This mean that the Java mindset 
originally was relatively close to the C mindset, where you explicitly write code 
that work on whatever data you have, and you had a very small runtime library
to help you do so so you either had to implement your data structures yourself or
use an external library to do so.

This is where the `for(int i = 0; i < 10; i++) { ... }` approach comes from.

The Java architects started changing this with the Collections framework
which was introduced in Java 1.2 which essentially said "Don't care about the data structure
in your code (except at initialization time).  We got you covered.  Just think you are using a List, 
or a Set, or a Map, and don't worry about it."

But any time you wanted to do things like:

* Give me only the persons older than 18 years.
* Give each employer a 10% raise.
* Give me a list of "Lastname, Firstname" for all persons in this list.

you or the runtime library explicitly had to invoke a loop 
(either with `for` or with iterators) and generate the expected result which 
could then be passed on for further processing.

Other paradigms than the "write code to work on data" has arisen from academia.
 
The "Functional Programming" paradigm has previously been part of for example Google Guava
and has now been implemented in the JDK.

The idea is to leave all the details of iterating over collections and creating
the new data structures to the language itself, and instead focus on providing 
the code snippets doing what is actually needed.

This allows to write e.g. a sort in two lines:

--

Predicate - "yes/no"?
===

Snippet returns a boolean.  Useful for filtering.


Function -  A->B
===

Given input of type A, return a B.  Useful for transforming data.

Optional<A> - may hold an A or a null value.
===

Wrapper around an object making it much easier to avoid NullPointerExceptions.




    


 

