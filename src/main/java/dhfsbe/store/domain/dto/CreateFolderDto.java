package dhfsbe.store.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateFolderDto {
    private Long ownerId;
    private Long parentFolderId;
    private String folderName;
}
