package dhfsbe.member.repository;

import dhfsbe.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    public Optional<Member> findByLoginId(String loginId);
}
