package com.bera.todo.service;

import com.bera.todo.dto.SubTodoCreateDto;
import com.bera.todo.dto.TodoCreateDto;
import com.bera.todo.dto.TodoDto;
import com.bera.todo.dto.TodoSearchDto;
import com.bera.todo.entity.Todo;
import com.bera.todo.mapper.TodoMapper;
import com.bera.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@RequiredArgsConstructor
@Service
public class TodoService {

  private final TodoRepository todoRepository;
  private final TodoMapper todoMapper;

  public void setTodo(TodoCreateDto todoCreateDto) {
    todoRepository.save(todoMapper.toEntity(todoCreateDto));
  }

  public Page<TodoDto> getTodoList(TodoSearchDto searchDto, Pageable pageable) {
    return todoRepository.findAllByPageableAndCondition(searchDto, pageable).map(todoMapper::toDto);
  }

  @Transactional
  public TodoDto setSubTodo(SubTodoCreateDto subTodoCreateDto) {
    Todo todo =
        todoRepository
            .findWithSubTodoById(subTodoCreateDto.getId())
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 todo 입니다."));

    Set<Todo> subTodo = todoRepository.findAllByIdAndMainTodo(subTodoCreateDto.getId(), subTodoCreateDto.getSubTodoId());
    todo.addSubTodo(subTodo);
    return todoMapper.toDto(todo);
  }

  @Transactional
  public TodoDto changeDoneStatus(Long id) {
    Todo todo =
        todoRepository
            .findWithSubTodoById(id)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 todo 입니다."));

    todo.changeDoneStatus();
    return todoMapper.toIgnoreSubTodoDto(todo);
  }

  @Transactional
  public void deleteTodo(Long id) {
    Todo todo =
        todoRepository
            .findWithSubTodoById(id)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 todo 입니다."));

    todo.clearTodo();
    todoRepository.delete(todo);
  }
}
