package dhfsbe.store.utils;

import dhfsbe.store.repository.FileStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

public class FileInfoExtractor {
    public static String createStoreFilename(String filename) {
        String ext = extractExt(filename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + ext;
    }

    public static String extractExt(String filename) {
        int i = filename.lastIndexOf(".");
        return filename.substring(i + 1);
    }


}
