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
    log.info 'Creating user', keyValue('name', user.name)
    return userRepository.save(user)
  }

  void deleteUser(final Integer id) {
    log.info 'Deleting user', keyValue('id', id)
    userRepository.deleteById(id)
  }

  List<User> getAllUsers() {
    log.info 'Getting all users'
    return userRepository.findAll()
  }

  Optional<User> getUser(final Integer id) {
    log.info 'Getting user', keyValue('id', id)
    return userRepository.getById(id)
  }

  Optional<User> patchUser(final Integer id, final JsonPatch jsonPatch) {
    log.info 'Patching user', keyValue('id', id), keyValue('jsonPatch', jsonPatch)
    return getUser(id).map({ User user ->
      final JsonNode jsonNode = jsonPatch.apply(objectMapper.convertValue(user, JsonNode.class))
      final User modifiedUser = objectMapper.treeToValue(jsonNode, User.class)
      return userRepository.save(modifiedUser)
    })
  }

  Optional<User> updateUser(final Integer id, final User user) {
    log.info 'Updating user', keyValue('id', id)
    return getUser(id)
        .map({ User modifiedUser ->
          modifiedUser.setName(user.getName())
          modifiedUser.setEmail(user.getEmail())
          return userRepository.save(modifiedUser)
        })
  }
}
