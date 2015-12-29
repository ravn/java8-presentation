nio2 - Getting file systems right or at least better
===

The original `File` class was hard bound to a file system
implementation and did not clearly separate between the name of 
a file, and concept of a physical file in the underlying file system.

