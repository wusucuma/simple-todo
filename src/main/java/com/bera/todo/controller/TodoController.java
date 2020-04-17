package com.bera.todo.controller;

import com.bera.todo.dto.TodoDto;
import com.bera.todo.dto.TodoRequestDto;
import com.bera.todo.dto.TodoSearchDto;
import com.bera.todo.mapper.TodoMapper;
import com.bera.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/todo")
@RestController
public class TodoController {

  private final TodoService todoService;
  private final TodoMapper todoMapper;

  @PostMapping("")
  public ResponseEntity<?> todoSave(
      @Valid @RequestBody TodoRequestDto.TodoCreateDto todoCreateDto) {
    todoService.setTodo(todoMapper.toCreateDto(todoCreateDto));
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @GetMapping("")
  public ResponseEntity<Page<TodoDto>> todoList(
      TodoSearchDto searchDto,
      @PageableDefault(
              sort = {"id"},
              direction = Sort.Direction.DESC)
          Pageable pageable) {
    return ResponseEntity.ok(todoService.getTodoList(searchDto, pageable));
  }

  @PutMapping("/sub/{id}")
  public ResponseEntity<TodoDto> subTodoSave(
      @PathVariable(name = "id") Long id,
      @Valid @RequestBody TodoRequestDto.SubTodoCreateDto subTodoCreateDto) {
    return ResponseEntity.ok(
        todoService.setSubTodo(todoMapper.toSubCreateDto(id, subTodoCreateDto)));
  }

  @PutMapping("/done/{id}")
  public ResponseEntity<TodoDto> todoDoneChange(@PathVariable(name = "id") Long id) {
    return ResponseEntity.ok(todoService.changeDoneStatus(id));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> todoDelete(@PathVariable(name = "id") Long id) {
    todoService.deleteTodo(id);
    return ResponseEntity.ok().build();
  }
}
