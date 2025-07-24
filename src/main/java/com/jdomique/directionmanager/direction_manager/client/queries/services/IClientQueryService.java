package com.jdomique.directionmanager.direction_manager.client.queries.services;

import com.jdomique.directionmanager.direction_manager.client.queries.entities.ClientQuery;
import com.jdomique.directionmanager.direction_manager.client.queries.entities.DirectionQuery;

public interface IClientQueryService {
  public Iterable<ClientQuery> findAll();
  public ClientQuery findById(String clientId);
  public Iterable<DirectionQuery> findAllDirections();
  public Iterable<DirectionQuery> findDirectionsByClientId(String clientId);
  public DirectionQuery findDirectionById(Long directionId);
}
