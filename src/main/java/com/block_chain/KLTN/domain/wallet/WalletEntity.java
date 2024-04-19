package com.block_chain.KLTN.domain.wallet;

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
}
