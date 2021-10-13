package com.dbowland.javatemplate.controller

import com.dbowland.javatemplate.domain.User
import com.dbowland.javatemplate.service.ExampleService
import com.fasterxml.jackson.databind.ObjectMapper
import org.spockframework.spring.SpringSpy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

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
    user = new User("Testguy", "testing-java-template@dbowland.com")
  }

  // Create user

  def "when POST request to /v1/users expect created status on success"() {
    given:
    final String id = "3"

    when:
    def result = mvc.perform(post("/v1/users")
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
    result.andReturn().getResponse().getHeader("Location").endsWith("/${id}")
  }

  // Delete user

  def "when DELETE request to /v1/users/{id} expect no content status on success"() {
    given:
    final String id = "8"

    when:
    def result = mvc.perform(delete("/v1/users/${id}"))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.deleteUser(id) >> true
    result.andExpect(status().isNoContent())
  }

  def "when DELETE request to /v1/users/{id} expect not found status on failure"() {
    given:
    final String id = "1"

    when:
    def result = mvc.perform(delete("/v1/users/${id}"))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.deleteUser(_) >> false
    result.andExpect(status().isNotFound())
  }

  // Get one user

  def "when GET request to /v1/users/{id} expect a user"() {
    given:
    final String id = "5"

    when:
    def result = mvc.perform(get("/v1/users/${id}"))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.getUser(id) >> Optional.of(user)
    result.andExpect(status().isOk())
    result.andExpect(content().json(objectMapper.writeValueAsString(user)))
  }

  def "when GET request to /v1/users/{id} expect not found when no user"() {
    given:
    final String id = "2"

    when:
    def result = mvc.perform(get("/v1/users/${id}"))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.getUser(id) >> Optional.empty()
    result.andExpect(status().isNotFound())
  }

  // Get all users

  def "when GET request to /v1/users expect a list of users"() {
    given:
    final User user2 = new User("Testgirl", "also-testing-java-template@dbowland.com")

    when:
    def result = mvc.perform(get("/v1/users"))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.getAllUsers() >> [user, user2]
    result.andExpect(status().isOk())
    result.andExpect(content().json(objectMapper.writeValueAsString([user, user2])))
  }

  // Update user

  def "when PUT request to /v1/users/{id} expect no content status on success"() {
    given:
    final String id = "7"

    when:
    def result = mvc.perform(put("/v1/users/${id}")
          .content(objectMapper.writeValueAsString(user))
          .contentType(MediaType.APPLICATION_JSON))
        .andDo(MockMvcResultHandlers.print())

    then:
    1 * exampleService.updateUser(_, _) >> { String passedId, User newUser ->
      assert objectMapper.writeValueAsString(newUser) == objectMapper.writeValueAsString(user)
      assert passedId == id
      return Optional.of(newUser)
    }
    result.andExpect(status().isNoContent())
  }

  def "when PUT request to /v1/users/{id} expect not found status on failure"() {
    given:
    final String id = "4"

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
