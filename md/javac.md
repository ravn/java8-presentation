javac
===

New option "-parameters" saves the names of constructor and method parameters 
so they can be retrieved by reflection.

(UNTESTED: Helps java decompiler in IntelliJ?)

New option "-profile X" ensures that the source conforms to compact profile X
(a well defined subset of the JRE).

Based on 
https://blogs.oracle.com/jtc/entry/a_first_look_at_compact

* "compact1" is for simple command line programs.
* "compact2" is "compact1" + RMI/JDBC/XML
* "compact3" is "compact2" + JMX/JNDI/security/annotations

