package tokenlogger.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CSV {
    private final Set<String> content = new HashSet<>();
    private String path;
    private BufferedWriter writer;

    public CSV(String path) {
        try {
            this.path = path;
            InputStream in = Files.newInputStream(Paths.get(this.path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null)
                this.content.addAll(Arrays.asList(line.split(",")));
            reader.close();
        } catch (Exception ignored) {}
    }

    public boolean contains(String value) {
        return this.content.contains(value);
    }

    public void add(String value) {
        try {
            this.content.add(value);
            this.writer.write(this.content.size() > 1 ? "," + value : value);
        } catch (Exception ignored) {}
    }

    public void open() {
        try {
            OutputStream out = new FileOutputStream(this.path, true);
            this.writer = new BufferedWriter(new OutputStreamWriter(out));
        } catch (Exception ignored) {}
    }

    public void close() {
        try {
            this.writer.close();
        } catch (Exception ignored) {}
    }
}
