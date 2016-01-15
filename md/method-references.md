Method references
===

Method references is a convenient shorthand for four different kind of lambda expressions.  The
method referred to have the same number of arguments as the corresponding lambda expression.

Reference to a static class:
* `e -> DefiningClass.staticMethod(e)`
* `DefiningClass::staticMethod`

Reference to a constructor:
* `e -> new DefiningClass(e)`
* `DefiningClass::new`

Reference to an instance of a particular object:
* `e -> myObject.method(e)`
* `myObject::method`

Reference to an instance method of an arbitrary object of a particular type:
* `(String a,String b) -> a.compareToIgnoreCase(b)`
* `String::compareToIgnoreCase`


    String[] stringArray = { "Barbara", "James", "Mary", "John",
        "Patricia", "Robert", "Michael", "Linda" };
    Arrays.sort(stringArray, String::compareToIgnoreCase);


Note: `System.out::println` is _very_ useful.