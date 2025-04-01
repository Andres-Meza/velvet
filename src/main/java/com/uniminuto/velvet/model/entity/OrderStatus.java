package com.uniminuto.velvet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
  name = "order_statuses",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}, name = "order_statuses_name_key") }
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class OrderStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, length = 50)
  private String name;
  
  @Column()
  private String description;
  
  @OneToMany(mappedBy = "orderStatus", fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Order> orders = new HashSet<>();
}