package com.block_chain.KLTN.domain.order;

import com.block_chain.KLTN.common.AbstractEntity;
import com.block_chain.KLTN.domain.order.order_item.OrderItemEntity;
import com.block_chain.KLTN.domain.organization.OrganizationEntity;
import lombok.*;

import javax.persistence.*;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
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
    private float totalWeight;

    @Column(name = "total_price")
    private float totalPrice;

    @Column(name = "delivery_at")
    private OffsetDateTime deliveryAt;

    @Column(name = "estimated_delivery_at")
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

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems;

}
