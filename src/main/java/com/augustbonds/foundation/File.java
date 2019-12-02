package com.augustbonds.foundation;

import java.io.Closeable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class File implements Closeable {

    private final Path path;
    private Stream<String> lines;

    public File(Path path) {
        this.path = path;
    }

    public Iterable<String> lines() throws IOException {
        lines = Files.lines(path);
        return () -> lines.iterator();
    }

    @Override
    public void close(){
        if (lines != null) {
            lines.close();
        }
    }
}
