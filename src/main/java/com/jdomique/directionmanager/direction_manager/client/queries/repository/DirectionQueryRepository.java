package com.jdomique.directionmanager.direction_manager.client.queries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jdomique.directionmanager.direction_manager.client.queries.entities.DirectionQuery;

@Repository
public interface DirectionQueryRepository extends JpaRepository<DirectionQuery, Long> {
  Iterable<DirectionQuery> findByClientId(String clientId);
}
