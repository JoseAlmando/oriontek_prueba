package com.jdomique.directionmanager.direction_manager.client.queries.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "client_id", "direction" }))
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectionQuery {

  @Id
  private Long id;

  private String direction;

  @ManyToOne
  private ClientQuery client;

}
