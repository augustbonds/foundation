package com.augustbonds.foundation;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTest {
    @Test
    public void testFile() throws IOException {
        try (File f = new File(Paths.get("foundation/src/test/resources/test.txt"))){
           int lineCount = 0;
           for (String line : f.lines()){
               assertEquals("hej", line, "Incorrect line read from file");
               lineCount++;
           }
           assertEquals(3, lineCount);
        }
    }
}
