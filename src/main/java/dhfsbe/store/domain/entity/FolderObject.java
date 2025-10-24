package dhfsbe.store.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(
        name = "folders",
        indexes = @Index(name = "idx_folder_parent", columnList = "folder_parent_id"),
        uniqueConstraints = @UniqueConstraint(name = "uk_parent_name", columnNames = {"folder_parent_id", "folder_name"})
)
@Getter  @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
public class FolderObject {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "folder_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_parent_id")
    private FolderObject parentFolder;

    @Builder.Default
    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("name ASC")
    private List<FolderObject> childFolders = new ArrayList<>();

    @Column(name = "folder_name")
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("uploadFilename ASC")
    private List<FileObject> fileList = new ArrayList<>();

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private boolean isDeleted;

    private LocalDateTime deletedAt;

    @Column(name = "owner_id")
    private Long ownerId;

    /* 보조 메서드 */
    public void addChildFolder(FolderObject child) {
        if (child == null || child == this) return;
        child.setParentFolder(this);
        this.childFolders.add(child);
    }

    public void removeChild(FolderObject child) {
        if (child == null) return;
        this.childFolders.remove(child);
        child.setParentFolder(null);
    }

    public void moveTo(FolderObject newParent) {
        if (this.parentFolder != null) this.parentFolder.childFolders.remove(this);
        this.setParentFolder(newParent);
        if (newParent != null) newParent.childFolders.add(this);
    }

    private void setParentFolder(FolderObject parent) {
        this.parentFolder = parent;
    }
}
