package dhfsbe.store.domain.dto;

import dhfsbe.store.domain.entity.FileObject;
import dhfsbe.store.domain.entity.FolderObject;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderListResponse {

    private FolderBriefDto currentFolder;

    private List<String> breadcrumbs;

    private Slice<FolderBriefDto> childFolders;

    private Slice<FileItemDto> fileList;
}
