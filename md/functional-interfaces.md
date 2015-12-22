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