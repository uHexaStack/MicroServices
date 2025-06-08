package com.uhexastack.inventoryservice.inventory.infrastructure.persistence.jpa.repositories;

import com.uhexastack.inventoryservice.inventory.domain.model.aggregate.InventoryItemAggregate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryItemAggregate, Long> {

    Optional<InventoryItemAggregate> findByUserIdAndName(Long userId, String name);

    List<InventoryItemAggregate> findByUserIdAndQuantityOnHandLessThanEqual(Long userId, int threshold);
}