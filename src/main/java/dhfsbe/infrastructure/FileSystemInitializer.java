package dhfsbe.infrastructure;

import dhfsbe.infrastructure.exception.FileCreateException;
import dhfsbe.member.entity.Member;
import dhfsbe.member.repository.MemberRepository;
import dhfsbe.store.domain.entity.FolderObject;
import dhfsbe.store.repository.FolderStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FileSystemInitializer {
    private final FileStoreProps fileStoreProps;

    private final FolderStoreRepository folderStoreRepository;

    private final MemberRepository memberRepository;


    @Transactional
    @EventListener(ApplicationReadyEvent.class)
    public void createRootDir() {

        if (memberRepository.findByLoginId("root").isPresent()) return;

        Member root = Member.builder()
                .loginId("root")
                .email("root@donghee.co.kr")
                .password("1234")
                .build();

        memberRepository.save(root);

        FolderObject folder = FolderObject.builder()
                .createdAt(LocalDateTime.now())
                .name(fileStoreProps.getRoot().getFileName().toString())
                .ownerId(root.getId())
                .parentFolder(null)
                .build();

        folderStoreRepository.save(folder);

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                Path rootPath = fileStoreProps.getRoot();
                try {
                    if (!Files.exists(rootPath)) {
                        Files.createDirectories(rootPath);
                    }
                } catch (IOException exception) {
                    throw new FileCreateException(exception);
                }
            }
        });
    }
}
