package com.jdomique.directionmanager.direction_manager.client.commands.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jdomique.directionmanager.direction_manager.client.commands.entities.ClientCommand;

@Repository
public interface ClientCommandRepository extends JpaRepository<ClientCommand, String>{

}
