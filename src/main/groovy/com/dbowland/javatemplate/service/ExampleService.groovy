package com.dbowland.javatemplate.service

import static net.logstash.logback.argument.StructuredArguments.keyValue

import com.dbowland.javatemplate.domain.User
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service

@Slf4j
@Service
class ExampleService {

  User createUser(final User user) {
    final String id = "8675309"
    log.info "Creating user", keyValue("id", id), keyValue("name", user.name)
    user.id = id
    return user
  }

  Boolean deleteUser(final String id) {
    log.info "Deleting user", keyValue("id", id)
    return isHealthy()
  }

  User[] getAllUsers() {
    log.info "Gettin' all users!"
    return [
        new User("Alice", "a@li.ce"),
        new User("Bob", "b@ob.bob")
    ]
  }

  Optional<User> getUser(final String id) {
    log.info "Getting user", keyValue("id", id)
    return Optional.of(new User("David", "e@ma.il"))
  }

  Boolean updateUser(final String id, final User user) {
    log.info "Updating user", keyValue("id", id)
    return isHealthy()
  }

  Boolean isHealthy() {
    return true
  }
}
