import React from 'react';
import TodoItem from './TodoItem';

const TodoItemList = ({ todos, onDelete, onDone, addEvent }) => {
  
  return (
    <div>
      {
        todos.map(todo => (
          <TodoItem 
            key={todo.id + todo.updateDate} 
            todo={todo} 
            onDelete={onDelete} 
            onDone={onDone} 
            addEvent={addEvent}
          />
        ))
      }
    </div>
  );
  
}

export default TodoItemList;