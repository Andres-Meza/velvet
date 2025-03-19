package com.uniminuto.velvet.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(
  name = "inventory_limits",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"product_id", "location_id"}, name = "uk_product_location_limits") }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryLimits {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  @JsonBackReference
  private Product product;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id", nullable = false)
  @JsonBackReference
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