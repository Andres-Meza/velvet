package com.uniminuto.velvet.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
  name = "document_types",
  uniqueConstraints = { @UniqueConstraint(columnNames = {"name"}, name = "document_types_name_key") }
)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Builder
@AllArgsConstructor
public class DocumentType {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @Column(nullable = false, length = 50)
  private String name;
  
  @Column()
  private String description;
  
  @OneToMany(mappedBy = "documentType", fetch = FetchType.LAZY)
  @Builder.Default
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<User> users = new HashSet<>();
}