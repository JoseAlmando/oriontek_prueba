package com.jdomique.directionmanager.direction_manager.client.commands.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jdomique.directionmanager.direction_manager.client.commands.entities.DirectionCommand;

@Repository
public interface DirectionCommandRepository extends JpaRepository<DirectionCommand, Long>{
  boolean existsByDirectionAndClientId(String direction, String clientId);

}
