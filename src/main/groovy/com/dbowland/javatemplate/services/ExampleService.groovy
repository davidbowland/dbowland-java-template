package com.dbowland.javatemplate.services

import com.dbowland.javatemplate.models.User
import com.dbowland.javatemplate.repositories.UserRepository
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.fge.jsonpatch.JsonPatch
import groovy.util.logging.Slf4j
import org.springframework.stereotype.Service
import org.springframework.util.Assert

import static net.logstash.logback.argument.StructuredArguments.keyValue

@Slf4j
@Service
class ExampleService {
  private static final ObjectMapper objectMapper = new ObjectMapper()

  private UserRepository userRepository

  ExampleService(final UserRepository userRepository) {
    Assert.notNull(userRepository, 'userRepository must not be null')
    this.userRepository = userRepository
  }

  User createUser(final User user) {
    log.info 'Creating user', keyValue('id', user.id), keyValue('name', user.name)
    return user
  }

  Boolean deleteUser(final Integer id) {
    log.info 'Deleting user', keyValue('id', id)
    return true
  }

  User[] getAllUsers() {
    log.info "Gettin' all users!"
    return [
      new User('Alice', 'a@li.ce'),
      new User('Bob', 'b@ob.bob')
    ]
  }

  Optional<User> getUser(final Integer id) {
    log.info 'Getting user', keyValue('id', id)
    return Optional.of(new User('David', 'e@ma.il'))
  }

  Optional<User> patchUser(final Integer id, final JsonPatch jsonPatch) {
    log.info 'Patching user', keyValue('id', id), keyValue('jsonPatch', jsonPatch)
    final Optional<User> modifiedUser = getUser(id).map({ User user ->
      final JsonNode jsonNode = jsonPatch.apply(objectMapper.convertValue(user, JsonNode.class))
      return objectMapper.treeToValue(jsonNode, User.class)
    })
    return modifiedUser
  }

  Boolean updateUser(final Integer id, final User user) {
    log.info 'Updating user', keyValue('id', id)
    return true
  }
}
