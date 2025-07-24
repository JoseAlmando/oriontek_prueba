package com.jdomique.directionmanager.direction_manager.client.queries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jdomique.directionmanager.direction_manager.client.queries.entities.ClientQuery;

@Repository
public interface ClientQueryRepository extends JpaRepository<ClientQuery, String>{

}
