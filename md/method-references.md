Method references
===

Method references is a convenient shorthand for four different kind of lambda expressions.

Reference to a static class
e -> DefiningClass.staticMethod(e)
DefiningClass::staticMethod

Reference to a constructor:
e -> new DefiningClass(e)
DefiningClass::new

Reference to a method in an interface: FIXME(THIS NEEDS MORE UNDERSTANDING)
e -> 



Useful method references:

System.out::println