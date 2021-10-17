package com.dbowland.javatemplate

import com.dbowland.javatemplate.controllers.ExampleController
import com.dbowland.javatemplate.services.ExampleService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JavaTemplateApplicationTests extends Specification {

  @Autowired(required = false)
  private ExampleController exampleController

  @Autowired(required = false)
  private ExampleService exampleService

  def "when context is loaded expect all beans created"() {
    expect:
    exampleController
    exampleService
  }
}
