Concurrency
===

http://www.infoq.com/articles/Java-8-Quiet-Features
http://winterbe.com/posts/2015/05/22/java8-concurrency-tutorial-atomic-concurrent-map-examples/

StampedLock
---
Fast optimistic lock, but if failing (which should be rarely)
you will have to redo your work.



LongAdder
---
Faster than AtomicLong (different way to handle contention).  

"Atomics, 
uses a direct CPU compare and swap (CAS) instruction (via the sun.misc.Unsafe class) 
to try and set the value of a counter. 
The problem was that when a CAS failed due to contention, the AtomicInteger would spin, 
continually retrying the CAS in an infinite loop until it succeeded. At high levels of contention this could prove to be pretty slow."

Parallel Sorting
---

`Arrays.parallelSort(myArray);`

distributes over cores.  Details cannot be tuned, and performance degrades under high load.

