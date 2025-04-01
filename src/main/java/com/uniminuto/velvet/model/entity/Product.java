package com.uniminuto.velvet.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
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
  @ToString.Exclude
  private ProductType productType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subcategory_id", nullable = false)
  @ToString.Exclude
  private SubCategory subCategory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "unit_of_measure_id", nullable = false)
  @ToString.Exclude
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