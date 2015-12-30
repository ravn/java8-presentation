nashorn - javascript on the JVM
===

Oracle JDK has included a Javascript engine running on the JVM
since Java 6 accessible through the JSR-223 `javax.script` classes.

With the optimizations that has happened in the Javascript world over 
the last decade, an update was needed, using the `invokedynamic` mechanism
introduced in Java 7.  (Essentially that user code can help the JIT 
when processing non-Java programs).

The Nashorn engine 

* is 2-3 times slower than the highly optimized V8 engine after warmup.
* can easily be used for scripts with `jjs`.
* has full access to the standard Java libraries including JavaFX.
* can be used for configuration scripts (code as opposed to stringly typed properties) 
with the JSR-223 classes.
* Can use many Node.js packages.
* Netbeans 8+ and IntelliJ 13+ can be used for debugging.

scripting
---

    #!/usr/bin/env jjs -scripting

    print(<<EOD);
    ...${arguments[0]}---
    EOD

Full access to JVM including JavaFX.  Sweet spot is single-class-sized 
application possibly with a GUI.  
Main developer side project is "Nasven" to put Maven artifacts 
on the classpath.

FIXME:  Non-trivial JavaFX example.  Disk usage?

Does not yet appear to have reached critical mass or killer-application.