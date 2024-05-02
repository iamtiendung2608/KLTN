package com.block_chain.KLTN.domain.location_tag;

import com.block_chain.KLTN.common.OptionalBooleanBuilder;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class DefaultLocationTagQueryService implements LocationTagQueryService{
    private final LocationTagRepository locationTagRepository;
    private final LocationTagMapper locationTagMapper;

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<LocationTagResponse> getLocationTag(LocationTagSearchRequest request) {
        QLocationTagEntity qLocationTag = QLocationTagEntity.locationTagEntity;
        JPAQuery<LocationTagEntity> locationTagQuery = new JPAQuery<>(entityManager);

        BooleanExpression predicate = qLocationTag.id.gt(1L);

        switch (request.type()) {
            case PROVINCE -> {
                predicate = new OptionalBooleanBuilder(predicate)
                        .notEmptyAnd(qLocationTag.district::containsIgnoreCase, request.keyword()).build();
                return locationTagQuery.select(qLocationTag).from(qLocationTag).where(predicate).distinct().fetch().stream()
                        .filter(distinctByKey(LocationTagEntity::getProvince)).limit(10).map(locationTagMapper::toResponse).toList();
            }
            case DISTRICT -> {
                LocationTagEntity locationTag = locationTagRepository.findById(request.locationId())
                        .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "LocationTag"));
                predicate = new OptionalBooleanBuilder(predicate.and(qLocationTag.province.eq(locationTag.getProvince())))
                        .notEmptyAnd(qLocationTag.ward::containsIgnoreCase, request.keyword()).build();
                return locationTagQuery.from(qLocationTag).where(predicate).distinct().fetch().stream()
                        .filter(distinctByKey(LocationTagEntity::getDistrict))
                        .map(locationTagMapper::toResponse).toList();
            }
            case WARD -> {
                LocationTagEntity locationTag = locationTagRepository.findById(request.locationId())
                        .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "LocationTag"));
                predicate = new OptionalBooleanBuilder(predicate.and(qLocationTag.district.eq(locationTag.getDistrict()).and(qLocationTag.province.eq(locationTag.getProvince()))))
                        .notEmptyAnd(qLocationTag.province::containsIgnoreCase, request.keyword()).build();
                return locationTagQuery.from(qLocationTag).where(predicate).distinct().fetch().stream().map(locationTagMapper::toResponse).toList();
            }
            default -> throw new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "LocationTag");
        }
    }

    @Override
    public LocationTagResponse getLocationTagById(Long id) {
        return locationTagMapper.toResponse(locationTagRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "LocationTag")));
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }
}
