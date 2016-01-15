
    System.out.println(Files.walk(Paths.get(System.getProperty("user.home")+"/tmp")).count());
    // 4422

https://dzone.com/articles/10-features-java-8-you-havent

FIXME:  The original `File` class was hard bound to a file system
        implementation and did not clearly separate between the name of
        a file, and concept of a physical file in the underlying file system.

