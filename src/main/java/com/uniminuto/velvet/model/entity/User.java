package com.uniminuto.velvet.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(
  name = "users",
  uniqueConstraints = {
    @UniqueConstraint(columnNames = {"username"}, name = "uk_user_username"),
    @UniqueConstraint(columnNames = {"email"}, name = "uk_user_email"),
    @UniqueConstraint(columnNames = {"document"}, name = "uk_user_document")
  }
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false)
  private String username;
  
  @Column(nullable = false)
  private String password;
  
  @Column(nullable = false)
  private String name;
  
  @Column(name = "last_name", nullable = false)
  private String lastName;
  
  @Column(nullable = false)
  private String email;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "document_type_id", nullable = false)
  @ToString.Exclude
  private DocumentType documentType;
  
  @Column(nullable = false, length = 50)
  private String document;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "role_id", nullable = false)
  @ToString.Exclude
  private Role role;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id", nullable = false)
  @ToString.Exclude
  private Location location;
  
  @Builder.Default
  @Column(nullable = false)
  private Boolean active = true;
  
  @Column(name = "last_login")
  private LocalDateTime lastLogin;
  
  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
  
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
}