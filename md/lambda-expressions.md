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
a functional interface (an interface with exactly one abstract method, optionally marked with `@FunctionalInterface`)
or an assignment.

Scope:
---
Lambdas use lexical scope in the same way as a normal {}-delimited block, so as with
anonymous classes it is allowed to
refer to variables outside the lambda if they are "final or effectively final".

Variables can be overridden if needed.

"Effectively final variables" mean variables or parameters
whose values are never changed after they are initialized - the `final` keyword is not 
required.

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

IntelliJ 15+:

* Convert method reference to lambda expression and back.
* Convert anonymous type to method reference.
* Add inferred lambda parameter types.
* Shows javadoc for method implemented with cursor on `->` or `::` and Ctrl-Q.
* Add type and parenthesis to single inferred parameter -`s->{}` -> `(String s)->{}`
* Debugger supports lambda expressions


@FunctionalInterface
---

Interfaces _intended_ to be functional interfaces can explicitly be annotated with
`@FunctionalInterface`.  If so, compilers are required to
generate an error message unless:

* The type is an interface type and not an annotation type, enum, or class.
* The annotated type satisfies the requirements of a functional interface (meaning it
  has exactly _one_ abstract method)

However, the compiler will treat any interface meeting the definition of a
functional interface as a functional interface regardless of whether or not a
FunctionalInterface annotation is present on the interface declaration.

`java.*` functional interfaces in the JRE
---
* java.awt.KeyEventDispatcher
* java.awt.KeyEventPostProcessor
* java.io.FileFinder
* java.io.FilenameFilter
* java.lang.Runnable
* java.lang.Thread.UncaughtExceptionHandler
* java.nio.file.DirectoryStream.Filter
* java.nio.file.PathMatcher
* java.time.temporal.TemporalAdjuster
* java.time.temporal.TemporalQuery
* java.util.Comparator
* java.util.concurrent.Callable
* java.util.function.BiConsumer
* java.util.function.BiFunction
* java.util.function.BinaryOperator
* java.util.function.BiPredicate
* java.util.function.BooleanSupplier
* java.util.function.Consumer
* java.util.function.DoubleBinaryOperator
* java.util.function.DoubleConsumer
* java.util.function.DoubleFunction
* java.util.function.DoublePredicate
* java.util.function.DoubleSupplier
* java.util.function.DoubleToIntFunction
* java.util.function.DoubleToLongFunction
* java.util.function.DoubleUnaryOperator
* java.util.function.Function
* java.util.function.IntBinaryOperator
* java.util.function.IntConsumer
* java.util.function.IntFunction
* java.util.function.IntPredicate
* java.util.function.IntSupplier
* java.util.function.IntToDoubleFunction
* java.util.function.IntToLongFunction
* java.util.function.IntUnaryOperator
* java.util.function.LongBinaryOperator
* java.util.function.LongConsumer
* java.util.function.LongFunction
* java.util.function.LongPredicate
* java.util.function.LongSupplier
* java.util.function.LongToDoubleFunction
* java.util.function.LongToIntFunction
* java.util.function.LongUnaryFunction
* java.util.function.ObjDoubleConsumer
* java.util.function.ObjIntConsumer
* java.util.function.ObjLongConsumer
* java.util.function.Predicate
* java.util.function.Supplier
* java.util.function.ToDoubleBiFunction
* java.util.function.ToDoubleFunction
* java.util.function.ToIntBiFunction
* java.util.function.ToIntFunction
* java.util.function.ToLongBiFunction
* java.util.function.ToLongFunction
* java.util.function.UnaryOperator
* java.util.logging.Filter
* java.util.prefs.PreferenceChangeListener

Note that the many variants with Long/Int/Double/Binary is to support native non-object
types.  If you only use objects you can ignore them.  The Bi-prefix means two
arguments instead of one.

