package com.uniminuto.velvet.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "inventory_movements")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryMovement {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne
  @JoinColumn(name = "inventory_stock_id", nullable = false)
  @JsonBackReference
  private InventoryStock inventoryStock;
  
  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  @JsonBackReference
  private Product product;
  
  @ManyToOne
  @JoinColumn(name = "movement_type_id", nullable = false)
  private MovementType movementType;
  
  @Column(nullable = false)
  private Integer quantity;
  
  @Column(name = "unit_cost", precision = 10, scale = 2)
  private BigDecimal unitCost;
  
  @Column(name = "movement_date", nullable = false)
  private LocalDateTime movementDate;
  
  @ManyToOne
  @JoinColumn(name = "order_id")
  @JsonBackReference
  private Order order;
  
  @ManyToOne
  @JoinColumn(name = "created_by", nullable = false)
  @JsonBackReference
  private User createdBy;
  
  @Column(length = 255)
  private String reference;
  
  @Column(length = 500)
  private String notes;
}