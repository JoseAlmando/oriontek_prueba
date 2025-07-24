package com.jdomique.directionmanager.direction_manager.client.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdomique.directionmanager.direction_manager.client.common.dto.EClientEventType;


@RestController
@RequestMapping("/api/common")
public class CommonController {

  @GetMapping("client-event-types")
  public ResponseEntity<?> getClientEventsType() {
      return ResponseEntity.ok().body(EClientEventType.values());
  }
  
}
