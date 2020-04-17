import React, {useState, useEffect} from 'react';
import './TodoItem.css';
import TagsInput from 'react-tagsinput-2'
import 'react-tagsinput-2/react-tagsinput.css'
import axios from "axios";

const TodoItem = ({ todo, onDelete, onDone, addEvent }) => {
  const [subTodo, setSubTodo] = useState([]);

  const setSub = (subTodos) => {
    const reg = /^\d+$/;

    let result = [];
    for (let i = 0; i < subTodos.length; i++) {
      const subTodo = subTodos[i];
      if (Number.isInteger(subTodo) && subTodo !== todo.id) {
        result.push(subTodo);
      } else if (subTodo.match(reg)) {
        const id = Number.parseInt(subTodo);
        if (id !== todo.id) {
          result.push(Number.parseInt(subTodo));
        }
      }
    }
    saveSubTodo(todo.id, result);
  };

  const saveSubTodo = async (id, subTodo) => {
    const response = await axios.put(`http://localhost:8080/todo/sub/${id}`, {subTodoId: subTodo});
    if (response.status !== 200) {
      alert(response.data.message);
    }
    addEvent();
    setSubTodo(convertSubTodoId(response.data.subTodo));
  };

  const convertSubTodoId = (subTodo) => {
    return subTodo.map(todo => todo.id)
  };

  useEffect(() => {
    setSubTodo(convertSubTodoId(todo.subTodo));
  }, []);

  return (
    <div className="todo-item" onClick={(e) => onDone(todo.id)}  >
      <div className="remove" onClick={(e) => {
        e.stopPropagation();
        onDelete(todo.id);
        }}>&times;
      </div>
      <div className={`todo-text ${todo.done && 'checked'}`}>
        <div>
          <span>ID</span>
          <span style={{ marginLeft: "5px" }}>
            <small>{todo.id}</small>
          </span>
          <span style={{ marginLeft: "20px" }}>내용</span>
          <span style={{ marginLeft: "5px" }}>
            <small>{todo.subject}</small>
          </span>
          <span style={{ marginLeft: "20px" }}>작성일</span>
          <span style={{ marginLeft: "5px" }}>
            <small>{todo.createDate}</small>
          </span>
          <span style={{ marginLeft: "20px" }}>수정일</span>
          <span style={{ marginLeft: "5px" }}>
            <small>{todo.updateDate}</small>
          </span>
        </div>
        <div onClick={e => e.stopPropagation()}>
          <TagsInput 
            value={subTodo} 
            onChange={setSub} 
            inputProps={{placeholder: "sub todo id"}} 
          />
        </div>
      </div>

      {
        todo.done && (<div className="check-mark">✓</div>)
      }
    </div>
  );
}

export default TodoItem;
