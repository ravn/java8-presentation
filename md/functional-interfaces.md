Functional Interfaces - `Callable` on stereoids
===

It is frequently necessary to define code that is intended
to be run later and/or elsewhere in your code.  Examples include:

* Comparators
* Callables/Runnables
* FIXME:  Swing stuff

The general approach in Java has been to define a suitable interface,
implement the interface with the code snippet to be run, and pass
an instance of the interface implementation to the code which need to
call the snippet.

        List<String> l = Arrays.asList("abc", "bc", "a");
        Collections.sort(l);
        System.out.println(l); // [a, abc, bc]
        Collections.sort(l, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length()-o1.length();
            }
        });
        System.out.println(l); // [abc, bc, a]


The most tedious form of this so far was in the original EJB specification 
where it was used to so JVM 1 could invoke calls on JVM 2 running on 
another machine.  JVM 1 and JVM 2 both needed to know the interface to 
be implemented and stubs had to be written both by hand and auto generated.

@FunctionalInterface - make the compiler help
---
(text adapted from the javadoc) 

Interfaces _intended_ to be functional interfaces can explicitly be annotated with 
`@FunctionalInterface`.  If so, compilers are required to 
generate an error message unless:

* The type is an interface type and not an annotation type, enum, or class.
* The annotated type satisfies the requirements of a functional interface (meaning it
  has exactly _one_ abstract method)

However, the compiler will treat any interface meeting the definition of a 
functional interface as a functional interface regardless of whether or not a 
FunctionalInterface annotation is present on the interface declaration.

**This is great for lambda expressions**

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

Predicate - Yes/No function
---
(Apple Dictionary) _Logic:_ something which is affirmed or denied concerning an argument of a proposition.

So a `Predicate<X>` takes an argument of type `X` and returns a boolean.  Good for filtering a stream.

lambda: a -> a > 0

Supplier - (yet another Factory<Y>)
---
The `get()` method may be called an arbitrary number of times to get new Y values.  There
is no way to indicate there is no more values, making its usefulness limited.  The easiest
is typically to have a datatype containing the values or `IntStream.forRange(1, 1000)` instead.

lambda:  () -> 1

Function - given some arguments return a Y
---

Create an Y value based on the argument(s).  

lambda:  (a, b) -> a + b


Consumer - accept argument 
---

Endpoint in streams which use side-effects to expose results later.

