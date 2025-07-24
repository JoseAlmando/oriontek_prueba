package com.jdomique.directionmanager.direction_manager.client.commands.entities;

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
public class ClientCommand {
  @Id
  private String id;
  @Column(nullable = false)
  private String name;
}
