package dhfsbe.infrastructure;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

@ConfigurationProperties(prefix = "file.store")
@Getter @Setter
public class FileStoreProps {
    private Path root;
}
