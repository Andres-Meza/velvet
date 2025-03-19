package com.uniminuto.velvet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
  name = "units_of_measure",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}, name = "units_of_measure_name_key") }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnitOfMeasure {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column(nullable = false, length = 10)
  private String symbol;

  @OneToMany(mappedBy = "unitOfMeasure", fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Product> products = new HashSet<>();
}