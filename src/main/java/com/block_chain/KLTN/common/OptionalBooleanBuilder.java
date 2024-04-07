package com.block_chain.KLTN.common;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanTemplate;
import com.querydsl.core.types.dsl.Expressions;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@AllArgsConstructor
public class OptionalBooleanBuilder {
    private BooleanExpression predicate;

    public <T> OptionalBooleanBuilder notNullAnd(Function<T, BooleanExpression> function, T value) {
        if (value != null) {
            return new OptionalBooleanBuilder(predicate.and(function.apply(value)));
        }
        return this;
    }

    public <T> OptionalBooleanBuilder notNullOr(Function<T, BooleanExpression> function, T value) {
        if (value != null) {
            return new OptionalBooleanBuilder(predicate.or(function.apply(value)));
        }
        return this;
    }

    public <T> OptionalBooleanBuilder notNullAnd(BiFunction<T, T, BooleanExpression> function,
                                                 T value1, T value2) {
        if (value1 != null || value2 != null) {
            return new OptionalBooleanBuilder(predicate.and(function.apply(value1, value2)));
        }
        return this;
    }

    public <T> OptionalBooleanBuilder notNullAnd(BiFunction<T, T, BooleanExpression> function,
                                                 BiFunction<T, T, BooleanExpression> function2,
                                                 T value1, T value2) {
        if (value1 != null || value2 != null) {
            return new OptionalBooleanBuilder(predicate.andAnyOf(function.apply(value1, value2), function2.apply(value1, value2)));
        }
        return this;
    }

    public <T> OptionalBooleanBuilder notEmptyAnd(Function<T, BooleanExpression> function, T value) {
        if (ObjectUtils.isNotEmpty(value)) {
            return new OptionalBooleanBuilder(predicate.and(function.apply(value)));
        }
        return this;
    }

    public <T> OptionalBooleanBuilder notEmptyOr(Function<T, BooleanExpression> function, T value) {
        if (ObjectUtils.isNotEmpty(value)) {
            return new OptionalBooleanBuilder(predicate.or(function.apply(value)));
        }
        return this;
    }

    public OptionalBooleanBuilder notBlankAnd(Function<String, BooleanExpression> function, String value) {
        if (StringUtils.isNotBlank(value)) {
            return new OptionalBooleanBuilder(predicate.and(function.apply(value)));
        }
        return this;
    }

    @SafeVarargs
    public final <T> OptionalBooleanBuilder notEmptyAndMultipleOr(T value, Function<T, BooleanExpression>... functions) {
        if (ObjectUtils.isNotEmpty(value)) {
            List<Predicate> predicates = Arrays
                    .stream(functions)
                    .map(f -> f.apply(value))
                    .collect(Collectors.toList());

            return new OptionalBooleanBuilder(predicate.andAnyOf(predicates.toArray(new Predicate[predicates.size()])));
        }

        return this;
    }

    public BooleanExpression build() {
        return predicate;
    }

    public OptionalBooleanBuilder notNullAndExpressionTrue(String expression, String value, String conf) {
        if (ObjectUtils.isNotEmpty(value) && ObjectUtils.isNotEmpty(expression) && ObjectUtils.isNotEmpty(conf)) {
            BooleanTemplate booleanTemplate = Expressions.booleanTemplate(expression, value, conf);
            return new OptionalBooleanBuilder(predicate.and(booleanTemplate));
        }

        return this;
    }

    public OptionalBooleanBuilder notNullOrMultiExpressionTrue(String value, String conf, String... expression) {
        if (ObjectUtils.isNotEmpty(value) && ObjectUtils.isNotEmpty(expression) && ObjectUtils.isNotEmpty(conf)) {
            List<BooleanTemplate> templateList = Arrays.stream(expression).map(t -> Expressions.booleanTemplate(t, value, conf)).toList();
            return new OptionalBooleanBuilder(predicate.andAnyOf(templateList.toArray(new Predicate[0])));
        }
        return this;
    }
}