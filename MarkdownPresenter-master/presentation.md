Java 8
===

* Basics for all I could find of new stuff for Java 8.
* Some Java 7 too.

(This is markdown rendered on the fly as a web page presentation)



!

JVM stuff first...
---

![Zzzz!](/6a00d8341c858253ef00e54f60280d8834-640wi.jpg "horrified cat")



!
### `invokedynamic` byte code instruction added.

Allows user space code to help Hotspot to resolve
type information at runtime instead of compile time.

Is very helpful to "duck typing"-languages like JRuby.

Also used for creating the object representation for λ-expressions.

http://blog.headius.com/2008/09/first-taste-of-invokedynamic.html
!

### Garbage Collection

Permgen is replaced with
Metaspace which use native memory instead of
a fixed size pool.

Can grow _much_ bigger!

* `-verbose:gc` - activate simple GC logging.
* `-XX:+PrintGCDetails` - activate detailed GC logging.
* `-Xloggc:<file>` - send GC log to _file_ instead of console.
* `-XX:+PrintGCDateStamps` - add date stamp to every line.
* `-XX:+PrintGCTimeStamps` - adds seconds since JVM start to every line.


!

### Garbage collectors

Default garbage collector is still Parallel (which can stop the world).

Concurrent-Mark-Sweep performs better due to background work but can still
stop the world.

Alternative G1 is for +4GB heaps which can do string deduplication. May be default
in Java 9.

!

### New tools

Java Packager - "performs task related to packaging and signing Java/JavaFX
applications".  JavaFX is a new GPU-powered UI for Java,
and is not covered in this presentation.

jdeps - static dependencies of applications and libraries.

Java Flight Recorder can collect low level data for Java Mission Control
to allow after-the-fact incident analysis. (Commercial, can be enabled at runtime)

Advanced Management Console can give an overview of Java applications
(and their JRE's) in an organization. Runs in WebLogic 12. (Commercial)



!

## Miscellaneous.

* Endorsed dirs mechanism and extension mechanism are deprecated as of 8u40.

* Scheduled regular security updates with predictable numbers.

* JRE expires automatically when the next security update is released.


!

Repeatable `@Annotations`
---

       @Retention( RetentionPolicy.RUNTIME )
        public @interface Cars {
            Manufacturer[] value() default{};
        }
        @Manufacturer("Mercedes Benz")
        @Manufacturer("Toyota")
        @Manufacturer("BMW")
        @Manufacturer("Range Rover")
        public interface Car { }
 
        @Repeatable(value = Cars.class )
        public @interface Manufacturer {
            String value();
        };

Note:  Several `@Manufacturer` annotations on `Car`
(silently put in a list).

!
Static checking using annotations:
---

The JVM itself does not yet enforce any kind behavior based on
annotations on source code (javac does).

Note that annotations mentioned in the following may be in different
packages and not immediately interchangeable.

!

IntelliJ 1/2:
---
Respects the following directly as part of the source analysis:

* `@NotNull` - null value is forbidden to return (for methods) and
  hold (for local variables and fields).
* `@Nullable` - null value is perfectly valid to return (for methods),
  pass to (for parameters) and hold (for local variables and methods)
* `@NonNls` - string is not to be internationalized.
* `@Contract` - hint the compiler about input and return values.
* `@ParametersAreNonnullByDefault` - annotate a method once, instead
  of all parameters with `@NotNull`.

Also respects JSR-305 and FindBugs annotations directly if part of the project.

Annotations may be put outside Java source in an annotations.xml file
(needs to be configured in the SDK).


!

IntelliJ 2/2
---

Null analysis can be done with "Analyze | Infer Nullity" and
appropriate annotations added.

Checker framework checks at compile time: 

* `@NotNull` - flag if null is being assigned.
* `@ReadOnly` - flag any attempt to change the object.
* `@Regex` - is this string assigned a valid regular expression _string_?
* `@Tainted` + `@Untainted` - avoid mixing data that does not go
together like user input being used in system commands, or sensitive
data in log statements.
* `@m` - ensure units are dealt with properly.


!

String
===

* String.substring() does not hold on to underlying char array.
* G1 Garbage Collection deduplicates strings.
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

Interfaces:
---


* default methods 
* static methods 

Interfaces can now hold code.  This helps adding functionality to
existing API's without breaking backward compatibility.

!

Interfaces - default methods:
---

It was impossible to add new methods to interfaces without breaking
existing code as these new methods needed to be implemented too.

A default method has an implementation directly in the interface, and
does not _have_ to be implemented (but can) when instantiated.
Example: java.util.List.sort(Comparator...).

        List<String> l = Arrays.asList("abc", "Bc", "a");
        l.sort(Comparator.naturalOrder());
        System.out.println(l); // [Bc, a, abc]

!

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

1. This allows multiple inheritance as Traits do in Scala.
2. Implementing multiple interfaces defining the exact same default 
method is a compilation error.
3. You cannot define variables in an interface so you cannot keep state in
default methods except by passing in a state keeping object.

!

Interfaces - static methods:
---

Works like for classes.  They are available just by implementing the
interface (which is beneficial for λ-expressions).  Comparator has
added ".comparing(...)" method.

(FIXME:  Better example.)

!

Of course this can be abused...

    public interface Test {
        static void main(String[] args) {
            System.out.println("I'm ok!");
        }
    }

http://stackoverflow.com/q/34710274/53897


!
Deep breath!
---

![z!](/computer-monitor-cat-2.jpg "ZZZ!")

!
It all comes together...
---

* λ-expressions
* Streams
* java.util.function.*

![Zzzz!](/bf3f4c4e4cbc909f957f939bb6bc7cc6.jpg "rainbow cat")

_Because sometimes, you need a rainbow butterfly unicorn kitten._




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


![Zzzz!](/ce547544ed6f035ab1b1ddef8d2388b8.jpg "sleepy cat")

!
