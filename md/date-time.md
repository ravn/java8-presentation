JSR-310 - Date handling finally done right(?)
===

Note:  This is a minimal presentation.  The full API seems a bit
over-engineered to me.

The JODA library has for a long time been recommended instead of
the default `Calendar` and `Date` classes, especially for differences.  

JSR-310 set out to produce a new set of date and
time handling classes for the standard Java library, where the obvious
choice was to adapt JODA, but the architect
disagreed with some of the design decisions, and took the opportunity to 
fix them.  JSR-310 was included in Java 8 under `java.time.*`.  

The JODA web site recommends
using the new routines instead of JODA for Java 8 onwards.  There is not 
an immediate upgrade path from JODA to `java.time.*` so some
manual work is needed to do so.

Highlights:

* Values are always immutable (also known as thread safe).
* Easy to express _durations_ which can then be used relative to a given time.
* Machine time - `Instant` - is separated from human perception of time (like days in a calendar, clock on a wall).
* The concept of time _passing_ is abstracted out - `Clock` - making it much easier to write tests involving time.
* Chronologies are abstracted out, making it easier to use different calendars than ISO-8601 if needed. (Apparently this is relevant in Japan and Thailand).


java.util.Date has toInstant()
---


To help bridge the gap between the old and new API’s, the venerable Date class 
now has a new method called toInstant() which converts the Date into the new 
representation. This can be especially effective in those cases where you're 
working on an API that expects the classic form, but would like to enjoy everything 
the new API has to offer.

        System.out.println(new java.util.Date().toInstant()); // 2016-01-12T09:37:14.910Z

Note:  Z==Zulu/UTC time!

LocalDate + LocalTime + LocalDateTime
---

* LocalDate represents a date as seen from the context of the observer, like a calendar on the wall.
* LocalTime represents a point in time as seen from the context of the observer, like a clock on the wall.
* LocalDateTime represents both.


    System.out.println(LocalDateTime.now()); // 2016-01-12T10:37:14.908
    System.out.println(LocalDate.of(2012, Month.DECEMBER, 12)); // 2012-12-12
    System.out.println(LocalDate.ofEpochDay(150));  // 1970-05-31
    System.out.println(LocalTime.of(17, 18)); // 17:18
    System.out.println(LocalTime.parse("10:15:30")); // 10:15:30

Note that this is _without_ timezone information as there is no
trailing timezone indicator.

Adjustments can be made:


    System.out.println(LocalDate.now().withDayOfMonth(10).withYear(2010)); // 2010-01-10
    System.out.println(LocalDate.now().plusWeeks(3).plus(3, ChronoUnit.WEEKS)); // 2016-02-23

"Temporal adjusters"" know of calendars.

    System.out.println(LocalDate.now().with(java.time.temporal.TemporalAdjusters.lastDayOfYear())); // 2016-12-31

Different precisions may be interesting, like rounding to the number of seconds or days
a given time corresponds to:

    System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)); // 2016-01-12T00:00




