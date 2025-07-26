package com.jdomique.directionmanager.direction_manager.client.queries.services;

import com.jdomique.directionmanager.direction_manager.client.queries.entities.ClientQuery;
import com.jdomique.directionmanager.direction_manager.client.queries.entities.AddressQuery;

public interface IClientQueryService {
  public Iterable<ClientQuery> findAll();
  public ClientQuery findById(String clientId);
  public Iterable<AddressQuery> findAllDirections();
  public Iterable<AddressQuery> findDirectionsByClientId(String clientId);
  public AddressQuery findDirectionById(Long directionId);
}
