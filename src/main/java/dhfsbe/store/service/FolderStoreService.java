package dhfsbe.store.service;


import dhfsbe.store.domain.dto.CreateFolderDto;
import dhfsbe.store.domain.dto.FolderListResponse;
import dhfsbe.store.domain.entity.FileObject;
import dhfsbe.store.domain.entity.FolderObject;
import dhfsbe.infrastructure.FileSystemStorage;
import dhfsbe.store.repository.FolderStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FolderStoreService {
    private final FolderStoreRepository folderStoreRepository;
    private final FileSystemStorage fileSystemStorage;

    @Transactional
    public Long createFolder(CreateFolderDto createFolderDto) {
        Long parentFolderId = createFolderDto.getParentFolderId();

        FolderObject parentFolder = folderStoreRepository.findById(parentFolderId)
                .orElseThrow(() -> new NoSuchElementException("Folder Not founded: " + parentFolderId));

        boolean exists = parentFolder.getChildFolders().stream().anyMatch(folder -> folder.getName().equals(createFolderDto.getFolderName()));
        if (exists) throw new IllegalArgumentException("Already exists folder name");

        FolderObject folder = FolderObject.builder()
                .createdAt(LocalDateTime.now())
                .name(createFolderDto.getFolderName())
                .ownerId(createFolderDto.getOwnerId())
                .parentFolder(parentFolder)
                .build();

        folderStoreRepository.save(folder);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                fileSystemStorage.createFolder(folder);
            }
        });

        return folder.getId();
    }

    public FolderListResponse folderList(Long folderId) {
        FolderObject folderObject = folderStoreRepository.findById(folderId)
                .orElseThrow(() -> new NoSuchElementException("Folder not found: " + folderId));

        FolderObject currentFolder = folderObject;
        List<String> breadcrumbs = fileSystemStorage.getBreadcrumbs(currentFolder);
        List<FolderObject> childFolders = folderObject.getChildFolders();
        List<FileObject> fileList = folderObject.getFileList();
        return FolderListResponse.builder()
                .currentFolder(currentFolder)
                .breadcrumbs(breadcrumbs)
                .childFolders(childFolders)
                .fileList(fileList)
                .build();
    }
}
