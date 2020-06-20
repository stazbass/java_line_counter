package org.example;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Line counting application
 * http://codekata.com/kata/kata13-counting-code-lines/
 * <p>
 * Style guide:
 * https://google.github.io/styleguide/javaguide.html
 */
public class App {
    private static final String JAVA_FILE_EXTENSION = ".java";
    private static final String CHILD_RECORD_PREFIX = "+";

    public static FSEntry generateStats(File file) {
        if (file.isDirectory()) {
            DirEntryStat result = new DirEntryStat(file.toPath());
            File[] directoryContent = file.listFiles();
            if (directoryContent != null) {
                List<FSEntry> childEntries = Stream.of(directoryContent)
                        .map(p -> generateStats(p))
                        .collect(Collectors.toList());
                result.addChildren(childEntries);
            }
            return result;
        } else {
            if (file.getName().endsWith(JAVA_FILE_EXTENSION)) {
                return new JavaFileEntryStat(file);
            } else {
                return new FileEntryStat(file);
            }
        }
    }

    public static String printStats(FSEntry entry, String prefix) {
        StringBuilder result = new StringBuilder();
        result.append(prefix);
        result.append(entry.toString());
        result.append('\n');
        if (entry instanceof DirEntryStat) {
            for (FSEntry e : ((DirEntryStat) entry).getChildren()) {
                result.append(printStats(e, prefix + CHILD_RECORD_PREFIX));
            }
        }
        return result.toString();
    }

    public static void main(String[] args) {
        FSEntry e = generateStats(new File(args[0]));
        System.out.println(printStats(e, ""));
    }
}
