package org.example;

import java.io.*;

/**
 * Java source code specific line collection
 */
public class JavaFileEntryStat implements FSEntry {
    public static final String JAVA_BLOCK_COMMENT_REGEXP = "(?s)/\\*(.)*?\\*/";
    public static final String JAVA_LINE_COMMENT_REGEXP = "//.*";
    private final File file;
    long lineCount = -1;

    public JavaFileEntryStat(File file) {
        this.file = file;
    }

    public static long getLineCount(String into) throws IOException {
        String result = into;
        result = result.replaceAll(JAVA_BLOCK_COMMENT_REGEXP, "");
        result = result.replaceAll(JAVA_LINE_COMMENT_REGEXP, "");
        BufferedReader br = new BufferedReader(new StringReader(result));
        long lineCount = 0;
        String line;
        while ((line = br.readLine()) != null) {
            line = line.trim(); // empty line ignore
            if (line.length() > 0) {
                lineCount++;
            }
        }
        return lineCount;
    }

    private String readFile() throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(file));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append('\n');
        }
        br.close();
        return stringBuilder.toString();
    }

    @Override
    public long getLineCount() {
        try {
            if (lineCount == -1) lineCount = getLineCount(readFile());
        } catch (IOException e) {
            e.printStackTrace();
            lineCount = -1;
        }
        return lineCount;
    }

    @Override
    public String toString() {
        String result = String.format("F[%s]:%d", file, getLineCount());
        return result;
    }

    @Override
    public String getPrefix() {
        return "-";
    }
}
