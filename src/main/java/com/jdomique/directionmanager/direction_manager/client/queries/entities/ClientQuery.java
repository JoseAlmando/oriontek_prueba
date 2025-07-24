package com.jdomique.directionmanager.direction_manager.client.queries.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientQuery {
  @Id
  private String id;
  @Column(nullable = false)
  private String name;
}
