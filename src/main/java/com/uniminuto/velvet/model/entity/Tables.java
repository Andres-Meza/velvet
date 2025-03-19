package com.uniminuto.velvet.model.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
  name = "tables",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"number", "location_id"}, name = "uk_table_number_location") }
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tables {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String number;
  
  @Column(length = 255)
  private String description;
  
  @Column(nullable = false)
  private Integer capacity;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id", nullable = false)
  private Location location;
  
  @Builder.Default
  @Column(nullable = false)
  private Boolean active = true;
  
  @Builder.Default
  @OneToMany(mappedBy = "table", fetch = FetchType.LAZY)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private List<Order> orders = new ArrayList<>();
}