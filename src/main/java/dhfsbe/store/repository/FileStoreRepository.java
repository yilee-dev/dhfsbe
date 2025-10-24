package dhfsbe.store.repository;

import dhfsbe.store.domain.dto.FileItemDto;
import dhfsbe.store.domain.entity.FileObject;
import org.apache.catalina.Store;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FileStoreRepository extends JpaRepository<FileObject, Long> {

    @Query("""
        select new dhfsbe.store.domain.dto.FileItemDto(
            f.id, f.uploadFilename, f.contentType, f.size, f.createdAt
        )
        from FileObject f
        where f.folder.id = :folderId and f.isDeleted = false
        order by f.uploadFilename asc
    """)
    Slice<FileItemDto> findFilesByFolderId(@Param("folderId") Long folderId, Pageable pageable);
}
