package com.dbowland.javatemplate.services

import com.dbowland.javatemplate.models.User
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.JsonNodeFactory
import com.fasterxml.jackson.databind.node.ObjectNode
import com.github.fge.jsonpatch.JsonPatch
import org.spockframework.spring.SpringSpy
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ExampleServiceTests extends Specification {
  private final ObjectMapper objectMapper = new ObjectMapper()

  @SpringSpy
  private ExampleService exampleService

  private User user

  def setup() {
    user = new User('Evie', 'e@ves.drop')
  }

  def 'when createUser then expect id back'() {
    when:
    String result = exampleService.createUser(user)

    then:
    result
  }

  def 'when deleteUser then expect true'() {
    given:
    final Integer id = 2

    when:
    Boolean result = exampleService.deleteUser(id)

    then:
    result
  }

  def 'when getAllUsers expect a list of new Users'() {
    when:
    List<User> result = exampleService.getAllUsers()

    then:
    result != null
  }

  def 'when getUser expect a User'() {
    given:
    final Integer id = 4

    when:
    Optional<User> result = exampleService.getUser(id)

    then:
    result.isPresent()
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
    result.get().name == expectedUser.name
  }

  def 'when updateUser then expect true'() {
    given:
    final Integer id = 9

    when:
    Boolean result = exampleService.updateUser(id, user)

    then:
    result
  }
}
