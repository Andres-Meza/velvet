package com.uniminuto.velvet.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
  name = "locations",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}, name = "uk_location_name") }
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, length = 100)
  private String name;
  
  @Column(nullable = false)
  private String address;
  
  @Column(length = 500)
  private String description;
  
  @Column(length = 20)
  private String phone;
  
  @Builder.Default
  @Column(nullable = false)
  private Boolean active = true;
  
  @CreationTimestamp
  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;
  
  @UpdateTimestamp
  @Column(name = "updated_at", nullable = false)
  private LocalDateTime updatedAt;
  
  @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @JsonManagedReference
  private List<InventoryStock> inventoryStocks = new ArrayList<>();
  
  @OneToMany(mappedBy = "location", fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @JsonManagedReference
  private List<User> users = new ArrayList<>();
}