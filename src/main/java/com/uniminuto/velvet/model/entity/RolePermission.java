package com.uniminuto.velvet.model.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role_has_permission")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolePermission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "permission_id", nullable = false)
  private Permission permission;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assigned_by", nullable = false)
  private User assignedBy;

  @Builder.Default
  @Column(name = "assigned_at", nullable = false, updatable = false)
  private LocalDateTime assignedAt = LocalDateTime.now();
}