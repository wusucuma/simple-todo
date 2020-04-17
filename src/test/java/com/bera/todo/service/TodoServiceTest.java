package com.bera.todo.service;

import com.bera.todo.dto.SubTodoCreateDto;
import com.bera.todo.dto.TodoCreateDto;
import com.bera.todo.dto.TodoDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TodoServiceTest {

  @Autowired private TodoService todoService;

  @Test
  @Transactional
  public void todo_저장() {
    TodoCreateDto todoCreateDto = TodoCreateDto.builder().subject("할일1").build();
    assertDoesNotThrow(() -> todoService.setTodo(todoCreateDto));
  }

  @Test
  @Transactional
  public void todo_리스트_가져오기() {
    TodoCreateDto todoCreateDto1 = TodoCreateDto.builder().subject("할일1").build();
    TodoCreateDto todoCreateDto2 = TodoCreateDto.builder().subject("할일2").build();

    todoService.setTodo(todoCreateDto1);
    todoService.setTodo(todoCreateDto2);
    assertEquals(2, todoService.getTodoList(PageRequest.of(0, 20)).getTotalElements());
  }

  @Test
  @Transactional
  public void subtodo_없는_todo_완료상태변경() {
    TodoCreateDto todoCreateDto = TodoCreateDto.builder().subject("할일1").build();
    todoService.setTodo(todoCreateDto);

    TodoDto todoDto = todoService.changeDoneStatus((long) 1);
    assertEquals(true, todoDto.getDone());
  }

  @Test
  @Transactional
  public void subtodo_생성() {
    TodoCreateDto todoCreateDto1 = TodoCreateDto.builder().subject("할일1").build();
    TodoCreateDto todoCreateDto2 = TodoCreateDto.builder().subject("할일2").build();
    TodoCreateDto todoCreateDto3 = TodoCreateDto.builder().subject("할일3").build();

    todoService.setTodo(todoCreateDto1);
    todoService.setTodo(todoCreateDto2);
    todoService.setTodo(todoCreateDto3);

    Set<Long> subTodoIds = new HashSet<>();
    subTodoIds.add((long) 2);
    subTodoIds.add((long) 3);

    SubTodoCreateDto subTodoCreateDto = SubTodoCreateDto.builder()
            .id((long) 1)
            .subTodoId(subTodoIds)
            .build();

    TodoDto todoDto = todoService.setSubTodo(subTodoCreateDto);
    assertEquals(2, todoDto.getSubTodo().size());
  }

  @Test
  @Transactional
  public void subtodo_자신추가_생성() {
    TodoCreateDto todoCreateDto1 = TodoCreateDto.builder().subject("할일1").build();
    TodoCreateDto todoCreateDto2 = TodoCreateDto.builder().subject("할일2").build();
    TodoCreateDto todoCreateDto3 = TodoCreateDto.builder().subject("할일3").build();

    todoService.setTodo(todoCreateDto1);
    todoService.setTodo(todoCreateDto2);
    todoService.setTodo(todoCreateDto3);

    Set<Long> subTodoIds = new HashSet<>();
    subTodoIds.add((long) 1);
    subTodoIds.add((long) 2);
    subTodoIds.add((long) 3);

    SubTodoCreateDto subTodoCreateDto = SubTodoCreateDto.builder()
            .id((long) 1)
            .subTodoId(subTodoIds)
            .build();

    TodoDto todoDto = todoService.setSubTodo(subTodoCreateDto);
    assertEquals(2, todoDto.getSubTodo().size());
  }

  @Test
  @Transactional
  public void subtodo_이미추가한_subtodo_추가() {
    TodoCreateDto todoCreateDto1 = TodoCreateDto.builder().subject("할일1").build();
    TodoCreateDto todoCreateDto2 = TodoCreateDto.builder().subject("할일2").build();
    TodoCreateDto todoCreateDto3 = TodoCreateDto.builder().subject("할일3").build();

    todoService.setTodo(todoCreateDto1);
    todoService.setTodo(todoCreateDto2);
    todoService.setTodo(todoCreateDto3);

    Set<Long> subTodoIds = new HashSet<>();
    subTodoIds.add((long) 2);

    SubTodoCreateDto subTodoCreateDto = SubTodoCreateDto.builder()
            .id((long) 1)
            .subTodoId(subTodoIds)
            .build();

    TodoDto todoDto = todoService.setSubTodo(subTodoCreateDto);
    assertEquals(1, todoDto.getSubTodo().size());

    subTodoCreateDto = SubTodoCreateDto.builder()
            .id((long) 3)
            .subTodoId(subTodoIds)
            .build();
    todoDto = todoService.setSubTodo(subTodoCreateDto);
    assertEquals(0, todoDto.getSubTodo().size());
  }
}
