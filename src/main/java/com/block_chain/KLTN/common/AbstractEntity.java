package com.block_chain.KLTN.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.Builder.Default;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class AbstractEntity {
    // @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @Default
    private OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

    // @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    @Default
    private OffsetDateTime updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
}
