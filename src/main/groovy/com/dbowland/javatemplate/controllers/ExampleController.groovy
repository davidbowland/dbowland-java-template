package com.dbowland.javatemplate.controllers

import com.dbowland.javatemplate.models.User
import com.dbowland.javatemplate.services.ExampleService
import com.github.fge.jsonpatch.JsonPatch
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

import javax.validation.Valid

@RestController
@RequestMapping(value = '/v1/users', produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Slf4j
class ExampleController {

  ExampleService exampleService

  ExampleController(final ExampleService exampleService) {
    Assert.notNull(exampleService, 'exampleService must not be null')
    this.exampleService = exampleService
  }

  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  List<User> getAllUsers() {
    return exampleService.getAllUsers()
  }

  @GetMapping('/{id}')
  ResponseEntity<User> getSingleUser(@PathVariable('id') final Integer id) {
    final Optional<User> user = exampleService.getUser(id)
    return ResponseEntity.of(user)
  }

  @PatchMapping(value = '/{id}', consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<User> patchUser(@PathVariable('id') final Integer id,
      @Valid @RequestBody final JsonPatch jsonPatch) {
    final Optional<User> user = exampleService.patchUser(id, jsonPatch)
    return ResponseEntity.of(user)
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<Void> postNewUser(@Valid @RequestBody final User user) {
    final User newUser = exampleService.createUser(user)
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path('/{id}')
        .buildAndExpand(newUser.getId())
        .toUri()
    return ResponseEntity.created(location).build()
  }

  @PutMapping(value = '/{id}', consumes = MediaType.APPLICATION_JSON_VALUE)
  ResponseEntity<User> updateUser(@PathVariable('id') final Integer id,
      @Valid @RequestBody final User user) {
    final result = exampleService.updateUser(id, user)
    final HttpStatus httpStatus = result ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND
    return ResponseEntity.status(httpStatus).build()
  }

  @DeleteMapping('/{id}')
  ResponseEntity<Void> deleteUser(@PathVariable('id') final Integer id) {
    final boolean result = exampleService.deleteUser(id)
    final HttpStatus httpStatus = result ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND
    return ResponseEntity.status(httpStatus).build()
  }
}
