package dhfsbe.store.service;

import dhfsbe.infrastructure.FileSystemStorage;
import dhfsbe.store.domain.dto.UploadFileDto;
import dhfsbe.store.domain.entity.FileObject;
import dhfsbe.store.domain.entity.FolderObject;
import dhfsbe.store.repository.FileStoreRepository;
import dhfsbe.store.repository.FolderStoreRepository;
import dhfsbe.store.utils.FileInfoExtractor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileStoreService {

    private final FileStoreRepository fileStoreRepository;

    private final FolderStoreRepository folderStoreRepository;

    private final FileSystemStorage fileSystemStorage;

    @Transactional
    public Long uploadFile(UploadFileDto uploadFileDto) throws IOException {
        MultipartFile uploadFile = uploadFileDto.getUploadFile();
        if (uploadFile.isEmpty()) throw new IllegalArgumentException("upload file not exists");

        String originalFilename = uploadFile.getOriginalFilename();
        String storeFilename = FileInfoExtractor.createStoreFilename(originalFilename);

        FolderObject folderObject = folderStoreRepository.findById(uploadFileDto.getFolderId())
                .orElseThrow(() -> new NoSuchElementException("Folder not found: " + uploadFileDto.getFolderId()));

        Path dirPath = fileSystemStorage.getFullPath(fileSystemStorage.getBreadcrumbs(folderObject));
        Path fullPath = dirPath.resolve(storeFilename);

        uploadFile.transferTo(new File(fullPath.toString()));

        FileObject newFile = FileObject.builder()
                .contentType(uploadFile.getContentType())
                .ownerId(uploadFileDto.getOwnerId())
                .path(dirPath.toString())
                .ext(FileInfoExtractor.extractExt(originalFilename))
                .size(uploadFile.getSize())
                .createdAt(LocalDateTime.now())
                .uploadFilename(originalFilename)
                .storeFilename(storeFilename)
                .folder(folderObject)
                .build();

        FileObject saved = fileStoreRepository.save(newFile);
        return saved.getId();
    }

    public Resource download(Long fileId) {
        FileObject fileObject = fileStoreRepository.findById(fileId)
                .orElseThrow(() -> new NoSuchElementException("Not exists file: " + fileId));

        String path = fileObject.getPath();
        String storeFilename = fileObject.getStoreFilename();
        Path dirPath = Paths.get(path);
        Path downloadTarget = dirPath.resolve(storeFilename);

        try {
            return new UrlResource("file:" + downloadTarget.toString());
        } catch (MalformedURLException exception) {
            throw new RuntimeException(exception);
        }
    }

    public String getUploadFilename(Long fileId) {
        return fileStoreRepository.findById(fileId)
                .orElseThrow(() -> new NoSuchElementException("Not found file: " + fileId))
                .getUploadFilename();
    }

}
