package com.block_chain.KLTN.domain.transaction;

import com.block_chain.KLTN.common.AbstractEntity;
import com.block_chain.KLTN.domain.order.OrderEntity;
import com.block_chain.KLTN.domain.post_offices.PostOfficesEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "transaction")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private String note;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "post_office_id")
    private Long PostOfficeId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "post_office_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PostOfficesEntity postOffices;
}
