package com.block_chain.KLTN.domain.employee;

import com.block_chain.KLTN.common.AbstractEntity;
import com.block_chain.KLTN.domain.location_tag.LocationTagEntity;
import com.block_chain.KLTN.domain.post_offices.PostOfficesEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "employee")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeEntity extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String email;

    @Column(name = "location_tag_id")
    private Long locationTagId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_tag_id", referencedColumnName = "id", insertable = false, updatable = false)
    private LocationTagEntity locationTag;

    @Column(name = "post_office_tag")
    private Long postOfficeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_office_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PostOfficesEntity postOffice;
    private Boolean active;
}
