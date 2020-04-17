package com.bera.todo.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@DynamicUpdate
@Entity
public class Todo {

  @Id
  @Column(name = "todo_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String subject;

  private Boolean done;

  @JoinTable(
      name = "sub_todo",
      joinColumns = {@JoinColumn(name = "todo_id")},
      inverseJoinColumns = {@JoinColumn(name = "sub_todo_id")})
  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  private Set<Todo> subTodo;

  @OneToOne(cascade = CascadeType.DETACH)
  private Todo mainTodo;

  @CreationTimestamp private LocalDateTime createDate;

  @UpdateTimestamp private LocalDateTime updateDate;

  @Builder
  public Todo(String subject) {
    this.subject = subject;
    this.done = false;
    this.subTodo = new HashSet<>();
  }

  public void addSubTodo(Set<Todo> todos) {
    Set<Todo> result =
        todos.stream()
            .filter(todo -> !todo.getId().equals(this.id))
            .filter(todo -> todo.subTodo.size() == 0)
            .peek(todo -> todo.mainTodo = this)
            .collect(Collectors.toSet());

    this.subTodo.clear();
    this.subTodo.addAll(result);
  }

  public void changeDoneStatus() {
    if (this.done) {
      this.done = false;
      if (this.mainTodo != null) {
        this.mainTodo.done = false;
      }
    } else {
      this.todoDone();
    }
  }

  private void todoDone() {
    long count = subTodo.stream().filter(todo -> !todo.done).count();

    if (count == 0) {
      this.done = true;
    }
  }

  public void clearTodo() {
    this.subTodo.forEach(subTodo -> subTodo.mainTodo = null);
    this.subTodo.clear();
    if (mainTodo != null) {
      mainTodo.clearSubTodo(this);
    }
  }

  private void clearSubTodo(Todo todo) {
    this.subTodo.remove(todo);
  }
}
