package com.jdomique.directionmanager.direction_manager.client.commands.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jdomique.directionmanager.direction_manager.client.commands.entities.AddressCommand;

@Repository
public interface AddressCommandRepository extends JpaRepository<AddressCommand, Long>{
  boolean existsByAddressAndClientId(String direction, String clientId);

}
