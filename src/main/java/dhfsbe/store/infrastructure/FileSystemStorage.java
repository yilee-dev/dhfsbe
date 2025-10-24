package dhfsbe.store.infrastructure;

import dhfsbe.store.domain.entity.FolderObject;
import dhfsbe.store.infrastructure.exception.FileCreateException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class FileSystemStorage {
    private final FileStoreProps fileStoreProps;

    public Path getRootPath() {
        return fileStoreProps.getRoot();
    }

    public void createFolder(FolderObject folderObject) {
        if (folderObject == null) return;

        Path root = fileStoreProps.getRoot();
        if (root.getFileName().endsWith(folderObject.getName())) {
            return;
        }

        List<String> pathNames = new ArrayList<>();
        for (FolderObject current = folderObject; current != null && !fileStoreProps.getRoot().toString().endsWith(current.getName()); current = current.getParentFolder()) {
            String name = current.getName();
            validateFolderName(name);
            pathNames.add(name);
        }

        Collections.reverse(pathNames);

        Path path = root;
        for (String pathName : pathNames) {
            path = path.resolve(pathName);
        }

        path = path.normalize();

        if (!path.startsWith(root)) {
            throw new SecurityException("Invalid folder path");
        }

        try {
            Files.createDirectories(path);
        } catch (IOException exception) {
            throw new FileCreateException(exception);
        }
    }

    private void validateFolderName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Folder name is empty");
        }

        if (name.contains("..") || name.matches(".*[\\\\/:*?\"<>|].*")) {
            throw new IllegalArgumentException("Invalid characters in folder name: " + name);
        }
    }

}
