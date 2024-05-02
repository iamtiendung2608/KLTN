package com.block_chain.KLTN.domain.wallet;

import com.block_chain.KLTN.domain.user.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "wallet")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletEntity {
    @Id
    private String address;
    private String secret;
    @Column(name = "salt_iv")
    private String saltIv;

    private String code;
    @Enumerated(EnumType.STRING)
    private WalletType type;

    @Column(name = "user_id")
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;
}
