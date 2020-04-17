package com.bera.todo.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
public class SubTodoCreateDto {

  private Long id;

  private Set<Long> subTodoId;

  @Builder
  public SubTodoCreateDto(Long id, Set<Long> subTodoId) {
    this.id = id;
    this.subTodoId = subTodoId;
  }
}
