type annotations and checker
===

The `@Annotation` mechanism was previously only allowed at declarations, 
but has been enhanced in Java 8 to be allowed at any "type use". 
(A few examples of where types are used are class instance creation expressions (new), casts, implements clauses, and throws clauses. )

The idea appears to be to improve static checking.

The Oracle Tutorial recommends looking into the Checker framework for this. 


http://types.cs.washington.edu/checker-framework/
https://docs.oracle.com/javase/tutorial/java/annotations/type_annotations.html

FIXME:  Example with SQL injection checker etc.