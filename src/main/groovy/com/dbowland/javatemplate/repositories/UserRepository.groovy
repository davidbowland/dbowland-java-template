package com.dbowland.javatemplate.repositories

import com.dbowland.javatemplate.models.User
import org.springframework.stereotype.Repository

// This would normally be a JPA repository
@Repository
class UserRepository {
  private Integer maxKey
  private Map<Integer, User> users

  UserRepository() {
    maxKey = 0
    users = new HashMap()
  }

  void deleteById(final Integer id) {
    users.remove(id)
  }

  List<User> findAll() {
    return users.values().collect()
  }

  Optional <User> getById(final Integer id) {
    return users.values().stream()
        .filter({ User user -> user.id == id })
        .findFirst()
  }

  User save(User user) {
    if (user.id == null) {
      user.id = ++maxKey
    }
    users[user.id] = user
    return user
  }
}
