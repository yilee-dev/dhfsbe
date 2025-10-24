package dhfsbe.store.repository;

import dhfsbe.store.domain.dto.FolderBriefDto;
import dhfsbe.store.domain.entity.FolderObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FolderStoreRepository extends JpaRepository<FolderObject, Long> {

    @Query("""
             select new dhfsbe.store.domain.dto.FolderBriefDto(
                f.id, f.name,
                (select count(c) from FolderObject c where c.parentFolder = f and c.isDeleted = false),
                (select count(fi) from FileObject  fi where fi.folder = f and fi.isDeleted = false),
                f.createdAt
             )
             from FolderObject  f
             where f.id = :id and f.isDeleted = false
            """)
    Optional<FolderBriefDto> findBriefById(@Param("id") Long id);

    @Query("""
        select new dhfsbe.store.domain.dto.FolderBriefDto(
            c.id, c.name,
            (select count(gc) from FolderObject gc where gc.parentFolder = c and c.isDeleted = false),
            (select count(gf) from FileObject gf where gf.folder = c and gf.isDeleted = false),
            c.createdAt
        )
        from FolderObject c
        where c.parentFolder.id = :parentId and c.isDeleted = false
        order by c.name asc
    """)
    Slice<FolderBriefDto> findChildrenBriefByParentId(@Param("parentId") Long parentId, Pageable pageable);


}
