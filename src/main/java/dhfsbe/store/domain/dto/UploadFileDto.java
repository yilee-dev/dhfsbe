package dhfsbe.store.domain.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UploadFileDto {
    private Long ownerId;
    private Long folderId;
    private MultipartFile uploadFile;
    private String description;
}
