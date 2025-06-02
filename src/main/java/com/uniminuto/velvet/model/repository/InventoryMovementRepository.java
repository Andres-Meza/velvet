package com.uniminuto.velvet.model.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uniminuto.velvet.model.entity.InventoryMovement;
import com.uniminuto.velvet.model.entity.MovementType;

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

    @Query("""
        SELECT m FROM InventoryMovement m
        JOIN FETCH m.product p
        JOIN FETCH m.inventoryStock s
        JOIN FETCH s.location l
        WHERE (:sede IS NULL OR l.name = :sede)
        AND (:fechaInicio IS NULL OR m.movementDate >= :fechaInicio)
        AND (:fechaFin IS NULL OR m.movementDate <= :fechaFin)
        AND m.movementType.name = 'VENTA'
    """)
    List<InventoryMovement> findByFilters(
        @Param("sede") String sede,
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin
    );

  @Query("""
      SELECT m FROM InventoryMovement m
      WHERE (:locationId IS NULL OR m.inventoryStock.location.id = :locationId)
      AND (:startDate IS NULL OR m.movementDate >= :startDate)
      AND (:endDate IS NULL OR m.movementDate <= :endDate)
  """)
  List<InventoryMovement> findByFilters(Long locationId, LocalDate startDate, LocalDate endDate);

    @Query("SELECT m FROM InventoryMovement m " +
            "JOIN m.order o " +
            "WHERE o.orderStatus.name = :statusName " +
            "AND (:locationId IS NULL OR m.inventoryStock.location.id = :locationId) " +
            "AND m.movementDate BETWEEN :startDate AND :endDate")
    List<InventoryMovement> findVentasPorEstadoYSedeYFecha(
            @Param("statusName") String statusName,
            @Param("locationId") Long locationId,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}