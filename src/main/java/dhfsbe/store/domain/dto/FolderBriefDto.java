package dhfsbe.store.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @Builder
public class FolderBriefDto {

    public FolderBriefDto(Long id, String name, Long childCount, Long fileCount, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.childCount = childCount;
        this.fileCount = fileCount;
        this.createdAt = createdAt;
    }

    private Long id;
    private String name;
    private Long childCount;
    private Long fileCount;
    private LocalDateTime createdAt;
}
