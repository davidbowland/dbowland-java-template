package com.dbowland.javatemplate.service

import com.dbowland.javatemplate.domain.User
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class ExampleService {

  User getUser(final String id) {
    log.info "Getting user with id ${id}"
    return new User("David", "e@ma.il")
  }

  User[] getAllUsers() {
    log.info "Gettin' all users!"
    return [new User("Alice", "a@li.ce"), new User("Bob", "b@ob.bob")]
  }

  String createUser(final User user) {
    final String id = "8675309"
    log.info "Creating user ${user.name}"
    return id
  }

  boolean updateUser(final User user) {
    log.info "Updating user ${user.name}"
    return isHealthy()
  }

  boolean deleteUser(final String id) {
    log.info "Deleting user with id ${id}"
    return isHealthy()
  }

  boolean isHealthy() {
    return true
  }

}
