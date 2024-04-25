package com.block_chain.KLTN.domain.item;

import javax.persistence.*;

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
    private int price;
    private float weight;
    private float length;
    private float height;
    private float width;

    @Enumerated(EnumType.STRING)
    @Column(name="item_category")
    private ItemCategory itemCategory; //nullable
}

