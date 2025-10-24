package dhfsbe.member.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @EqualsAndHashCode(of = "id")
@Builder @NoArgsConstructor @AllArgsConstructor
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String email;

    private String password;

    private boolean isDisabled;
}
