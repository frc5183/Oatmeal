package wtf.triplapeeck.oatmeal;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileRW {
    private InputStream inputstream;
    private OutputStream outputstream;
    private Path path;
    public FileRW(Path path1) {
        path=path1;
    }
    public String readAll() throws IOException {
        inputstream = Files.newInputStream(path);
        byte[] bytes = inputstream.readAllBytes();
        inputstream.close();
        inputstream=null;
        return new String(bytes, StandardCharsets.UTF_8);
    }
    public void writeAll(String string) throws IOException {
        outputstream=Files.newOutputStream(path);
        outputstream.write(string.getBytes(StandardCharsets.UTF_8));
        outputstream.flush();
        outputstream.close();
        outputstream=null;
    }
}
