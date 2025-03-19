package com.uniminuto.velvet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
  name = "payment_methods",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}, name = "payment_methods_name_key") }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethod {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, length = 50)
  private String name;
  
  @Column(length = 255)
  private String description;
  
  @OneToMany(mappedBy = "paymentMethod", fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Order> orders = new HashSet<>();
}