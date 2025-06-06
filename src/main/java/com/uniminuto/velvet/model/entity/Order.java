package com.uniminuto.velvet.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String orderNumber;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  @JsonBackReference
  private User user;

  @ManyToOne
  @JoinColumn(name = "location_id", nullable = false)
  @JsonBackReference
  private Location location;

  @ManyToOne
  @JoinColumn(name = "table_id")
  @JsonBackReference
  private Tables table;

  @ManyToOne
  @JoinColumn(name = "order_status_id", nullable = false)
  private OrderStatus orderStatus;

  @ManyToOne
  @JoinColumn(name = "payment_method_id")
  private PaymentMethod paymentMethod;

  @Column(name = "total_amount", nullable = false, precision = 10, scale = 2)
  private BigDecimal totalAmount = BigDecimal.ZERO;

  @Column(name = "is_paid", nullable = false)
  @Builder.Default
  private boolean paid = false;

  @Column(name = "order_date", nullable = false)
  private LocalDateTime orderDate;

  @Column(name = "completed_date")
  private LocalDateTime completedDate;

  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;

  @Builder.Default
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
  @JsonManagedReference
  @ToString.Exclude
  private List<OrderDetail> orderDetails = new ArrayList<>();

  public void addOrderDetail(OrderDetail detail) {
    orderDetails.add(detail);
    detail.setOrder(this);
  }

  public void removeOrderDetail(OrderDetail detail) {
    orderDetails.remove(detail);
    detail.setOrder(null);
  }

  @PrePersist
  @PreUpdate
  private void calculateTotalAmount() {
    if (orderDetails != null && !orderDetails.isEmpty()) {
      this.totalAmount = orderDetails.stream()
              .map(OrderDetail::getSubtotal)
              .filter(java.util.Objects::nonNull)
              .reduce(BigDecimal.ZERO, BigDecimal::add);
    } else {
      this.totalAmount = BigDecimal.ZERO;
    }
  }
}