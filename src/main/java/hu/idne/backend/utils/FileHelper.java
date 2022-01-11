package hu.idne.backend.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelper {

    private FileHelper(){}

    public static String readFile(String path, String charset) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, charset);
    }
}
