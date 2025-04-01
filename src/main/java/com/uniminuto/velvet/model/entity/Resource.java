package com.uniminuto.velvet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
  name = "resources",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}, name = "resources_name_key") }
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Resource {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Permission> permissions = new HashSet<>();
}