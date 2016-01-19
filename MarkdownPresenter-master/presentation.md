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

Example:
---

![](/Java8_Metaspace_dynamic_resize.png "java 8 gc output from article")

https://dzone.com/articles/java-8-permgen-metaspace

!

### Garbage collectors

Default garbage collector is still Parallel (which can stop the world).

Concurrent-Mark-Sweep performs better due to background work but can still
stop the world.

Alternative G1 is for +4GB heaps which can do string deduplication. May be default
in Java 9.  Rarely stops the world.

!

### New tools

Java Packager - "performs task related to packaging and signing Java/JavaFX
applications".  JavaFX is a new GPU-powered UI for Java,
and is not covered in this presentation.

jdeps - static dependencies of applications and libraries.

jjs - JVM-based JavaScript engine useful for scripting (more later)

Java Flight Recorder can collect low level data for Java Mission Control
to allow after-the-fact incident analysis. (Commercial, can be enabled at runtime)

Advanced Management Console can give an overview of Java applications
(and their JRE's) in an organization. Runs in WebLogic 12. (Commercial)

!

javac
---

New option `-parameters` saves the names of constructor and method
parameters so they can be retrieved by reflection.  New checkbox in
Eclipse.

New option `-profile X` ensures that the source conforms to compact
profile X (a well defined subset of the JRE).

* "compact1" is for simple command line programs.
* "compact2" is "compact1" + RMI/JDBC/XML
* "compact3" is "compact2" + JMX/JNDI/security/annotations


`https://blogs.oracle.com/jtc/entry/a_first_look_at_compact`

!

Nashorn - JSR-223 Javascript engine
---

Newly written for Java 8 to utilize `invokedynamic`.

* is 2-3 times slower than the highly optimized V8 engine after warmup.
* can easily be used for scripts with `jjs -scripting` .
* has full access to the standard Java libraries including JavaFX.
* can be used for configuration scripts (code as opposed to stringly typed properties) 
with the JSR-223 classes.
* Can use many Node.js packages.
* Netbeans 8+ and IntelliJ 13+ can be used for debugging.

!

Nashorn scripting:
---


    #!/usr/bin/env jjs -scripting

    print(<<EOD);
    ...${arguments[0]}---
    EOD

Full access to JVM including JavaFX.  Sweet spot is single-class-sized
application possibly with a GUI.  Main developer side project is
"Nasven" to put Maven artifacts on the classpath.

Does not yet appear to have reached critical mass or found a killer-application.


!

## Miscellaneous.

* Endorsed dirs mechanism and extension mechanism are deprecated as of 8u40.

* Scheduled regular security updates with predictable numbers.

* JRE expires automatically when the next security update is released.

!

But what can we do _in Java_ itself?
---

![](/Cute-Kitten-kittens-16096139-1280-800.jpg "ready!")

!

Path & Files
---
NIO2: FIXME: Write section on nio2.


!

new Date+Time API (JSR-310)
---

The JODA library has for a long time been recommended instead of
the default `Calendar` and `Date` classes, especially for differences.  

JSR-310 set out to produce a new set of date and
time handling classes for the standard Java library, where the obvious
choice was to adapt JODA, but the architect
disagreed with some of the design decisions, and took the opportunity to 
fix them.  JSR-310 was included in Java 8 under `java.time.*`.  

The JODA web site recommends
using the new routines instead of JODA for Java 8 onwards.  There is not 
an immediate upgrade path from JODA to `java.time.*` so some
manual work is needed to do so.

`http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html`

!


Highlights:

* Values are always immutable (also known as thread safe).
* Easy to express _durations_ useful relative to a given time.
* Machine time - `Instant` - is separated from human perception of time (like days in a calendar, clock on a wall).
* The concept of time _passing_ is abstracted out - `Clock` - making it much easier to write tests involving time.
* Chronologies are abstracted out, allowing non-standard calendars.  Useful in Japan and Thailand).

![z!](/67e00a0c98132e0be9f1574c8d86bf88.jpg "ZZZ!")

!

Method naming conventions 1/2:
---

* `of` - creates an instance  where the factory is primarily validating the input parameters, not converting them.
* `from ` - converts the input parameters to an instance of the target class, which may involve losing information from the input.
* `parse` - parses the input string to produce an instance of the target class.
* `format` - uses the specified formatter to format the values in the temporal object to produce a string.
* `get` - returns a part of the state of the target object.
* `is` - queries the state of the target object.

!

Method naming conventions 2/2:
---
* `with` - returns a copy of the target object with one element changed; this is the immutable equivalent to a set method on a JavaBean.
* `plus` - returns a copy of the target object with an amount of time added.
* `minus` - returns a copy of the target object with an amount of time subtracted.
* `to` - converts this object to another type.
* `at` - combines this object with another.


!

java.util.Date.toInstant()
---

To help bridge the gap between the old and new API’s, the venerable Date class 
now has a new method called toInstant() which converts the Date into the new 
representation. This can be especially effective in those cases where you're 
working on an API that expects the classic form, but would like to enjoy everything 
the new API has to offer.

    System.out.println(new java.util.Date().toInstant());
    // 2016-01-12T09:37:14.910Z

Note:  Z==Zulu/UTC time!

!

Bridging methods:
---


* `Calendar.toInstant()` converts the Calendar object to an Instant.
* `GregorianCalendar.toZonedDateTime()` converts a GregorianCalendar instance to a ZonedDateTime.
* `GregorianCalendar.from(ZonedDateTime)` creates a GregorianCalendar object using the default locale from a ZonedDateTime instance.
* `Date.from(Instant)` creates a Date object from an Instant.
* `Date.toInstant()` converts a Date object to an Instant.
* `TimeZone.toZoneId()` converts a TimeZone object to a ZoneId.

!



LocalDate + LocalTime + LocalDateTime
---

Note: `Month` and `DayOfWeek` enums can make code more readable. `Year` class has `isLeap()` method to hel
identify leap years.

* LocalDate represents a date as seen from the context of the observer, like a calendar on the wall.
* LocalTime represents a point in time as seen from the context of the observer, like a clock on the wall.
* LocalDateTime represents both.

Note that there is no trailing timezone indicator.

    LocalDateTime.now() // 2016-01-12T10:37:14.908
    LocalDate.of(2012, Month.DECEMBER, 12) // 2012-12-12
    LocalDate.ofEpochDay(150) // 1970-05-31
    LocalTime.of(17, 18) // 17:18
    LocalTime.parse("10:15:30") // 10:15:30

!


Adjustments can be made:

    LocalDate.now().withDayOfMonth(10).withYear(2010)
    // 2010-01-10
    LocalDate.now().plusWeeks(3).plus(3, ChronoUnit.WEEKS)
    // 2016-02-23

"Temporal adjusters" know of calendars.

    LocalDate.now()
    .with(java.time.temporal.TemporalAdjusters.lastDayOfYear())
    // 2016-12-31

!

Different precisions may be interesting, like rounding to the number of seconds or days
a given time corresponds to:

    LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)
    // 2016-01-12T00:00

An exception is thrown if the truncation unit is incompatible with the value.

!

Additionally the `MonthDay` class is useful for birthdays.  The
`YearMonth` class is well suited for credit card expiration dates.

All these classes are supported in JDBC by the getObject/setObject
methods, but do not have dedicated helper methods.

!

Timezones
---

Instructional video:

<https://www.youtube.com/watch?v=-5wpm-gesOY>


`ZoneOffset` is the period offset from UTC.

    ZoneOffset offset = ZoneOffset.of("+2:00");

`ZoneId` is an identifier for a time zone region.  Use "PLT" or longer like "Europe/Copenhagen".

`ZonedDateTime` is "a date and time with a fully qualified time zone.  This
 can resolve an offset at any point in time.  The rule of thumb is that if you want
 to represent a date and time without relying of the context of a specific server, you should
 use ZonedDateTime.

!

    ZoneId id = ZoneId.of("Europe/Copenhagen");
    ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), id);
    System.out.println(zdt);
    // 2016-01-12T12:45:16.923+01:00[Europe/Copenhagen]

Strings can be parsed:

    ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]")
    .truncatedTo(ChronoUnit.HOURS)
    // 2007-12-03T10:00+01:00[Europe/Paris]

Dates and times can be formatted:

    LocalDateTime.now().format(
        DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm"))
    // 2016-01-12 16:09

!


When needing to serialize the zoned date times, convert them to an `OffsetDateTime`, where the
timezone is resolved to an offset.

    ZoneId id = ZoneId.of("Europe/Copenhagen");
    ZonedDateTime zdt =
        ZonedDateTime.of(LocalDateTime.now(), id);
	
    OffsetDateTime odt = OffsetDateTime.from(zdt);
    System.out.println(odt); // 2016-01-12T12:52:49.665+01:00


There are many helper methods to massage these values further.

!

Periods 
---

"Periods represents a date-based value such as '3 months and a day', which is a distance on the timeline" in terms
of wall time.
Can be used for calculations, and to find the "difference" between two date/times.
Periods are aware of daylight savings time.

    Period period = Period.of(1,2,3);
    System.out.println(period);
    // P1Y2M3D  // 1 year, 2 months, 3 days
    System.out.println(LocalDateTime.now().plus(period));
    // 2017-03-15T13:04:08.969
    System.out.println(Period.between(LocalDate.now(),
        LocalDate.now().plusMonths(1))); // P1M

!

Durations
---

Durations are
like Periods, but time-based instead, and do not take daylight savings time in consideration.  For a Duration
a day is _always_ 24 hours.

    Duration duration = Duration.ofSeconds(3, 5);
    System.out.println(duration); // PT3.000000005S
    System.out.println(Duration.between(
        LocalTime.now(), LocalTime.now().plusMinutes(1)));
    // PT1M

!

Note that for differences the ChronoUnit enums have a `between(...)` method

    Instant then = Instant.now();
    Thread.sleep(0,50); // 50 nanoseconds
    System.out.println(
        ChronoUnit.NANOS.between(then, Instant.now()));
    // 1000000

(The tick resolution on Linux for Thread.sleep is 1 ms, so this is the minimum time
the scheduler will let the sleep last).

!

Clock
---

"Most temporal-based objects provide a no-argument `now()` method that
use the system clock and the default time zone, _and_ a one-argument
now(Clock) method that allows you to pass in an alternative Clock."

If for _any_ reason you cannot use the clock as-is from the underlying
operating system, using a `java.time.Clock` allows you to control it
fully.  Note there are two kinds, ticking and standing still:

* `Clock.offset(Clock, Duration)` returns a ticking clock that is offset by the specified Duration.
* `Clock.systemUTC()` returns a clock representing the Greenwich/UTC time zone.
* `Clock.fixed(Instant, ZoneId)` always return the same Instant.  For this clock, *time stands still*.

<https://docs.oracle.com/javase/tutorial/datetime/iso/clock.html>

!

Chronology + ChronoLocalDate + ChronoLocalDateTime + ChronoZonedDateTime
---

These classes support the needs of developers using non-ISO
calendaring systems.

_These classes are there purely for developers who are working on
highly internationalized applications that need to take into account
local calendaring systems, and they shouldn’t be used by developers
without these requirements. Some calendaring systems don’t even have a
concept of a month or a week and calculations would need to be
performed via the very generic field API._

!

Date and Time Formatting
---

_Although the java.time.format.DateTimeFormatter provides a powerful
mechanism for formatting date and time values, you can also use the
java.time temporal-based classes directly with java.util.Formatter and
String.format, using the same pattern-based formatting that you use
with the java.util date and time classes._

Left as an exercise for the interested reader O:)

!

Concurrency
---

<http://www.infoq.com/articles/Java-8-Quiet-Features>
<http://winterbe.com/posts/2015/05/22/java8-concurrency-tutorial-atomic-concurrent-map-examples/>

`StampedLock`: Fast optimistic lock, but if failing (which should be
rarely) you will have to redo your work.

`LongAdder`: Faster than AtomicLong (different way to handle contention).  

Parallel Sorting: `Arrays.parallelSort(myArray)` distributes over
cores.  Underlying implementation cannot be tuned, and performance
degrades under high load.

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

http://types.cs.washington.edu/checker-framework/

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

Now create a new String, instead of pointing to same underlying char
array as the original String, so the char array could not be garbage
collected even when the original String went out of scope.  Fixed in
Java 1.7.0_06.

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

Random numbers
---

"Java 8 has added a new method called `SecureRandom.getInstanceStrong()` 
whose aim is to have the JVM choose a secure provider for you. "

!

Exact Math
---

"In cases where the size is int or long and overflow errors need to be detected, 
the methods `addExact`, `subtractExact`, `multiplyExact`, and `toIntExact` throw an 
`ArithmeticException` when the results overflow. "

    Math.multiplyExact(1_000_000, 1_000_000);
    // Exception in thread "main" java.lang.ArithmeticException: integer overflow


http://docs.oracle.com/javase/8/docs/api/java/lang/Math.html

!

Process
---

Three new methods in the Process class -

* `destroyForcibly()` - terminates a process with a much higher degree of success than before.
* `isAlive()` tells if a process launched by your code is still alive.
* A new overload for `waitFor()` lets you specify the amount of time
  you want to wait for the process to finish. This returns whether the
  process exited successfully or timed-out in which case you might
  terminate it.


!

java.time.*
---

![z!](/d1619b87f6883eaeddd581f5d1184b79.jpg "ZZZ!")





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

Optional - defusing `null` values
---

FIXME: TEXT

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

A stream version of the example in the tutorial -
https://docs.oracle.com/javase/tutorial/datetime/iso/timezones.html
- of printing out
what time it is now in not-whole-hour-offset timezones:


    LocalDateTime now = LocalDateTime.now();
    ZoneId.getAvailableZoneIds().stream()
        .map(zoneid -> ZoneId.of(zoneid))
        .sorted(Comparator.comparing(Object::toString))
        .filter(zone -> now.atZone(zone).getOffset().getTotalSeconds() % (60*60) != 0)
        .forEach(zone -> System.out.println(zone + " " +  now.atZone(zone).getOffset()));

![Zzzz!](/ce547544ed6f035ab1b1ddef8d2388b8.jpg "sleepy cat")

!
