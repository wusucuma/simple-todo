package com.bera.todo.mapper;

import com.bera.todo.dto.SubTodoCreateDto;
import com.bera.todo.dto.TodoCreateDto;
import com.bera.todo.dto.TodoDto;
import com.bera.todo.dto.TodoRequestDto;
import com.bera.todo.entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TodoMapper {

  public Todo toEntity(TodoCreateDto todoCreateDto);

  public TodoDto toDto(Todo todo);

  public TodoCreateDto toCreateDto(TodoRequestDto.TodoCreateDto todoCreateDto);

  @Mapping(target = "id", source = "todoId")
  public SubTodoCreateDto toSubCreateDto(
      Long todoId, TodoRequestDto.SubTodoCreateDto subTodoCreateDto);

  default Set<TodoDto> toDtoSet(Set<Todo> todos) {
    return todos.stream()
        .map(
            todo ->
                TodoDto.builder()
                    .id(todo.getId())
                    .done(todo.getDone())
                    .subject(todo.getSubject())
                    .createDate(todo.getCreateDate())
                    .updateDate(todo.getUpdateDate())
                    .build())
        .collect(Collectors.toSet());
  }

  default TodoDto toIgnoreSubTodoDto(Todo todo) {
    if (todo == null) {
      return null;
    } else {
      TodoDto.TodoDtoBuilder todoDto = TodoDto.builder();
      todoDto.id(todo.getId());
      todoDto.subject(todo.getSubject());
      todoDto.done(todo.getDone());
      todoDto.createDate(todo.getCreateDate());
      todoDto.updateDate(todo.getUpdateDate());
      return todoDto.build();
    }
  }
}
