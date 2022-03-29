package com.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class StarshipNotFoundException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public StarshipNotFoundException(String message) {
    super(message);
  }
}
