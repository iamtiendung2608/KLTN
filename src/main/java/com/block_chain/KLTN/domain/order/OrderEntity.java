package com.block_chain.KLTN.domain.order;

import com.block_chain.KLTN.common.AbstractEntity;
import com.block_chain.KLTN.domain.organization.OrganizationEntity;
import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity
@Table(name = "order")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "total_weight")
    private Integer totalWeight;

    @Column(name = "total_price")
    private Integer totalPrice;

    private OffsetDateTime deliveryAt;
    private OffsetDateTime estimatedDeliveryAt;

    @Column(name = "delivery_type")
    @Enumerated(EnumType.STRING)
    private DeliveryType deliveryType;

    private String note;

    @Column(name = "paid_type")
    @Enumerated(EnumType.STRING)
    private PaidType paidType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrganizationEntity organization;

    @Column(name = "organization_id")
    private Long organizationId;

}
