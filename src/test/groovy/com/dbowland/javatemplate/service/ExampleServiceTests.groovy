package com.dbowland.javatemplate.service

import com.dbowland.javatemplate.domain.User
import org.spockframework.spring.SpringSpy
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ExampleServiceTests extends Specification {

  @SpringSpy
  private ExampleService exampleService

  private User user

  def setup() {
    user = new User("Evie", "e@ves.drop")
  }

  def "when getUser expect a new User"() {
    given:
    final String id = "4"

    when:
    User result = exampleService.getUser(id)

    then:
    result != null
  }

  def "when getAllUsers expect a list of new Users"() {
    when:
    List<User> result = exampleService.getAllUsers()

    then:
    result != null
  }

  def "when createUser then expect id back"() {
    when:
    String result = exampleService.createUser(user)

    then:
    result
  }

  def "when updateUser then expect isHealthy()"() {
    when:
    boolean result = exampleService.updateUser(user)

    then:
    1 * exampleService.isHealthy() >> expected
    result == expected

    where:
    expected << [true, false]
  }

  def "when deleteUser then expect isHealthy()"() {
    given:
    final String id = "2"

    when:
    boolean result = exampleService.deleteUser(id)

    then:
    1 * exampleService.isHealthy() >> expected
    result == expected

    where:
    expected << [true, false]
  }

  def "when isHealthy then expect true"() {
    when:
    boolean result = exampleService.isHealthy()

    then:
    result
  }

}
