package com.dbowland.javatemplate.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.github.fge.jsonpatch.JsonPatchException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.ModelAndView
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionController extends ResponseEntityExceptionHandler {

  @ExceptionHandler([
    JsonPatchException,
    JsonProcessingException
  ])
  ResponseEntity<ModelAndView> patchErrorHandler(Exception exception) {

    ModelAndView modelAndView = new ModelAndView()
    modelAndView.setViewName('error')
    modelAndView.addObject('message', exception.getMessage())
    return ResponseEntity.badRequest().body(modelAndView)
  }
}
