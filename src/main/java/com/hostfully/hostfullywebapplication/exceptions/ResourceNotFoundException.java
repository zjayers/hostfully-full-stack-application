package com.hostfully.hostfullywebapplication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The ResourceNotFoundException is an exception that indicates a resource was not found.
 * It is typically used to handle HTTP 404 - Not Found responses.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

  /**
   * Constructs a ResourceNotFoundException with the specified error message.
   *
   * @param message The error message.
   */
  public ResourceNotFoundException(String message) {
    super(message);
  }
}
