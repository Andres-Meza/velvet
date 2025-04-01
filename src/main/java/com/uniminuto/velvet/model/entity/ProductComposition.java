package com.uniminuto.velvet.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product_compositions")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class ProductComposition {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne
  @JoinColumn(name = "composite_product_id", nullable = false)
  @JsonBackReference
  private Product compositeProduct;
  
  @ManyToOne
  @JoinColumn(name = "ingredient_product_id", nullable = false)
  @JsonBackReference
  private Product ingredientProduct;
  
  @Column(nullable = false)
  private Double quantity;
  
  @ManyToOne
  @JoinColumn(name = "unit_of_measure_id", nullable = false)
  private UnitOfMeasure unitOfMeasure;
}