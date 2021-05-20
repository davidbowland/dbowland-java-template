package com.dbowland.javatemplate.controller

import com.dbowland.javatemplate.service.ExampleService
import org.springframework.http.HttpStatus
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/status")
class StatusController {

  ExampleService exampleService

  StatusController(final ExampleService exampleService) {
    Assert.notNull(exampleService, "exampleService must not be null")
    this.exampleService = exampleService
  }

  @RequestMapping("/health")
  @ResponseStatus(HttpStatus.OK)
  void health() { }

  @RequestMapping("/liveness")
  @ResponseStatus(HttpStatus.OK)
  void liveness() {
    if (!exampleService.isHealthy()) {
      throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE)
    }
  }

}
