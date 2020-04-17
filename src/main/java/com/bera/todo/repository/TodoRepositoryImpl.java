package com.bera.todo.repository;

import com.bera.todo.dto.TodoSearchDto;
import com.bera.todo.entity.Todo;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

import static com.bera.todo.entity.QTodo.todo;

public class TodoRepositoryImpl extends QuerydslRepositorySupport implements TodoRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public TodoRepositoryImpl(JPAQueryFactory queryFactory) {
    super(Todo.class);
    this.queryFactory = queryFactory;
  }

  @Override
  public Page<Todo> findAllByPageableAndCondition(TodoSearchDto searchDto, Pageable pageable) {
    JPAQuery<Todo> jpaQuery =
        queryFactory
            .selectFrom(todo)
            .where(
                containsSubject(searchDto.getSubject()),
                eqDone(searchDto.getDone()),
                goeCreateDate(searchDto.getCreateDate()));
    jpaQuery = (JPAQuery<Todo>) super.getQuerydsl().applyPagination(pageable, jpaQuery);
    QueryResults<Todo> todoQueryResults = jpaQuery.fetchResults();
    return new PageImpl<>(todoQueryResults.getResults(), pageable, todoQueryResults.getTotal());
  }

  private BooleanExpression containsSubject(String subject) {
    if (StringUtils.isEmpty(subject)) {
      return null;
    }

    return todo.subject.contains(subject);
  }

  private BooleanExpression eqDone(Boolean done) {
    if (ObjectUtils.isEmpty(done)) {
      return null;
    }

    return todo.done.eq(done);
  }

  private BooleanExpression goeCreateDate(LocalDateTime createDate) {
    if (ObjectUtils.isEmpty(createDate)) {
      return null;
    }

    return todo.createDate.goe(createDate);
  }
}
