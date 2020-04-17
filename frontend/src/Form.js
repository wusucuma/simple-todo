import React, { useState } from 'react';
import './Form.css';
import axios from "axios";

const todoInit = {
  subject: ""
}

const Form = ({ addEvent }) => {

  const [ todo, setTodo ]= useState({
    subject: ""
  });

  const saveTodo = async () => {
    if (todo.subject === "") {
      return;
    }

    const response = await axios.post("http://localhost:8080/todo", todo);
    if (response.status === 400) {
      return;
    }
    setTodo(todoInit);
    addEvent();
  }

  return (
    <div className="form">
      <input 
        value={todo.subject} 
        onChange={(e) => setTodo({ ...todo, subject: e.target.value })} 
        onKeyPress={saveTodo}
      />
      <div className="create-button" onClick={saveTodo}>
        추가
      </div>
    </div>
  );
};

export default Form;
