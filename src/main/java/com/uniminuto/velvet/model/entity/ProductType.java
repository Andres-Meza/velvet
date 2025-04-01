package com.uniminuto.velvet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
  name = "product_types",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}, name = "product_types_name_key") }
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class ProductType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 50)
  private String name;

  @Column()
  private String description;

  @OneToMany(mappedBy = "productType", fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Product> products = new HashSet<>();
}