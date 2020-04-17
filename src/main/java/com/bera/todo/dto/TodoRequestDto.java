package com.bera.todo.dto;

import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class TodoRequestDto {

  @Getter
  public static class TodoCreateDto {
    @NotBlank private String subject;
  }

  @Getter
  public static class SubTodoCreateDto {
    private Set<Long> subTodoId;
  }
}
