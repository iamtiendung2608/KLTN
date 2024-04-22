package com.block_chain.KLTN.domain.transfer_object;

import com.block_chain.KLTN.domain.post_offices.PostOfficesEntity;
import com.block_chain.KLTN.domain.user.UserEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "transfer_object")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferObjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "at_office_flg")
    private Boolean atOfficeFlg;

    @Column(name = "recieve_shift")
    @Enumerated(EnumType.STRING)
    private ReceiveShift receiveShift;

    @Column(name = "user_id")
    private Integer userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;

    @Column(name = "post_office_id")
    private Integer postOfficeId;

    @ManyToOne
    @JoinColumn(name = "post_office_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PostOfficesEntity postOffices;
}
