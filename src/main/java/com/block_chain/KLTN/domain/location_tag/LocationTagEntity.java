package com.block_chain.KLTN.domain.location_tag;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "location_tag")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationTagEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String province;
    private String district;
    private String ward;
}
