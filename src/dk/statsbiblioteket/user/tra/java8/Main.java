package dk.statsbiblioteket.user.tra.java8;

import java.text.Collator;
import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * MAIN JAVADOC
 */

public class Main {


    public static void main(String[] args) {
        Collection<String> foo = new ArrayList<>();
        Iterator<String> it = foo.iterator();
        while (it.hasNext()) {
            doStuff(it.next());
        }

        for (String s : foo) {
            doStuff(s);
        }

        List<String> l = Arrays.asList("abc", "Bc", "a");
        l.sort(Comparator.naturalOrder());
        System.out.println(l); // [Bc, a, abc]
        Collections.sort(l);
        System.out.println(l); // [Bc, a, abc]
        Collections.sort(l, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.length() - o1.length();
            }
        });
        System.out.println(l); // [abc, Bc, a]
        Collections.sort(l, Collator.getInstance());
        System.out.println(l); // [a, abc, Bc]
        Optional<String> os = Optional.ofNullable(null);
        if (os.isPresent()) {
            System.out.println(os.get());
        }
        os.ifPresent(System.out::println);
        System.out.println(os.orElse("orElse"));
        Optional.of("Hello").ifPresent(System.out::println);
        l.sort(Comparator.naturalOrder());
        System.out.println(l);
        l.sort(Comparator.comparing(String::length));
        System.out.println(l); // [a, Bc, abc]

        {
            List<String> la = Arrays.asList("ad", "bc", "ba", "ac");
            // http://stackoverflow.com/a/24442897/53897
            // http://blog.jooq.org/2014/01/31/java-8-friday-goodies-lambdas-and-sorting/
            la.sort(Comparator.comparing((String e) -> e.substring(1, 2))
                    .thenComparing(e -> e.substring(0, 1)));
            System.out.println(la); // [ba, ac, bc, ad]
            la.sort(Comparator.comparing((String e) -> e.substring(1, 2))
                    .thenComparing(e -> e.substring(0, 1)).reversed());
            System.out.println(la); // [ad, bc, ac, ba]

            System.out.println(String.join(" ", "Hello", "World"));

            Logger log = Logger.getLogger("foo");
            Function<String, String> f = s -> {
                log.fine(s);
                return s;
            };
        }
        l.stream().forEach(e -> doStuff(e));
        {
            List<String> la = Arrays.asList("ad", "bc", "ba", "ac");
            la.stream().filter(s -> s.startsWith("a")).forEach(System.out::println);
        }

        {
            List<String> la = Arrays.asList("ad", "bc", "ba", "ac");
            System.out.println(la.stream().map(s -> s.startsWith("a")).collect(Collectors.toList()));
            System.out.println(la.stream().collect(Collectors.joining(", ")));
            Map<String, String> m = la.stream().collect(
                    Collectors.groupingBy(e -> e.substring(0,1),
                    Collectors.joining("+")));
            System.out.println(Arrays.toString(m.entrySet().toArray()));
        }
    }


    public static String doStuff(String s) {
        return ">" + s + "<";
    }
}
