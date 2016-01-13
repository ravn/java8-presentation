invokedynamic
===

The `invokedynamic` byte code was added to Java 7 and is used extensively
in Java 8 with lambda expressions under the hood.  

The idea is to loosen the requirements for code so
that type information can be resolved at runtime instead of
inside `javac` at compile time.  Pure Java does not
like the "if it quacks like a duck, it is a duck" paradigm.

FIXME:  Explain how user space code helps the JIT compile
bytecode.

http://blog.headius.com/2008/09/first-taste-of-invokedynamic.html