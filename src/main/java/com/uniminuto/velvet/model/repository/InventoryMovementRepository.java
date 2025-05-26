package com.uniminuto.velvet.model.repository;

import com.uniminuto.velvet.model.entity.InventoryMovement;
import com.uniminuto.velvet.model.entity.MovementType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, Long> {

    List<InventoryMovement> findByMovementType(MovementType movementType);

    List<InventoryMovement> findByProductId(Long productId);

    List<InventoryMovement> findByInventoryStockId(Long inventoryStockId);

    List<InventoryMovement> findByOrderId(Long orderId);

    List<InventoryMovement> findByCreatedById(Long userId);

    List<InventoryMovement> findByMovementDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<InventoryMovement> findByProductIdAndMovementType(Long productId, MovementType movementType);

    List<InventoryMovement> findByInventoryStockIdAndMovementType(Long inventoryStockId, MovementType movementType);
}