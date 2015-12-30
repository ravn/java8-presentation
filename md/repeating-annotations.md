repeating annotations
===

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

