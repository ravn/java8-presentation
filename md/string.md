String
===

GC does string deduplication.  When GC'ing cached String object reference hashvalues are 
compared and if matching the actual Strings are checked for equality.  If equal,
the GC uses one object for both references and discards the other.

String.substring()
---
Used to point to same underlying char array as the original String, so the
char array could not be garbage collected even when the original String
went out of scope.  Fixed in Java 1.7.0_06.

http://stackoverflow.com/a/20275133/53897

Note:  substring is now O(n) instead of O(1).  
Discussion on impact at https://www.reddit.com/comments/1qw73v

String.join(...)
---
New helper method.  First argument is separator, remaining arguments are joined with
the separator.

        System.out.println(String.join(" ", "Hello", "World"));

prints `Hello World`.  Use `StringJoiner` or `Collectors.joining()` for more advanced cases.