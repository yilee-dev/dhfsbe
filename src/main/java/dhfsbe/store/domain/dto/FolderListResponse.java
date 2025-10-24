package dhfsbe.store.domain.dto;

import dhfsbe.store.domain.entity.FileObject;
import dhfsbe.store.domain.entity.FolderObject;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FolderListResponse {
    FolderObject currentFolder;

    @Builder.Default
    List<String> breadcrumbs = new ArrayList<>();

    @Builder.Default
    List<FolderObject> childFolders = new ArrayList<>();

    @Builder.Default
    List<FileObject> fileList = new ArrayList<>();
}
