package dhfsbe.store.repository;

import dhfsbe.store.domain.entity.FolderObject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FolderStoreRepository extends JpaRepository<FolderObject, Long> {
}
