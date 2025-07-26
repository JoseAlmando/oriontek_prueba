package com.jdomique.directionmanager.direction_manager.client.commands.services;

import com.jdomique.directionmanager.direction_manager.client.commands.dto.AddAddressClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.CreateClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.DeleteClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.DeleteAddressClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.UpdateClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.entities.ClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.entities.AddressCommand;
import com.jdomique.directionmanager.direction_manager.client.common.dto.ClientEvent;

public interface IClientCommandService {

  public ClientCommand postClient(ClientEvent<CreateClientCommand> event);
  public ClientCommand putClient(ClientEvent<UpdateClientCommand> event);
  public boolean delete(ClientEvent<DeleteClientCommand> event);

  public AddressCommand postAddress(ClientEvent<AddAddressClientCommand> event);
  public boolean deleteAddress(ClientEvent<DeleteAddressClientCommand> event);

  
}
