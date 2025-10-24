package dhfsbe.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Breadcrumb {
    private Long folderId;
    private String folderName;
}
