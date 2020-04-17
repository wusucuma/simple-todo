package com.bera.todo.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class TodoCreateDto {

  @NotBlank
  private String subject;

  @Builder
  public TodoCreateDto(@NotBlank String subject) {
    this.subject = subject;
  }
}
