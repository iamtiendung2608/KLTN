package com.block_chain.KLTN.domain.admin.user;

import com.block_chain.KLTN.common.OptionalBooleanBuilder;
import com.block_chain.KLTN.domain.user.*;
import com.querydsl.core.types.Predicate;

public record UserAdminSearchRequest(
    String keyword,
    UserStatus status
) {
    public Predicate toPredicate(){
        QUserEntity qUserEntity = QUserEntity.userEntity;
        return new OptionalBooleanBuilder(qUserEntity.id.gt(0L))
            .notNullAnd(qUserEntity.status::eq, status)
            .notNullAnd(qUserEntity.email::containsIgnoreCase, keyword).build();
    }
}
