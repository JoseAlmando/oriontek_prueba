package com.jdomique.directionmanager.direction_manager.client.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEvent<T> {
  
  EClientEventType eventType;
  T data;
}
