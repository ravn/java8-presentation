JSR-310 - Date handling finally done right(?)
===

The JODA library has for a long time been recommended instead of
the default `Calendar` and `Date` classes, especially for differences.  

JSR-310 set out to produce a new set of date and
time handling classes for the standard Java library, where the obvious
choice was to adapt JODA, but the architect
disagreed with some of the design decisions, and took the opportunity to 
fix them.  JSR-310 was included in Java 8 under `java.time.*`.  

The JODA web site recommends
using the new routines instead of JODA for Java 8 onwards.  There is not 
an immediate upgrade path from JODA to `java.time.*`.

Highlights:

* Easy to express _durations_ which can then be used relative to a given time.
* Machine time is separated from human perception of time.
* The concept of time _passing_ is abstracted out, making it much easier to write tests involving time.


---

To help bridge the gap between the old and new API’s, the venerable Date class 
now has a new method called toInstant() which converts the Date into the new 
representation. This can be especially effective in those cases where you're 
working on an API that expects the classic form, but would like to enjoy everything 
the new API has to offer.




