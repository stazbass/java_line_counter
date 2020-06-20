package org.example;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

public class JavaFileEntryStatTest {
    String readResource(String name) throws IOException {
        StringBuilder resultBuilder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(name)));
        String line;
        while( (line = reader.readLine()) != null){
            resultBuilder.append(line);
            resultBuilder.append('\n');
        }
        return resultBuilder.toString();
    }


    @Test
    public void testLineCounter() throws IOException {
        String testFile = readResource("/test.java");
        long lineCount = JavaFileEntryStat.getLineCount(testFile);
        assertEquals(lineCount, 5);
    }
}