package dhfsbe.store.infrastructure;

import dhfsbe.member.entity.Member;
import dhfsbe.member.repository.MemberRepository;
import dhfsbe.store.domain.entity.FolderObject;
import dhfsbe.store.repository.FolderStoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    }
}
