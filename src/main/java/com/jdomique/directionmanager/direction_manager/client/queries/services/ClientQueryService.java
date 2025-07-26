package com.jdomique.directionmanager.direction_manager.client.queries.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdomique.directionmanager.direction_manager.client.common.TOPICS;
import com.jdomique.directionmanager.direction_manager.client.common.dto.ClientEvent;
import com.jdomique.directionmanager.direction_manager.client.queries.entities.ClientQuery;
import com.jdomique.directionmanager.direction_manager.client.queries.entities.AddressQuery;
import com.jdomique.directionmanager.direction_manager.client.queries.repository.ClientQueryRepository;
import com.jdomique.directionmanager.direction_manager.client.queries.repository.DirectionQueryRepository;

@Service
public class ClientQueryService implements IClientQueryService {

  @Autowired
  ClientQueryRepository clientQueryRepository;

  @Autowired
  DirectionQueryRepository directionQueryRepository;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public Iterable<ClientQuery> findAll() {
    return clientQueryRepository.findAll();
  }

  @Override
  public ClientQuery findById(String clientId) {

    Optional<ClientQuery> optionalClient = clientQueryRepository.findById(clientId);
    if (optionalClient.isEmpty()) {
      throw new RuntimeException("Client not exist.");
    }
    return optionalClient.get();
  }

  @Override
  public Iterable<AddressQuery> findAllDirections() {
    return directionQueryRepository.findAll();
  }

  @Override
  public Iterable<AddressQuery> findDirectionsByClientId(String clientId) {
    return directionQueryRepository.findByClientId(clientId);

  }

  @Override
  public AddressQuery findDirectionById(Long directionId) {

    Optional<AddressQuery> optionalDirection = directionQueryRepository.findById(directionId);
    if (optionalDirection.isEmpty()) {
      throw new RuntimeException("Client not exist.");
    }
    return optionalDirection.get();
  }

  @KafkaListener(topics = TOPICS.CLIENT_TOPIC, groupId = "directionmanager")
  public void handle(ClientEvent<?> clientEvent) {

    switch (clientEvent.getEventType()) {
      case CREATED:
        ClientQuery newClient = objectMapper.convertValue(
            clientEvent.getData(),
            ClientQuery.class);
        clientQueryRepository.save(newClient);
        break;
      case UPDATED:
        ClientQuery updateClientMapper = objectMapper.convertValue(
            clientEvent.getData(),
            ClientQuery.class);

        Optional<ClientQuery> updateClientOptional = clientQueryRepository.findById(updateClientMapper.getId());

        if (updateClientOptional.isPresent()) {
          ClientQuery updateClient = updateClientOptional.get();
          updateClient.setName(updateClientMapper.getName());
          clientQueryRepository.save(updateClient);
        }
        break;
      case DELETED:
        ClientQuery deleteClientMapper = objectMapper.convertValue(
            clientEvent.getData(),
            ClientQuery.class);

        Optional<ClientQuery> deleteClientOptional = clientQueryRepository.findById(deleteClientMapper.getId());

        if (deleteClientOptional.isPresent()) {
          clientQueryRepository.delete(deleteClientOptional.get());
        }
        break;

      case ADDED_ADDRESS:
        AddressQuery newDirection = objectMapper.convertValue(
            clientEvent.getData(),
            AddressQuery.class);

        ClientQuery client = clientQueryRepository.findById(newDirection.getClient().getId()).get();
        newDirection.setClient(client);

        directionQueryRepository.save(newDirection);
        break;
      case DELETED_ADDRESS:
        AddressQuery deleteDirectiontMapper = objectMapper.convertValue(
            clientEvent.getData(),
            AddressQuery.class);

        Optional<AddressQuery> deleteDirectionOptional = directionQueryRepository
            .findById(deleteDirectiontMapper.getId());

        if (deleteDirectionOptional.isPresent()) {
          directionQueryRepository.delete(deleteDirectionOptional.get());
        }
        break;

      default:
        break;
    }
  }

}