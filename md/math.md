math
===

http://docs.oracle.com/javase/8/docs/api/java/lang/Math.html

"In cases where the size is int or long and overflow errors need to be detected, 
the methods `addExact`, `subtractExact`, `multiplyExact`, and `toIntExact` throw an 
`ArithmeticException` when the results overflow. "

    Math.multiplyExact(1_000_000, 1_000_000);
    // Exception in thread "main" java.lang.ArithmeticException: integer overflow
