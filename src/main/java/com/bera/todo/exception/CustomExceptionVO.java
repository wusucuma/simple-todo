package com.bera.todo.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CustomExceptionVO {

  private String message;

  public CustomExceptionVO(String message) {
    this.message = message;
  }
}
