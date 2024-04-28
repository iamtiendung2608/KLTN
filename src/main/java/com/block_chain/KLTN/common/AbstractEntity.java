package com.block_chain.KLTN.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@AllArgsConstructor @NoArgsConstructor
public abstract class AbstractEntity {
    // @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now(ZoneOffset.UTC);

    // @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now(ZoneOffset.UTC);
}
