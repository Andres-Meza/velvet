package com.uniminuto.velvet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
  name = "tables",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"number", "location_id"}, name = "uk_table_number_location") }
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class Tables {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String number;
  
  @Column()
  private String description;
  
  @Column(nullable = false)
  private Integer capacity;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id", nullable = false)
  @ToString.Exclude
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