package com.dbowland.javatemplate.controller

import com.dbowland.javatemplate.service.ExampleService
import org.spockframework.spring.SpringSpy
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StatusControllerTests extends Specification {
  @Autowired
  private MockMvc mvc;

  @SpringSpy
  private ExampleService exampleService

  def "when GET request to /status/health expect 200 (OK)"() {
    when:
    def results = mvc.perform(get("/status/health"))

    then:
    results.andExpect(status().isOk())
  }

  def "when GET request to /status/liveness expect 200 (OK)"() {
    when:
    def results = mvc.perform(get("/status/liveness"))

    then:
    1 * exampleService.isHealthy() >> true
    results.andExpect(status().isOk())
  }

  def "when GET request to /status/liveness and status is unhealthy expect 5XX response (Server Error)"() {
    when:
    def results = mvc.perform(get("/status/liveness"))

    then:
    1 * exampleService.isHealthy() >> false
    results.andExpect(status().is5xxServerError())
  }

}
