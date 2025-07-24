package com.jdomique.directionmanager.direction_manager.client.commands.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.jdomique.directionmanager.direction_manager.client.commands.dto.AddDirectionClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.CreateClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.DeleteClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.DeleteDirectionClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.dto.UpdateClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.entities.ClientCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.entities.DirectionCommand;
import com.jdomique.directionmanager.direction_manager.client.commands.repository.ClientCommandRepository;
import com.jdomique.directionmanager.direction_manager.client.commands.repository.DirectionCommandRepository;
import com.jdomique.directionmanager.direction_manager.client.common.TOPICS;
import com.jdomique.directionmanager.direction_manager.client.common.dto.ClientEvent;

@Service
public class ClientCommandService implements IClientCommandService {

  @Autowired
  ClientCommandRepository clientCommandRepository;

  @Autowired
  DirectionCommandRepository directionCommandRepository;

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
  public DirectionCommand postDirection(ClientEvent<AddDirectionClientCommand> event) {

    boolean directionExist = directionCommandRepository.existsByDirectionAndClientId(event.getData().direction(),
        event.getData().clientId());
    if (directionExist) {
      throw new RuntimeException("Direction already added by client.");

    }

    Optional<ClientCommand> optionalClient = clientCommandRepository.findById(event.getData().clientId());

    if (optionalClient.isEmpty()) {
      throw new RuntimeException("Client not exist.");
    }

    ClientCommand client = optionalClient.get();

    DirectionCommand direction = new DirectionCommand();
    direction.setClient(client);
    direction.setDirection(event.getData().direction());

    directionCommandRepository.save(direction);

    ClientEvent<DirectionCommand> eventSend = new ClientEvent<DirectionCommand>(event.getEventType(), direction);

    kafkaTemplate.send(TOPICS.CLIENT_TOPIC, eventSend);

    return direction;
  }

  @Override
  public boolean deleteDirection(ClientEvent<DeleteDirectionClientCommand> event) {
    Optional<DirectionCommand> optionalDirection = directionCommandRepository.findById(event.getData().directionId());

    if (optionalDirection.isEmpty()) {
      throw new RuntimeException("Client not exist.");
    }

    DirectionCommand direction = optionalDirection.get();
    directionCommandRepository.delete(direction);

    ClientEvent<DirectionCommand> eventSend = new ClientEvent<DirectionCommand>(event.getEventType(), direction);

    kafkaTemplate.send(TOPICS.CLIENT_TOPIC, eventSend);

    return true;
  }

}
