package com.uniminuto.velvet.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_details")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class OrderDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  @JsonBackReference
  private Order order;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  @JsonBackReference
  private Product product;

  @Column(nullable = false)
  @Min(1)
  private Integer quantity;

  @Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
  private BigDecimal unitPrice;

  @Column(nullable = false, precision = 10, scale = 2)
  private BigDecimal subtotal;

  @Column(name = "product_notes")
  private String productNotes;

  // Método para validar que subtotal = cantidad * precio unitario
  @PrePersist
  @PreUpdate
  protected void validateSubtotal() {
    if (this.quantity != null && this.unitPrice != null) {
        this.subtotal = this.unitPrice.multiply(BigDecimal.valueOf(this.quantity));
    }
  }
}