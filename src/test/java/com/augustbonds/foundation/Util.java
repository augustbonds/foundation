package com.augustbonds.foundation;

import static org.junit.jupiter.api.Assertions.fail;

public class Util {
    static void expect(Runnable throwsException, Exception exceptionClass){
        try {
            throwsException.run();
        } catch (Exception e) {
            if (exceptionClass.getClass() != e.getClass()){
               fail("Wrong exception thrown");
            }
            return;
        }
        fail("No exception thrown");
    }
}
