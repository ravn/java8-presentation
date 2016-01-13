JSR-310 - Date handling finally done right(?)
===

This is adapted from http://www.oracle.com/technetwork/articles/java/jf14-date-time-2125367.html

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


Method naming conventions:
---
(https://docs.oracle.com/javase/tutorial/datetime/overview/naming.html)

* `of` - creates an instance  where the factory is primarily validating the input parameters, not converting them.
* `from ` - converts the input parameters to an instance of the target class, which may involve losing information from the input.
* `parse` - parses the input string to produce an instance of the target class.
* `format` - uses the specified formatter to format the values in the temporal object to produce a string.
* `get` - returns a part of the state of the target object.
* `is` - queries the state of the target object.
* `with` - returns a copy of the target object with one element changed; this is the immutable equivalent to a set method on a JavaBean.
* `plus` - returns a copy of the target object with an amount of time added.
* `minus` - returns a copy of the target object with an amount of time subtracted.
* `to` - converts this object to another type.
* `at` - combines this object with another.

java.util.Date has toInstant()
---
FIXME: wording

To help bridge the gap between the old and new API’s, the venerable Date class 
now has a new method called toInstant() which converts the Date into the new 
representation. This can be especially effective in those cases where you're 
working on an API that expects the classic form, but would like to enjoy everything 
the new API has to offer.

        System.out.println(new java.util.Date().toInstant()); // 2016-01-12T09:37:14.910Z

Note:  Z==Zulu/UTC time!

* `Calendar.toInstant()` converts the Calendar object to an Instant.
* `GregorianCalendar.toZonedDateTime()` converts a GregorianCalendar instance to a ZonedDateTime.
* `GregorianCalendar.from(ZonedDateTime)` creates a GregorianCalendar object using the default locale from a ZonedDateTime instance.
* `Date.from(Instant)` creates a Date object from an Instant.
* `Date.toInstant()` converts a Date object to an Instant.
* `TimeZone.toZoneId()` converts a TimeZone object to a ZoneId.




LocalDate + LocalTime + LocalDateTime
---

Note: `Month` and `DayOfWeek` enums can make code more readable. `Year` class has `isLeap()` method to hel
identify leap years.

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

"Temporal adjusters" know of calendars.

    System.out.println(LocalDate.now().with(java.time.temporal.TemporalAdjusters.lastDayOfYear())); // 2016-12-31

Different precisions may be interesting, like rounding to the number of seconds or days
a given time corresponds to:

    System.out.println(LocalDateTime.now().truncatedTo(ChronoUnit.DAYS)); // 2016-01-12T00:00

Additionally the MonthDay class is useful for birthdays.  The YearMonth class
is well suited for credit card expiration dates.

All these classes are supported in JDBC by the getObject/setObject methods.




Timezones:
---

`ZoneOffset` is the period offset from UTC.

    ZoneOffset offset = ZoneOffset.of("+2:00");

`ZoneId` is an identifier for a time zone region.  Use "PLT" or longer like "Europe/Copenhagen".

`ZonedDateTime` is "a date and time with a fully qualified time zone.  This
 can resolve an offset at any point in time.  The rule of thumb is that if you want
 to represent a date and time without relying of the context of a specific server, you should
 use ZonedDateTime.

    ZoneId id = ZoneId.of("Europe/Copenhagen");
    ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), id);
    System.out.println(zdt); // 2016-01-12T12:45:16.923+01:00[Europe/Copenhagen]

Strings can be parsed:

    System.out.println(ZonedDateTime.parse("2007-12-03T10:15:30+01:00[Europe/Paris]").truncatedTo(ChronoUnit.HOURS)); // 2007-12-03T10:00+01:00[Europe/Paris]

Dates and times can be formatted:

    System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm"))); // 2016-01-12 16:09


When needing to serialize the zoned date times, convert them to an `OffsetDateTime`, where the
timezone is resolved to an offset.

    ZoneId id = ZoneId.of("Europe/Copenhagen");
    ZonedDateTime zdt = ZonedDateTime.of(LocalDateTime.now(), id);
    OffsetDateTime odt = OffsetDateTime.from(zdt);
    System.out.println(odt); // 2016-01-12T12:52:49.665+01:00

There are many helper methods to massage these values further.

A stream version of the example in the tutorial -
https://docs.oracle.com/javase/tutorial/datetime/iso/timezones.html
- of printing out
what time it is now in not-whole-hour-offset timezones:


    LocalDateTime now = LocalDateTime.now();
    ZoneId.getAvailableZoneIds().stream()
        .map(zoneid -> ZoneId.of(zoneid))
        .sorted(Comparator.comparing(Object::toString))
        .filter(zone -> now.atZone(zone).getOffset().getTotalSeconds() % (60*60) != 0)
        .forEach(zone -> System.out.println(zone + " " +  now.atZone(zone).getOffset()));

which prints:

    America/Caracas -04:30
    America/St_Johns -03:30
    Asia/Calcutta +05:30
    Asia/Colombo +05:30
    Asia/Kabul +04:30
    Asia/Kathmandu +05:45
    Asia/Katmandu +05:45
    Asia/Kolkata +05:30
    Asia/Pyongyang +08:30
    Asia/Rangoon +06:30
    Asia/Tehran +03:30
    Australia/Adelaide +10:30
    Australia/Broken_Hill +10:30
    Australia/Darwin +09:30
    Australia/Eucla +08:45
    Australia/North +09:30
    Australia/South +10:30
    Australia/Yancowinna +10:30
    Canada/Newfoundland -03:30
    Indian/Cocos +06:30
    Iran +03:30
    NZ-CHAT +13:45
    Pacific/Chatham +13:45
    Pacific/Marquesas -09:30
    Pacific/Norfolk +11:30

I could not easily figure out how to have both zone and now.atZone(zone) in the stream
so I had to look now.atZone(zone) up twice.  Suggestions welcome :)




Periods + Durations
---

"Periods represents a date-based value such as '3 months and a day', which is a distance on the timeline" in terms
of wall time.
Can be used for calculations, and to find the "difference" between two date/times.
Periods are aware of daylight savings time.

    Period period = Period.of(1,2,3);
    System.out.println(period); // P1Y2M3D  // 1 year, 2 months, 3 days
    System.out.println(LocalDateTime.now().plus(period)); // 2017-03-15T13:04:08.969
    System.out.println(Period.between(LocalDate.now(), LocalDate.now().plusMonths(1))); // P1M

Durations are
like Periods, but time-based instead, and do not take daylight savings time in consideration.  For a Duration
a day is _always_ 24 hours.

    Duration duration = Duration.ofSeconds(3, 5);
    System.out.println(duration); // PT3.000000005S
    System.out.println(Duration.between(LocalTime.now(), LocalTime.now().plusMinutes(1))); // PT1M


Note that for differences the ChronoUnit enums have a `between(...)` method

    Instant then = Instant.now();
    Thread.sleep(0,50); // 50 nanoseconds
    System.out.println(ChronoUnit.NANOS.between(then, Instant.now())); // 1000000

(The tick resolution on Linux for Thread.sleep is 1 ms, so this is the minimum time
the scheduler will let the sleep last).

Chronology/ChronoLocalDate/ChronoLocalDateTime/ChronoZonedDateTime
---

These classes support the needs of developers using non-ISO calendaring systems.

"These classes are there purely for developers who are working on highly internationalized applications that need to take into account local calendaring systems, and they shouldn’t be used by developers without these requirements. Some calendaring systems don’t even have a concept of a month or a week and calculations would need to be performed via the very generic field API."


Clock
---

FIXME: https://docs.oracle.com/javase/tutorial/datetime/iso/clock.html