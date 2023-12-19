package wtf.triplapeeck.oatmeal;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * A simple file reader/writer
 */
public class FileRW {
    private InputStream inputstream;
    private OutputStream outputstream;
    private Path path;

    /**
     * Create a Path object from a string for this constructor
     * @param path1 The path to the file
     */
    public FileRW(Path path1) {
        path=path1;
    }

    /**
     * Reads the entire file and returns it as a string
     * @return The contents of the file as a string
     * @throws IOException
     */
    public String readAll() throws IOException {
        inputstream = Files.newInputStream(path);
        byte[] bytes = inputstream.readAllBytes();
        inputstream.close();
        inputstream=null;
        return new String(bytes, StandardCharsets.UTF_8);
    }

    /**
     * (Over)Writes the entire file with the given string
     * @param string The string to write to the file
     * @throws IOException
     */
    public void writeAll(String string) throws IOException {
        Files.deleteIfExists(path);
        outputstream=Files.newOutputStream(path);
        outputstream.write(string.getBytes(StandardCharsets.UTF_8));
        outputstream.flush();
        outputstream.close();
        outputstream=null;
    }
}
