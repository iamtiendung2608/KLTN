package com.block_chain.KLTN.common;

import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.EntityListeners;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
@AllArgsConstructor @NoArgsConstructor
public abstract class AbstractEntity {
    // @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt = OffsetDateTime.now();

    // @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "modified_at", nullable = false)
    private OffsetDateTime updatedAt = OffsetDateTime.now();
}
