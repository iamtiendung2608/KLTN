package com.block_chain.KLTN.domain.postOffices;

import com.block_chain.KLTN.domain.location_tag.LocationTagEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "post_offices")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostOfficesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String phone;
    private String address;
    private String longitude;
    private String latitude;
    private String sponsor;
    private String code;

    @Column(name = "sponsor_phone")
    private String sponsorPhone;

    @Column(name = "location_tag_id")
    private Long locationTagId;

    @ManyToOne
    @JoinColumn(name = "location_tag_id", referencedColumnName = "id", insertable = false, updatable = false)
    private LocationTagEntity locationTag;

    private boolean deleted;

    @Column(name="wallet_address", unique = true)
    private String walletAddress;
}
