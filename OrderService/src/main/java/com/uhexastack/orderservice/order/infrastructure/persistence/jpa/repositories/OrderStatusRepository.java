package com.uhexastack.orderservice.order.infrastructure.persistence.jpa.repositories;


import com.uhexastack.orderservice.order.domain.model.entities.OrderStatus;
import com.uhexastack.orderservice.order.domain.model.valueobjects.OrderStatusType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {

    Optional<OrderStatus> findByName(OrderStatusType name);

    Boolean existsByName(OrderStatusType name);
}
