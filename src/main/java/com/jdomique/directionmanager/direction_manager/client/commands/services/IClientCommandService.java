package com.jdomique.directionmanager.direction_manager.client.commands.services;

import com.jdomique.directionmanager.direction_manager.client.commands.dto.AddDirectionClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.CreateClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.DeleteClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.DeleteDirectionClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.UpdateClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.entities.ClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.entities.DirectionCommand;
import com.jdomique.directionmanager.direction_manager.client.common.dto.ClientEvent;

public interface IClientCommandService {

  public ClientCommand postClient(ClientEvent<CreateClientCommand> event);
  public ClientCommand putClient(ClientEvent<UpdateClientCommand> event);
  public boolean delete(ClientEvent<DeleteClientCommand> event);

  public DirectionCommand postDirection(ClientEvent<AddDirectionClientCommand> event);
  public boolean deleteDirection(ClientEvent<DeleteDirectionClientCommand> event);

  
}
