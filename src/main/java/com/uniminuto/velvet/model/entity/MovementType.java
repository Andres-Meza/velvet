package com.uniminuto.velvet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
  name = "movement_types",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}, name = "movement_types_name_key") }
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class MovementType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, length = 50)
  private String name;
  
  @Column()
  private String description;
  
  @Column(name = "affects_stock", nullable = false)
  private Boolean affectsStock;
  
  @OneToMany(mappedBy = "movementType", fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<InventoryMovement> inventoryMovements = new HashSet<>();
}