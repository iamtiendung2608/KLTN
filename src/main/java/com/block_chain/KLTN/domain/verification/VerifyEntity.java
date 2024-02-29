package com.block_chain.KLTN.domain.verification;

import com.block_chain.KLTN.common.AbstractEntity;
import com.block_chain.KLTN.domain.user.UserEntity;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "verification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class VerifyEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", updatable = false, insertable = false)
    private UserEntity user;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "is_verified")
    private boolean isVerified;

    private String code;

    @Column(name = "verified_at")
    private OffsetDateTime verifiedAt;

    @Column(name = "last_send_at")
    private OffsetDateTime lastSendAt;
}
