package com.block_chain.KLTN.domain.product;

import com.block_chain.KLTN.common.AbstractEntity;
import com.block_chain.KLTN.domain.organization.OrganizationEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private Integer weight;
    private Integer length;
    private Integer height;
    private Integer width;

    @Column(name = "organization_id")
    private Integer organizationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrganizationEntity organization;
}
