package com.uniminuto.velvet.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_type_id", nullable = false)
  private ProductType productType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subcategory_id", nullable = false)
  private SubCategory subCategory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unit_of_measure_id", nullable = false)
  private UnitOfMeasure unitOfMeasure;

  @Column(name = "purchase_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal purchasePrice;

  @Column(name = "sale_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal salePrice;

  @Builder.Default
  @Column(nullable = false)
  private Boolean active = true;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Builder.Default
  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<InventoryStock> inventoryStocks = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<OrderDetail> orderDetails = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "compositeProduct", fetch = FetchType.LAZY)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<ProductComposition> components = new ArrayList<>();

  @Builder.Default
  @OneToMany(mappedBy = "ingredientProduct", fetch = FetchType.LAZY)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<ProductComposition> usedIn = new ArrayList<>();

  // Método para obtener la categoría a través de la subcategoría
  public Category getCategory() {
    return this.subCategory != null ? this.subCategory.getCategory() : null;
  }
}