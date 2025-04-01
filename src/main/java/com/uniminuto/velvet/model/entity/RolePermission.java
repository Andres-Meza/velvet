package com.uniminuto.velvet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "role_has_permission")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class RolePermission {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", nullable = false)
  @ToString.Exclude
  private Role role;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "permission_id", nullable = false)
  @ToString.Exclude
  private Permission permission;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "assigned_by", nullable = false)
  @ToString.Exclude
  private User assignedBy;

  @Builder.Default
  @Column(name = "assigned_at", nullable = false, updatable = false)
  private LocalDateTime assignedAt = LocalDateTime.now();
}