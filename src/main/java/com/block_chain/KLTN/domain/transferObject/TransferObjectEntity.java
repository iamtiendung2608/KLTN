package com.block_chain.KLTN.domain.transferObject;

import com.block_chain.KLTN.domain.customer.CustomerEntity;
import com.block_chain.KLTN.domain.postOffices.PostOfficesEntity;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

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

    @Column(name = "customer_id")
    private Long customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
    private CustomerEntity customer;

    @Column(name = "post_office_id")
    private Long postOfficeId;

    @ManyToOne
    @JoinColumn(name = "post_office_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PostOfficesEntity postOffices;

    @Column(name = "action_date")
    private OffsetDateTime actionDate;
}
