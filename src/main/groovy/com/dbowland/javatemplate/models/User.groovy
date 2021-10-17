package com.dbowland.javatemplate.models

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
class User {

  User() {
    this.id = Math.random() * 100
  }

  User(final String name, final String email) {
    this.id = Math.random() * 100
    this.name = name
    this.email = email
  }

  @Id
  Integer id

  @NotBlank(message = 'Name is mandatory')
  String name

  @NotBlank(message = 'Email is mandatory')
  String email
}
