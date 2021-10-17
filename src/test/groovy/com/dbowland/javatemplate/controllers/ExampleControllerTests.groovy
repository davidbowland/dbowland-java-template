package com.dbowland.javatemplate.controllers

import com.dbowland.javatemplate.models.User
import com.dbowland.javatemplate.services.ExampleService
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonpatch.JsonPatchException
import org.spockframework.spring.SpringSpy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExampleControllerTests extends Specification {
  private final ObjectMapper objectMapper = new ObjectMapper()

  @Autowired
  private MockMvc mvc

  @SpringSpy
  private ExampleService exampleService

  private User user

  def setup() {
    user = new User('Testuser', 'testing@java.template')
  }

  // Create user

  def 'when POST request to /v1/users expect created status on success'() {
    given:
    final Integer id = 3

    when:
    def result = mvc.perform(post('/v1/users')
        .content(objectMapper.writeValueAsString(user))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.createUser(_) >> { User newUser ->
      assert objectMapper.writeValueAsString(newUser) == objectMapper.writeValueAsString(user)
      newUser.id = id
      return newUser
    }
    result.andExpect(status().isCreated())
    result.andReturn().getResponse().getHeader('Location').endsWith("/${id}")
  }

  // Delete user

  def 'when DELETE request to /v1/users/{id} expect no content status on success'() {
    given:
    final Integer id = 8

    when:
    def result = mvc.perform(delete("/v1/users/${id}"))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.deleteUser(id) >> true
    result.andExpect(status().isNoContent())
  }

  def 'when DELETE request to /v1/users/{id} expect not found status on failure'() {
    given:
    final Integer id = 1

    when:
    def result = mvc.perform(delete("/v1/users/${id}"))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.deleteUser(_) >> false
    result.andExpect(status().isNotFound())
  }

  // Get one user

  def 'when GET request to /v1/users/{id} expect a user'() {
    given:
    final Integer id = 5

    when:
    def result = mvc.perform(get("/v1/users/${id}"))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.getUser(id) >> Optional.of(user)
    result.andExpect(status().isOk())
    result.andExpect(content().json(objectMapper.writeValueAsString(user)))
  }

  def 'when GET request to /v1/users/{id} expect not found when no user'() {
    given:
    final Integer id = 2

    when:
    def result = mvc.perform(get("/v1/users/${id}"))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.getUser(id) >> Optional.empty()
    result.andExpect(status().isNotFound())
  }

  // Get all users

  def 'when GET request to /v1/users expect a list of users'() {
    given:
    final User user2 = new User('Michelle', 'also-testing@java.prototype')

    when:
    def result = mvc.perform(get('/v1/users'))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.getAllUsers() >> [user, user2]
    result.andExpect(status().isOk())
    result.andExpect(content().json(objectMapper.writeValueAsString([user, user2])))
  }

  // Patch user

  def 'when PATCH request to /v1/users/{id} expect modified user on success'() {
    given:
    final Integer id = 6
    final User expectedUser = new User('Jennifer', user.email)
    final def jsonPatch = [
      [
        op: 'replace',
        path: '/name',
        value: expectedUser.name
      ]
    ]

    when:
    def result = mvc.perform(patch("/v1/users/${id}")
        .content(objectMapper.writeValueAsString(jsonPatch))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.patchUser(_, _) >> Optional.of(expectedUser)
    result.andExpect(status().isOk())
    result.andExpect(content().json(objectMapper.writeValueAsString(expectedUser)))
  }

  def 'when PATCH request to /v1/users/{id} expect not found status on failure'() {
    given:
    final Integer id = 9
    final def jsonPatch = [
      [
        op: 'replace',
        path: '/name',
        value: 'Frank'
      ]
    ]

    when:
    def result = mvc.perform(patch("/v1/users/${id}")
        .content(objectMapper.writeValueAsString(jsonPatch))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.patchUser(_, _) >> Optional.empty()
    result.andExpect(status().isNotFound())
  }

  def 'when PATCH request to /v1/users/{id} expect bad request status when malformed'() {
    given:
    final Integer id = 0
    final def jsonPatch = [
      [
        op: 'replace',
        path: '/doesNotExist',
        value: 'whatever'
      ]
    ]

    when:
    def result = mvc.perform(patch("/v1/users/${id}")
        .content(objectMapper.writeValueAsString(jsonPatch))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.patchUser(_, _) >> {
      throw new JsonPatchException('Operation replace is invalid')
    }
    result.andExpect(status().isBadRequest())
  }

  // Update user

  def 'when PUT request to /v1/users/{id} expect no content status on success'() {
    given:
    final Integer id = 7

    when:
    def result = mvc.perform(put("/v1/users/${id}")
        .content(objectMapper.writeValueAsString(user))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.updateUser(_, _) >> { Integer passedId, User newUser ->
      assert objectMapper.writeValueAsString(newUser) == objectMapper.writeValueAsString(user)
      assert passedId == id
      return Optional.of(newUser)
    }
    result.andExpect(status().isNoContent())
  }

  def 'when PUT request to /v1/users/{id} expect not found status on failure'() {
    given:
    final Integer id = 4

    when:
    def result = mvc.perform(put("/v1/users/${id}")
        .content(objectMapper.writeValueAsString(user))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.updateUser(_, _) >> Optional.empty()
    result.andExpect(status().isNotFound())
  }
}
