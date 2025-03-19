package com.uniminuto.velvet.model.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
  name = "roles",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}, name = "roles_name_key") }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 30)
  private String name;

  @Column(length = 100)
  private String description;

  @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<RolePermission> rolePermissions = new HashSet<>();
}