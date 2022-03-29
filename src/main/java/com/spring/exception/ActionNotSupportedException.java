package com.spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
public class ActionNotSupportedException extends RuntimeException {
  private static final long serialVersionUID = 1L;

  public ActionNotSupportedException(String message) {
    super(message);
  }
}
