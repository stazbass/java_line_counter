package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Simple text file as line collection
 */
public class FileEntryStat implements FSEntry {
    private final File file;
    private long lineCount = -1;

    public FileEntryStat(File file) {
        this.file = file;
    }

    public static long getLineCount(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            long lines = 0;
            while (reader.readLine() != null) lines++;
            reader.close();
            return lines;
        } catch (Exception e) {
            e.printStackTrace();
            return 0l;
        }
    }

    @Override
    public long getLineCount() {
        if (lineCount < 0) lineCount = getLineCount(this.file);
        return lineCount;
    }

    @Override
    public String toString() {
        String result = String.format("F[%s]:%d", file, getLineCount());
        return result;
    }
}
