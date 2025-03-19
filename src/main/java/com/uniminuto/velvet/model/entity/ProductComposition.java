package com.uniminuto.velvet.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_compositions")
@Data
@Builder
@NoArgsConstructor
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