comparators - easier now with default methods
===

Comparators are used for sorting.  Given two elements (a,b) the
comparator needs to return -1 if a<b, 0 if a==b and 1 if a>b.

    public class PersonComparator implements Comparator<? extends Person> {
    
      public int compare(Person p1, Person p2) {
         int nameCompare = p1.name.compareToIgnoreCase(p2.name);
         if (nameCompare != 0) {
            return nameCompare;
         } else {
           return Integer.valueOf(p1.age).compareTo(Integer.valueOf(p2.age));
         }
      }
    }

With the new default methods on the `Comparator` interface this can be done simpler with
the `Comparator.comparing()` class.

        List<String> la = Arrays.asList("ad", "bc", "ba", "ac");

        la.sort(Comparator.comparing((String e) -> e.substring(1,2))
                .thenComparing(e -> e.substring(0,1)));
        System.out.println(la); // [ba, ac, bc, ad]

        la.sort(Comparator.comparing((String e) -> e.substring(1,2))
                .thenComparing(e -> e.substring(0,1)).reversed());
        System.out.println(la); // [ad, bc, ac, ba]


See http://stackoverflow.com/a/24442897/53897 and
http://blog.jooq.org/2014/01/31/java-8-friday-goodies-lambdas-and-sorting/

static methods
---


Comparators -- helper class
---
