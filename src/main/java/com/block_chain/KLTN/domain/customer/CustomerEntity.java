package com.block_chain.KLTN.domain.customer;

import com.block_chain.KLTN.common.AbstractEntity;
import com.block_chain.KLTN.domain.location_tag.LocationTagEntity;
import com.block_chain.KLTN.domain.organization.OrganizationEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEntity extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    private String address;

    @Column(name = "organization_id")
    private Long organizationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", referencedColumnName = "id", insertable = false, updatable = false)
    private OrganizationEntity organization;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @Column(name = "location_tag_id")
    private Long locationTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_tag_id", referencedColumnName = "id", insertable = false, updatable = false)
    private LocationTagEntity locationTag;
}
