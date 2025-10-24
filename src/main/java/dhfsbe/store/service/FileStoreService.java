package dhfsbe.store.service;

import dhfsbe.store.domain.dto.UploadFileDto;
import dhfsbe.store.repository.FileStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileStoreService {

    private final FileStoreRepository fileStoreRepository;


    @Transactional
    public void uploadFile(UploadFileDto uploadFileDto) {

    }

}
