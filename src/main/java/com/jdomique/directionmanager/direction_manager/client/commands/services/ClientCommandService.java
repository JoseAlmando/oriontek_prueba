package com.jdomique.directionmanager.direction_manager.client.commands.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.jdomique.directionmanager.direction_manager.client.commands.dto.AddAddressClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.CreateClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.DeleteClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.DeleteAddressClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.UpdateClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.entities.ClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.entities.AddressCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.repository.ClientCommandRepository;
import com.jdomique.directionmanager.direction_manager.client.commands.repository.AddressCommandRepository;
import com.jdomique.directionmanager.direction_manager.client.common.TOPICS;
import com.jdomique.directionmanager.direction_manager.client.common.dto.ClientEvent;

@Service
public class ClientCommandService implements IClientCommandService {

  @Autowired
  ClientCommandRepository clientCommandRepository;

  @Autowired
  AddressCommandRepository addressCommandRepository;

  @Autowired
  KafkaTemplate<String, Object> kafkaTemplate;

  @Override
  public ClientCommand postClient(ClientEvent<CreateClientCommand> event) {

    Optional<ClientCommand> optionalClient = clientCommandRepository.findById(event.getData().clientId());

    if (optionalClient.isPresent()) {
      throw new RuntimeException("Client already exist.");
    }

    ClientCommand client = new ClientCommand(event.getData().clientId(), event.getData().name());
    clientCommandRepository.save(client);

    ClientEvent<ClientCommand> eventSend = new ClientEvent<ClientCommand>(event.getEventType(), client);

    kafkaTemplate.send(TOPICS.CLIENT_TOPIC, eventSend);

    return client;
  }

  @Override
  public ClientCommand putClient(ClientEvent<UpdateClientCommand> event) {
    Optional<ClientCommand> optionalClient = clientCommandRepository.findById(event.getData().clientId());

    if (!optionalClient.isPresent()) {
      throw new RuntimeException("Client not exist.");
    }

    ClientCommand client = optionalClient.get();
    client.setName(event.getData().newName());
    clientCommandRepository.save(client);

    ClientEvent<ClientCommand> eventSend = new ClientEvent<ClientCommand>(event.getEventType(), client);

    kafkaTemplate.send(TOPICS.CLIENT_TOPIC, eventSend);

    return client;
  }

  @Override
  public boolean delete(ClientEvent<DeleteClientCommand> event) {
    Optional<ClientCommand> optionalClient = clientCommandRepository.findById(event.getData().clientId());

    if (optionalClient.isEmpty()) {
      throw new IllegalArgumentException("Client not exist.");
    }

    ClientCommand client = optionalClient.get();
    clientCommandRepository.delete(client);

    ClientEvent<ClientCommand> eventSend = new ClientEvent<ClientCommand>(event.getEventType(), client);

    kafkaTemplate.send(TOPICS.CLIENT_TOPIC, eventSend);

    return true;
  }

  @Override
  public AddressCommand postAddress(ClientEvent<AddAddressClientCommand> event) {

    boolean addressExist = addressCommandRepository.existsByAddressAndClientId(event.getData().address(),
        event.getData().clientId());
    if (addressExist) {
      throw new RuntimeException("Address already added by client.");

    }

    Optional<ClientCommand> optionalClient = clientCommandRepository.findById(event.getData().clientId());

    if (optionalClient.isEmpty()) {
      throw new RuntimeException("Client not exist.");
    }

    ClientCommand client = optionalClient.get();

    AddressCommand address = new AddressCommand();
    address.setClient(client);
    address.setAddress(event.getData().address());

    addressCommandRepository.save(address);

    ClientEvent<AddressCommand> eventSend = new ClientEvent<AddressCommand>(event.getEventType(), address);

    kafkaTemplate.send(TOPICS.CLIENT_TOPIC, eventSend);

    return address;
  }

  @Override
  public boolean deleteAddress(ClientEvent<DeleteAddressClientCommand> event) {
    Optional<AddressCommand> optionalAddress = addressCommandRepository.findById(event.getData().directionId());

    if (optionalAddress.isEmpty()) {
      throw new RuntimeException("Client not exist.");
    }

    AddressCommand address = optionalAddress.get();
    addressCommandRepository.delete(address);

    ClientEvent<AddressCommand> eventSend = new ClientEvent<AddressCommand>(event.getEventType(), address);

    kafkaTemplate.send(TOPICS.CLIENT_TOPIC, eventSend);

    return true;
  }

}
