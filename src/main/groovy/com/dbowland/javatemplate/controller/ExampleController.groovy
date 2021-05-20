package com.dbowland.javatemplate.controller

import com.dbowland.javatemplate.domain.User
import com.dbowland.javatemplate.service.ExampleService
import groovy.util.logging.Slf4j
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.Assert
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

import javax.validation.Valid

@RestController
@RequestMapping(value = "/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
@Slf4j
class ExampleController {

  ExampleService exampleService

  ExampleController(final ExampleService exampleService) {
    Assert.notNull(exampleService, "exampleService must not be null")
    this.exampleService = exampleService
  }

  @GetMapping("/users/{id}")
  @ResponseStatus(HttpStatus.OK)
  User getSingleUser(@PathVariable("id") final String id) {
    return exampleService.getUser(id)
  }

  @GetMapping("/users")
  @ResponseStatus(HttpStatus.OK)
  List<User> getAllUsers() {
    return exampleService.getAllUsers()
  }

  @PostMapping("/users")
  @ResponseStatus(HttpStatus.CREATED)
  Map<String, String> addNewUser(@Valid @RequestBody final User user) {
    return [ id: exampleService.createUser(user) ]
  }

  @PutMapping("/users")
  ResponseEntity updateUser(@Valid @RequestBody final User user) {
    return ResponseEntity.status(
        exampleService.updateUser(user) ? HttpStatus.NO_CONTENT : HttpStatus.CONFLICT).build()
  }

  @DeleteMapping("/users/{id}")
  ResponseEntity deleteUser(@PathVariable("id") final String id) {
    return ResponseEntity.status(
        exampleService.deleteUser(id) ? HttpStatus.NO_CONTENT : HttpStatus.INTERNAL_SERVER_ERROR).build()
  }

}
