package com.uhexastack.orderservice.order.infrastructure.persistence.jpa.repositories;


import com.uhexastack.orderservice.order.domain.model.aggregates.OrderAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderAggregate, Long> {

    List<OrderAggregate> findByUserId(Long userId);
}
