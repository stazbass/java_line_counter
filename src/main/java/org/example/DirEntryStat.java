package org.example;

import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Directory implementation of line collection
 */
public class DirEntryStat implements FSEntry {
    private final Path path;
    private final List<FSEntry> children;

    public DirEntryStat(Path path) {
        this.path = path;
        children = new LinkedList<>();
    }

    public void addChildren(Collection<FSEntry> entries) {
        children.addAll(entries);
    }

    public List<FSEntry> getChildren() {
        return children;
    }

    @Override
    public String toString() {
        String result = String.format("D[%s]:%d", path.toFile().getAbsolutePath(), getLineCount());
        return result;
    }

    @Override
    public long getLineCount() {
        return children.stream().map(child -> child.getLineCount()).collect(Collectors.summingLong(e -> e));
    }
}
