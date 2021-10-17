package com.dbowland.javatemplate.services

import com.dbowland.javatemplate.models.User
import com.dbowland.javatemplate.repositories.UserRepository
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.fge.jsonpatch.JsonPatch
import org.spockframework.spring.SpringSpy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ExampleServiceTests extends Specification {
  private final ObjectMapper objectMapper = new ObjectMapper()

  @Autowired
  private ExampleService exampleService

  @SpringSpy
  private UserRepository userRepository

  private User user

  def setup() {
    user = new User('Evie', 'e@ves.drop')
  }

  def 'when createUser then expect user with ID returned'() {
    when:
    final User result = exampleService.createUser(user)

    then:
    1 * userRepository.save(user) >> { User modifiedUser ->
      modifiedUser.id = 10
      return modifiedUser
    }
    result.id
    result.getName() == user.getName()
    result.getEmail() == user.getEmail()
  }

  def 'when deleteUser then expect delete called'() {
    given:
    final Integer id = 2

    when:
    exampleService.deleteUser(id)

    then:
    1 * userRepository.deleteById(id) >> null
  }

  def 'when getAllUsers expect a list of new Users'() {
    given:
    final User user2 = new User('Michelle', 'also-testing@java.prototype')

    when:
    List<User> result = exampleService.getAllUsers()

    then:
    1 * userRepository.findAll() >> [user, user2]
    result == [user, user2]
  }

  def 'when getUser expect a User'() {
    given:
    final Integer id = 4

    when:
    Optional<User> result = exampleService.getUser(id)

    then:
    1 * userRepository.getById(id) >> Optional.of(user)
    result.get() == user
  }

  def 'when patchUser expect a modified User'() {
    given:
    final Integer id = 6
    final User expectedUser = new User('George', user.email)

    final ObjectNode objectNode = JsonNodeFactory.instance.objectNode()
    objectNode.put('op', 'replace')
    objectNode.put('path', '/name')
    objectNode.put('value', expectedUser.name)
    final ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode()
    arrayNode.add(objectNode)
    final JsonPatch jsonPatch = JsonPatch.fromJson(arrayNode)

    when:
    Optional<User> result = exampleService.patchUser(id, jsonPatch)

    then:
    1 * userRepository.getById(id) >> Optional.of(user)
    1 * userRepository.save(_) >> { User modifiedUser -> modifiedUser }
    result.get().name == expectedUser.name
  }

  def 'when updateUser then expect true'() {
    given:
    final Integer id = 9

    when:
    Optional<User> result = exampleService.updateUser(id, user)

    then:
    1 * userRepository.getById(id) >> Optional.of(user)
    1 * userRepository.save(_) >> { User modifiedUser -> modifiedUser }
    result.get() == user
  }
}
