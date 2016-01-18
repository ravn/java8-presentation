## Java 8

FIXME: BLURB COMING HERE!!

- Update "presentation.md" by any text editor which you prefer.
 - to separate slides insert a paragraph with an exclamation mark.
- Hit space key at this browser to rapid reload.
  - F5 or Ctrl+R reload work well too.

!
String
===

* String.substring() does not hold on to underlying char array.
* Garbage Collection deduplicates strings.
* String.join(..) allows for easy concatenation of strings.

!

String.substring(...)
---
Note:  Important implementation change!

Now create a new String, instead of pointing to same underlying
char array as the original String, so the
char array could not be garbage collected even when the original String
went out of scope.  Fixed in Java 1.7.0_06.

http://stackoverflow.com/a/20275133/53897

Note:  substring is now O(n) instead of O(1). Discussion on impact
at https://www.reddit.com/comments/1qw73v

!

Garbage Collection
---

Permgen is replaced with Metaspace which uses
native memory instead of a fixed size pool.  Can
grow much bigger.

Default GC is Parallel (which can stop the world).
Alternatives are CMS and G1.

G1 is for +4GB heaps and can do String deduplication!

!

String.join(...)
---
New helper method.  First argument is separator, remaining arguments are joined with
the separator.

    System.out.println(
        String.join(" ", "Hello", "World")
    );

prints `Hello World`.

Use `Collectors.joining()` or `StringJoiner` for more advanced cases.

!

Deep breath!
---

![z!](/computer-monitor-cat-2.jpg "ZZZ!")


!
λ-expressions:
===

λ-expressions are related to anonymous classes so an interface
can be implemented without writing a whole new class, but in a more concise way.

_λ-calculus is a formal system in mathematical logic by Alonzo Church for expressing computation based on function abstraction and application using variable binding and substitution.
Lambda calculus is a universal model of computation equivalent to a Turing machine.
λ is used in lambda terms (also called lambda expressions) to **denote binding a
variable in a function**._

!
Remember
---

* λ-expressions are _declarations_, not _invocations_.   Actually invoking the
code must be done "outside" the lambda expression itself.

* FIXME: more stuff

!

A single statement:
---

    () -> 42
    () -> null

    (int x) -> x + 1
    (String s) -> "Hello " + s

    (int x, String s) -> x + " " + s

    (int a, int b, int c) -> a + b + c

Zero or more comma separated variable definitions in parenthesis, `->` and
an expression to be evaluated.

!

Block:
---

    () -> { System.out.println(
                System.currentTimeMillis()
            ) }

    (String s) -> { log.debug("{}", s);
                    return s;
                  }

If there is no return statement, it corresponds to a `void` method.

!

Inferred parameter types:
---

    (x) -> x + 1
    (s) -> "Hello " + s
    (a, b, c) -> a + b + c

If there is only one parameter the parenthesis is optional:

    x -> x + 1
    s -> "Hello " + s
    s -> { log.debug("{}", s); return s; }

The compiler makes an effort to infer the parameter types and the result type from
the surrounding context.  It may give up, and the parameters then have to
be explicitly typed.


!

Scope:
---
Lambdas use lexical scope in the same way as a normal `{}`-delimited block, so as with
anonymous classes it is allowed to
refer to variables outside the lambda if they are "final or effectively final".

Variables may be overridden if needed.

"Effectively final variables" mean variables or parameters
whose values are never changed after they are initialized - the `final` keyword is
_not_
required!

Note: `this` is unchanged inside the lambda, and doesn't refer to the lambda itself!

!
Interface underneath:
---

Regardless where a λ-expression is used, it _must_ implement an interface
with only one abstract method!

A λ-expression may only throw those exceptions declared in the interface.

The JRE has functional interfaces with up to two parameters (including native types).
None of these allow throwing checked exceptions.  Streams use these so
they don't accept these λ-expressions either.




!

IntelliJ 15+:
---

* Convert method reference to lambda expression and back.
* Convert anonymous type to method reference.
* Add inferred lambda parameter types.
* Shows javadoc for method implemented with cursor on `->` or `::` and Ctrl-Q.
* Add type and parenthesis to single inferred parameter - `s->{}` -> `(String s)->{}`
* Debugger supports lambda expressions inside chained method calls (like streams).

!

Eclipse 4.5+ Ctrl-1:
---

* Convert anonymous class to lambda expression and back.
* Convert method reference to lambda expression and back.
* Add inferred lambda parameter types.
* Remove/add parenthesis around single inferred parameter.
* Convert lambda expression body from expression to block and back.
* View method implemented by hovering mouse on `->` or `::`. Use Ctrl-hover to navigate to declaration.
* Debugger supports lambda expressions.


!
@FunctionalInterface
---

Interfaces _intended_ to be functional interfaces can explicitly be annotated with
`@FunctionalInterface`.  If so, compilers are required to
check:

* Is an interface (and not something else)
* Has exactly _one_ abstract method.

However, the compiler will treat _**any interface meeting the definition of a
functional interface as a functional interface**_ regardless of whether or not a
`@FunctionalInterface` annotation is present on the interface declaration.

!

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

!

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

!

* java.util.function.Function
* java.util.function.IntBinaryOperator
* java.util.function.IntConsumer
* java.util.function.IntFunction
* java.util.function.IntPredicate
* java.util.function.IntSupplier
* java.util.function.IntToDoubleFunction
* java.util.function.IntToLongFunction
* java.util.function.IntUnaryOperator

!

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

!

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

!

Deep breath! #2
---

![Zzzz!](/ce547544ed6f035ab1b1ddef8d2388b8.jpg "sleepy cat")

!

![Zzzz!](/bf3f4c4e4cbc909f957f939bb6bc7cc6.jpg "rainbow cat")

!

![Zzzz!](/6a00d8341c858253ef00e54f60280d8834-640wi.jpg "horrified cat")

