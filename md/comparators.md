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

        List<String> l = Arrays.asList("abc", "Bc", "a");
        l.sort(Comparator.comparing(String::length));
        System.out.println(l); // [a, Bc, abc]


A more complex Comparator working on two fields could look like (here to sort a deck
of cards descending by rank and ordering same ranks by suit):

        myDeck.sort(
            Comparator.comparing(Card::getRank)
                .reversed()
                .thenComparing(Comparator.comparing(Card::getSuit)));
                
(example stolen from Oracle tutorial https://docs.oracle.com/javase/tutorial/java/IandI/defaultmethods.html )
