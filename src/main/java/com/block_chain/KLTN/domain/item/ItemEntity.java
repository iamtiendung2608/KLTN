package com.block_chain.KLTN.domain.item;

import java.util.List;

import javax.persistence.*;

import com.block_chain.KLTN.domain.order.orderItem.OrderItemEntity;

import lombok.*;

@Entity
@Data @Builder 
@NoArgsConstructor
@AllArgsConstructor
@Table(name="item")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer price;
    private Float weight;

    @Enumerated(EnumType.STRING)
    @Column(name="item_category")
    private ItemCategory itemCategory; //nullable

    @OneToMany(mappedBy = "item")
    private List<OrderItemEntity> orderItems;
}

