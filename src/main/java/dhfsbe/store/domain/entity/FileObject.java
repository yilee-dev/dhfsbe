package dhfsbe.store.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "files")
@Getter @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
public class FileObject {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "folder_id")
    private FolderObject folder;

    @Column(nullable = false, name = "upload_filename")
    private String uploadFilename;

    @Column(nullable = false, name = "store_filename")
    private String storeFilename;

    @Column(name = "path")
    private String path;

    @Column(name = "content_type")
    private String contentType;

    @Column(name = "size")
    private int size;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "owner_id")
    private Long ownerId;
}
