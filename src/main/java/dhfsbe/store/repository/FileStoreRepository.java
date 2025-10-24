package dhfsbe.store.repository;

import dhfsbe.store.domain.entity.FileObject;
import org.apache.catalina.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileStoreRepository extends JpaRepository<FileObject, Long> {
}
