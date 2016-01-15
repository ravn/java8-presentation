Process
===

Three new methods in the Process class -

* `destroyForcibly()` - terminates a process with a much higher degree of success than before.
* `isAlive()` tells if a process launched by your code is still alive.
* A new overload for `waitFor()` lets you specify the amount of time you want to wait for the process to finish. This returns whether the process exited successfully or timed-out in which case you might terminate it.
