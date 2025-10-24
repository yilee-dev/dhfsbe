package dhfsbe.store.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @Builder
public class FileItemDto {
    public FileItemDto(Long id, String uploadFilename, String contentType, Long size, LocalDateTime createdAt) {
        this.id = id;
        this.uploadFilename = uploadFilename;
        this.contentType = contentType;
        this.size = size;
        this.createdAt = createdAt;
    }

    private Long id;
    private String uploadFilename;
    private String contentType;
    private Long size;
    private LocalDateTime createdAt;
}
