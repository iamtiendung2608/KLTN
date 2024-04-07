package com.block_chain.KLTN.domain.organization;

import lombok.*;

import javax.persistence.*;

@Table(name = "organization")
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String description;

    @Enumerated(EnumType.STRING)
    private OrganizationScale scale;

    @Enumerated(EnumType.STRING)
    private OrganizationCategory category;

    @Enumerated(EnumType.STRING)
    private OrganizationScope scope;
}
