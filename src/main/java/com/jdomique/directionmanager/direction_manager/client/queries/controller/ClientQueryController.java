package com.jdomique.directionmanager.direction_manager.client.queries.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdomique.directionmanager.direction_manager.client.queries.entities.ClientQuery;
import com.jdomique.directionmanager.direction_manager.client.queries.entities.AddressQuery;
import com.jdomique.directionmanager.direction_manager.client.queries.services.IClientQueryService;

@RestController
@RequestMapping("/api/client")
public class ClientQueryController {

  @Autowired
  IClientQueryService clientQueryService;

  @GetMapping
  public ResponseEntity<?> findAll() {
    return ResponseEntity.ok().body(clientQueryService.findAll());
  }

  @GetMapping("/{clientId}")
  public ClientQuery getClienteById(@PathVariable String clientId) {
    return clientQueryService.findById(clientId);
  }

  @GetMapping("/address")
  public Iterable<AddressQuery> findAllDirections() {
    return clientQueryService.findAllDirections();
  }

  @GetMapping("/{clientId}/address")
  public Iterable<AddressQuery> findDirectionsByClient(@PathVariable String clientId) {
    return clientQueryService.findDirectionsByClientId(clientId);
  }

  @GetMapping("/address/{addressId}")
  public AddressQuery getDireccionById(@PathVariable Long addressId) {
    return clientQueryService.findDirectionById(addressId);
  }
}
