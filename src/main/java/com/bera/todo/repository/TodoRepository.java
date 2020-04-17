package com.bera.todo.repository;

import com.bera.todo.entity.Todo;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface TodoRepository extends JpaRepository<Todo, Long>, TodoRepositoryCustom {

  @EntityGraph(attributePaths = "subTodo")
//  @Query(value = "select t from Todo t where t.id = :id")
  public Optional<Todo> findWithSubTodoById(Long id);

  @EntityGraph(attributePaths = "subTodo")
  @Query(
      value =
          "select t from Todo t where (t.mainTodo is null or t.mainTodo.id = :mainId) and t.id in :todoIds")
  public Set<Todo> findAllByIdAndMainTodo(Long mainId, Set<Long> todoIds);
}
