package com.dbowland.javatemplate.service

import static net.logstash.logback.argument.StructuredArguments.keyValue

import com.dbowland.javatemplate.domain.User
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class ExampleService {

  User getUser(final String id) {
    log.info "Getting user", keyValue("id", id)
    return new User("David", "e@ma.il")
  }

  User[] getAllUsers() {
    log.info "Gettin' all users!"
    return [
      new User("Alice", "a@li.ce"),
      new User("Bob", "b@ob.bob")
    ]
  }

  String createUser(final User user) {
    final String id = "8675309"
    log.info "Creating user", keyValue("id", id), keyValue("name", user.name)
    return id
  }

  boolean updateUser(final User user) {
    log.info "Updating user", keyValue("id", user.id)
    return isHealthy()
  }

  boolean deleteUser(final String id) {
    log.info "Deleting user", keyValue("id", id)
    return isHealthy()
  }

  boolean isHealthy() {
    return true
  }
}
