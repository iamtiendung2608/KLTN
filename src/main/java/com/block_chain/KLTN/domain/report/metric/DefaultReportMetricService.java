package com.block_chain.KLTN.domain.report.metric;

import com.block_chain.KLTN.domain.customer.QCustomerEntity;
import com.block_chain.KLTN.domain.order.OrderRepository;
import com.block_chain.KLTN.domain.order.QOrderEntity;
import com.block_chain.KLTN.domain.report.metric.admin.MetricAdminResponse;
import com.block_chain.KLTN.domain.report.metric.user.MetricUserResponse;
import com.block_chain.KLTN.domain.user.UserEntity;
import com.block_chain.KLTN.domain.user.UserRepository;
import com.block_chain.KLTN.exception.BusinessException;
import com.block_chain.KLTN.exception.ErrorMessage;
import com.querydsl.jpa.impl.JPAQuery;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultReportMetricService implements ReportMetricService {

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
        UserDetails userDetail = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserEntity userEntity = userRepository.findByEmail(userDetail.getUsername())
            .orElseThrow(() -> new BusinessException(ErrorMessage.RESOURCE_NOT_FOUND, "User")); 
        QOrderEntity qOrderEntity = QOrderEntity.orderEntity;
        QCustomerEntity qCustomerEntity = QCustomerEntity.customerEntity;
        JPAQuery<Float> subTotalQuery = new JPAQuery<>(entityManager);
        JPAQuery<Integer> totalOrderQuery = new JPAQuery<>(entityManager);
        JPAQuery<Integer> totalCustomerQuery = new JPAQuery<>(entityManager);
        MetricUserResponse response = new MetricUserResponse();

        response.setTotalSubtotal(subTotalQuery.select(qOrderEntity.subTotal.sum())
            .where(qOrderEntity.organizationId.eq(userEntity.getOrganizationId()))
            .from(qOrderEntity)
            .fetchOne());

        response.setCurrentSubtotal(subTotalQuery.select(qOrderEntity.subTotal.sum())
            .where(qOrderEntity.organizationId.eq(userEntity.getOrganizationId()).and(qOrderEntity.createdAt.after(OffsetDateTime.now(ZoneOffset.UTC).minusDays(1))))
            .from(qOrderEntity)
            .fetchOne());

        response.setTotalOrder(totalOrderQuery.select(qOrderEntity.id)
            .where(qOrderEntity.organizationId.eq(userEntity.getOrganizationId()))
            .from(qOrderEntity)
            .fetch().size());

        response.setCurrentOrder(totalOrderQuery.select(qOrderEntity.id)
            .where(qOrderEntity.organizationId.eq(userEntity.getOrganizationId()).and(qOrderEntity.createdAt.after(OffsetDateTime.now(ZoneOffset.UTC).minusDays(1))))
            .from(qOrderEntity)
            .fetch().size());

        response.setTotalCustomer(totalCustomerQuery.select(qCustomerEntity.id.count())
            .where(qCustomerEntity.organizationId.eq(userEntity.getOrganizationId()))
            .from(qCustomerEntity).fetchCount());

//        response.setCurrentCustomer(totalCustomerQuery.select(qCustomerEntity.id.count())
//            .where(qCustomerEntity.organizationId.eq(userEntity.getOrganizationId()).and(qCustomerEntity.createdAt.after(OffsetDateTime.now(ZoneOffset.UTC).minusDays(1))))
//            .from(qCustomerEntity).fetchCount());
        return response;
    }
}
