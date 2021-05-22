package com.dbowland.javatemplate.controller

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import com.dbowland.javatemplate.domain.User
import com.dbowland.javatemplate.service.ExampleService
import com.google.gson.Gson
import org.spockframework.spring.SpringSpy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExampleControllerTests extends Specification {
  private Gson gson = new Gson()

  @Autowired
  private MockMvc mvc;

  @SpringSpy
  private ExampleService exampleService

  private User user

  def setup() {
    user = new User("Testguy", "testing-java-template@dbowland.com")
  }

  // Get one user

  def "when GET request to /v1/users/{id} expect a user"() {
    given:
    final String id = "5"

    when:
    def results = mvc.perform(get("/v1/users/${id}"))

    then:
    1 * exampleService.getUser(id) >> user
    results.andExpect(status().isOk())
    results.andExpect(content().json(gson.toJson(user)))
  }

  // Get all users

  def "when GET request to /v1/users expect a list of users"() {
    given:
    final User user2 = new User("Testgirl", "also-testing-java-template@dbowland.com")

    when:
    def results = mvc.perform(get("/v1/users"))

    then:
    1 * exampleService.getAllUsers() >> [user, user2]
    results.andExpect(status().isOk())
    results.andExpect(content().json(gson.toJson([user, user2])))
  }

  // Create user

  def "when POST request to /v1/users expect created status on success"() {
    given:
    final String id = "3"

    when:
    def results = mvc.perform(post("/v1/users")
        .content(gson.toJson(user))
        .contentType(MediaType.APPLICATION_JSON))

    then:
    1 * exampleService.createUser(_) >> { User newUser ->
      assert gson.toJson(user) == gson.toJson(newUser)
      return id
    }
    results.andExpect(status().isCreated())
    results.andExpect(content().json(gson.toJson([ id: id ])))
  }

  def "when POST request to /v1/users expect bad request status on malformed request"() {
    when:
    def results = mvc.perform(post("/v1/users")
        .content("notJson")
        .contentType(MediaType.APPLICATION_JSON))

    then:
    results.andExpect(status().isBadRequest())
  }

  // Update user

  def "when PUT request to /v1/users expect no content status on success"() {
    when:
    def results = mvc.perform(put("/v1/users")
        .content(gson.toJson(user))
        .contentType(MediaType.APPLICATION_JSON))

    then:
    1 * exampleService.updateUser(_) >> { User newUser ->
      assert gson.toJson(user) == gson.toJson(newUser)
      return true
    }
    results.andExpect(status().isNoContent())
  }

  def "when PUT request to /v1/users expect conflict status on failure"() {
    when:
    def results = mvc.perform(put("/v1/users")
        .content(gson.toJson(user))
        .contentType(MediaType.APPLICATION_JSON))

    then:
    1 * exampleService.updateUser(_) >> false
    results.andExpect(status().isConflict())
  }

  def "when PUT request to /v1/users expect bad request status on malformed request"() {
    when:
    def results = mvc.perform(put("/v1/users")
        .content("notJson")
        .contentType(MediaType.APPLICATION_JSON))

    then:
    results.andExpect(status().isBadRequest())
  }

  // Delete user

  def "when DELETE request to /v1/users/{id} expect no content status on success"() {
    given:
    final String id = "8"

    when:
    def results = mvc.perform(delete("/v1/users/${id}")
        .content(gson.toJson(user))
        .contentType(MediaType.APPLICATION_JSON))

    then:
    1 * exampleService.deleteUser(id) >> true
    results.andExpect(status().isNoContent())
  }

  def "when DELETE request to /v1/users expect internal server error on failure"() {
    given:
    final String id = "1"

    when:
    def results = mvc.perform(delete("/v1/users/${id}")
        .content(gson.toJson(user))
        .contentType(MediaType.APPLICATION_JSON))

    then:
    1 * exampleService.deleteUser(_) >> false
    results.andExpect(status().isInternalServerError())
  }
}
