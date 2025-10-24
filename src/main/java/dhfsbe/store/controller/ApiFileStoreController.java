package dhfsbe.store.controller;

import dhfsbe.store.domain.dto.CreateFolderDto;
import dhfsbe.store.service.FileStoreService;
import dhfsbe.store.service.FolderStoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiFileStoreController {

    private final FolderStoreService folderStoreService;

    @PostMapping("/folders")
    public ResponseEntity<Map<String, Long>> createFolder(CreateFolderDto createFolderDto) {
        Long folderId = folderStoreService.createFolder(createFolderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", folderId));
    }
}
