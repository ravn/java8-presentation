lambda expressions:
===

Lambda expressions are related to anonymous classes which allows you
to provide an implementation of an interface without writing a whole new class,
but in a more concise way.

Note: The Oracle Java Tutorial section on Lambda expressions is not very well written.



Examples with a single statement:
---

    () -> 42
    () -> null
    
    (int x) -> x + 1
    (String s) -> "Hello " + s
    
    (int x, String s) -> x + " " + s
    
    (int a, int b, int c) -> a + b + c


Examples with a block:
---

    () -> { System.out.println(System.currentTimeMillis()) }
    
    (String s) -> { log.debug("{}", s);
                    return s;
                  }
    
If there is no return statement, it corresponds to a `void` method.

Examples with inferred parameter types:
---

    (x) -> x + 1
    (s) -> "Hello " + s
    (a, b, c) -> a + b + c
  
If there is only one parameter the parenthesis is optional:
    
    x -> x + 1
    s -> "Hello " + s
    s -> { log.debug("{}", s); return s; }

The compiler makes an effort to infer the type from the surrounding context, 
which either is
a functional interface or an assignment.

scope:
---
Lambdas use lexical scope in the same way as a normal {}-delimited block.  Variables 
can be overridden if needed.  "Effectively final variables" (variables or parameters 
whose values are never changed after they are initialized - the `final` keyword is not 
required) 
from the outside scope 
can be used inside the lambda expression.  

Note: Only "effectively final variables" 

Note: `this` is unchanged inside the lambda, and doesn't refer to the lambda itself!

IDE help:
---

Eclipse 4.5+ Quick Assists (Ctrl-1):

* Convert anonymous class to lambda expression and back.
* Convert method reference to lambda expression and back.
* Add inferred lambda parameter types.
* Remove/add parenthesis around single inferred parameter.
* Convert lambda expression body from expression to block and back.
* View method implemented by hovering mouse on `->` or `::`. Use Ctrl-hover to navigate to declaration.
* Debugger supports lambda expressions.

http://help.eclipse.org/luna/index.jsp?topic=%2Forg.eclipse.jdt.doc.user%2FwhatsNew%2Fjdt_whatsnew.html


IntelliJ:

* Convert method reference to lambda expression and back.
* Convert anonymous type to method reference.
* Add inferred lambda parameter types.
* 