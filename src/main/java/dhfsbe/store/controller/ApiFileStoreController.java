package dhfsbe.store.controller;

import dhfsbe.store.domain.dto.CreateFolderDto;
import dhfsbe.store.domain.dto.FolderListResponse;
import dhfsbe.store.domain.dto.UploadFileDto;
import dhfsbe.store.service.FileStoreService;
import dhfsbe.store.service.FolderStoreService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ApiFileStoreController {

    private final FolderStoreService folderStoreService;
    private final FileStoreService fileStoreService;

    @PostMapping("/folders")
    public ResponseEntity<Map<String, Long>> createFolder(CreateFolderDto createFolderDto) {
        Long folderId = folderStoreService.createFolder(createFolderDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", folderId));
    }

    @PostMapping("/files")
    public ResponseEntity<Map<String, Long>> createFile(UploadFileDto uploadFileDto) {
        try {
            Long id = fileStoreService.uploadFile(uploadFileDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id", id));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/files/{id}")
    public ResponseEntity<Resource> download(@PathVariable("id") Long fileId) {
        UrlResource resource = (UrlResource) fileStoreService.download(fileId);
        String uploadFilename = fileStoreService.getUploadFilename(fileId);

        String encodeUploadFilename = UriUtils.encode(uploadFilename, StandardCharsets.UTF_8);

        String contentDisposition = "attachment; filename=\"" + encodeUploadFilename + "\"";
        return ResponseEntity.ok().header("Content-Disposition", contentDisposition)
                .body(resource);
    }

    @GetMapping("/folders/{id}")
    public ResponseEntity<FolderListResponse> folderList(@PathVariable("id") Long id,
                                                         @RequestParam(defaultValue = "0") int folderPage,
                                                         @RequestParam(defaultValue = "20") int folderSize,
                                                         @RequestParam(defaultValue = "0") int filePage,
                                                         @RequestParam(defaultValue = "100") int fileSize) {

        PageRequest folderPageRequest = PageRequest.of(folderPage, folderSize);
        PageRequest filePageRequest = PageRequest.of(filePage, fileSize);

        FolderListResponse folderListResponse = folderStoreService.folderList(id, folderPageRequest, filePageRequest);
        return ResponseEntity.ok(folderListResponse);
    }

}
