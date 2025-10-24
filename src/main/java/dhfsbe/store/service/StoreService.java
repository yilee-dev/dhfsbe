package dhfsbe.store.service;

import dhfsbe.store.domain.dto.CreateFolderDto;
import dhfsbe.store.domain.dto.UploadFileDto;
import org.springframework.transaction.annotation.Transactional;

public interface StoreService {
    Long createFolder(CreateFolderDto createFolderDto);

    void uploadFile(UploadFileDto uploadFileDto);
}
