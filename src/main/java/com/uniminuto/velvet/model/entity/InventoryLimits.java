package com.uniminuto.velvet.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(
  name = "inventory_limits",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"product_id", "location_id"}, name = "uk_product_location_limits") }
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class InventoryLimits {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  @JsonBackReference
  @ToString.Exclude
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id", nullable = false)
  @JsonBackReference
  @ToString.Exclude
  private Location location;

  @Column(name = "min_stock", nullable = false)
  @Min(0)
  private Long minStock;

  @Column(name = "max_stock")
  @Min(1)
  private Long maxStock;
  
  @PrePersist
  @PreUpdate
  protected void validateStockLevels() {
    if (this.minStock != null && this.maxStock != null && this.minStock >= this.maxStock) {
      throw new IllegalStateException("El stock mínimo debe ser menor que el stock máximo");
    }
  }
}