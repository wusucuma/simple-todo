import React from "react";
import './TodoListTemplate.css';

const TodoListTemplate = ({ form, searchForm, children, paging }) => {

  return (
    <main className="todo-list-template">
      <div className="title">
        오늘 할 일
      </div>
      <section className="form-wrapper">
        {form}
      </section>
      <section className="form-wrapper">
        {searchForm}
      </section>
      <section className="todos-wrapper">
        { children }
      </section>
      <section className="todos-wrapper">
        { paging }
      </section>
    </main>
  )
};

export default TodoListTemplate;
