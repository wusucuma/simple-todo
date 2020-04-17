package com.bera.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
public class TodoDto {

  private Long id;

  private String subject;

  private Boolean done;

  private Set<TodoDto> subTodo;

  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss",
      timezone = "Asia/Seoul")
  private LocalDateTime createDate;

  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "yyyy-MM-dd'T'HH:mm:ss",
      timezone = "Asia/Seoul")
  private LocalDateTime updateDate;

  @Builder
  public TodoDto(
      Long id,
      String subject,
      Boolean done,
      Set<TodoDto> subTodo,
      LocalDateTime createDate,
      LocalDateTime updateDate) {
    this.id = id;
    this.subject = subject;
    this.done = done;
    this.subTodo = subTodo;
    this.createDate = createDate;
    this.updateDate = updateDate;
  }
}
