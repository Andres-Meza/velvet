package com.uniminuto.velvet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
  name = "permissions",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}, name = "permissions_name_key") }
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Permission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column()
  private String description;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resource_id", foreignKey = @ForeignKey(name = "fk_permissions_resource"))
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Resource resource;

  @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<RolePermission> rolePermissions = new HashSet<>();
}