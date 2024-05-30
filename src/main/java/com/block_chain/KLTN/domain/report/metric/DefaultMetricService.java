package com.block_chain.KLTN.domain.report.metric;

import com.block_chain.KLTN.domain.order.OrderRepository;
import com.block_chain.KLTN.domain.order.QOrderEntity;
import com.block_chain.KLTN.domain.report.metric.admin.MetricAdminResponse;
import com.block_chain.KLTN.domain.report.metric.user.MetricUserResponse;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class DefaultMetricService implements MetricService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public MetricAdminResponse getReportAdminMetric() {
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
        JPAQuery<Float> totalShipQuery = new JPAQuery<>(entityManager);

        MetricAdminResponse response = new MetricAdminResponse();
        response.setTotalUser(userRepository.count());
        response.setTotalOrder(orderRepository.count());
        response.setTotalShip(totalShipQuery.select(qOrderEntity.feePaid.sum()).from(qOrderEntity).fetchOne());

        response.setCurrentShip(totalShipQuery.select(qOrderEntity.feePaid.sum()).from(qOrderEntity)
                .where(qOrderEntity.createdAt.after(OffsetDateTime.now(ZoneOffset.UTC).minusDays(1))).fetchOne());
        response.setCurrentOrder(orderRepository.countByCreatedAtAfter(OffsetDateTime.now(ZoneOffset.UTC).minusDays(1)));

        return response;
    }

    @Override
    public MetricUserResponse getReportUserMetric() {
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
        JPAQuery<Float> subTotalQuery = new JPAQuery<>(entityManager);
        MetricUserResponse response = new MetricUserResponse();
        return response;
    }
}
