Annotations
===

Annotations can now be added to almost any type usage (JSR 308).
This is metadata - not runnable code.

Repeating annotations
---

It is now possible to have the same annotation applied multiple times 
(if the annotation is annotated itself with `java.lang.annotation.Repeatable`)
without having to wrap the multiple annotations in a container annotation.
In Java 8 this is being done automatically by the compiler.

    package repeatingannotations;
    import java.lang.annotation.Repeatable;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
     
    public class RepeatingAnnotations {
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
 
        public static void main(String[] args) {
            Manufacturer[] a = Car.class.getAnnotationsByType(Manufacturer.class );
            System.out.println("Number of car manufacturers is "+ a.length );
            System.out.println("\n-------Printing out Car Manufacturers--------");
            Cars cars = Car.class.getAnnotation(Cars.class);
            for(Manufacturer car: cars.value()){
                System.out.println(car.value());
            }
        }
    }




https://blog.idrsolutions.com/2015/03/java-8-repeating-annotation-explained-in-5-minutes/

Static checking using annotations:
---

The JVM itself does not yet enforce any kind of annotations on source code.   Note that annotations
mentioned in the following may be in different packages and not immediately interchangeable.


Eclipse:


IntelliJ:

* @NotNull - null value is forbidden to return (for methods) and hold (for local variables  and fields).
* @Nullable - null value is perfectly valid to return (for methods), pass to (for parameters) and hold (for local variables and methods)
* @NonNls - string is not to be internationalized.
* @Contract - hint the compiler about input and return values.
* @ParametersAreNonnullByDefault - annotate a method once, instead of all parameters with @NotNull.

Also respects JSR-305 and FindBugs annotations directly if part of the project.

Annotations may be put outside Java source in an annotations.xml file (needs to be
configured in the SDK).  

* Null analysis can be done with "Analyze | Infer Nullity" and appropriate annotations added. 


Checker framework checks at compile time: 

* @NotNull - flag if null is being assigned.
* @ReadOnly - flag any attempt to change the object.
* @Regex - is this string assigned a valid regular expression _string_?
* @Tainted + @Untainted - avoid mixing data that does not go together like user input being used in system commands, or
sensitive data in log statements.
* @m - ensure units are dealt with properly.

