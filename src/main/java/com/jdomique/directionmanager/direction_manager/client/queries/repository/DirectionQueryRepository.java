package com.jdomique.directionmanager.direction_manager.client.queries.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jdomique.directionmanager.direction_manager.client.queries.entities.AddressQuery;

@Repository
public interface DirectionQueryRepository extends JpaRepository<AddressQuery, Long> {
  Iterable<AddressQuery> findByClientId(String clientId);
}
