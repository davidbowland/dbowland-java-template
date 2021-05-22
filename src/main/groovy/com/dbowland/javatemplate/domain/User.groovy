package com.dbowland.javatemplate.domain

import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
class User {

  User() { }

  User(final String name, final String email) {
    this.name = name
    this.email = email
  }

  @Id
  String id

  @NotBlank(message = "Name is mandatory")
  String name

  @NotBlank(message = "Email is mandatory")
  String email
}
