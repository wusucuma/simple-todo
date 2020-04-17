package com.bera.todo.repository;

import com.bera.todo.dto.TodoSearchDto;
import com.bera.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TodoRepositoryCustom {

    public Page<Todo> findAllByPageableAndCondition(TodoSearchDto searchDto, Pageable pageable);
}
