package com.jdomique.directionmanager.direction_manager.client.commands.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdomique.directionmanager.direction_manager.client.commands.dto.AddDirectionClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.CreateClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.DeleteClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.UpdateClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.services.IClientCommandService;
import com.jdomique.directionmanager.direction_manager.client.common.dto.ClientEvent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/client")
public class ClientCommandController {

  @Autowired
  IClientCommandService clientCommandService;

  @PostMapping
  public ResponseEntity<?> postClient(@RequestBody ClientEvent<CreateClientCommand> event) {
    return ResponseEntity.ok().body(clientCommandService.postClient(event));
  }

  @PutMapping
  public ResponseEntity<?> putClient(@RequestBody ClientEvent<UpdateClientCommand> event) {
    return ResponseEntity.ok(clientCommandService.putClient(event));
  }

  @DeleteMapping
  public ResponseEntity<?> deleteClient(@RequestBody ClientEvent<DeleteClientCommand> event) {
    boolean deleted = clientCommandService.delete(event);
    return ResponseEntity.status(deleted ? 200 : 404).body(deleted);
  }

  @PostMapping("/direction")
  public ResponseEntity<?> postDirection(@RequestBody ClientEvent<AddDirectionClientCommand> event) {
    return ResponseEntity.ok(clientCommandService.postDirection(event));
  }
}
