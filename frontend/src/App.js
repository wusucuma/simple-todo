import React, { useState, useEffect } from 'react';
import axios from "axios";
import TodoListTemplate from './TodoListTemplate';
import Form from './Form';
import TodoItemList from "./TodoItemList";
import Pagination from '@material-ui/lab/Pagination';
import moment from "moment";
import SearchForm from './SearchForm';

function App() {

  const [todoList, setTodoList] = useState([]);
  const [search, setSearch] = useState({
    subject: "",
    done: -1,
    createDate: moment().format("YYYY-MM-DDT00:00:00")
  });

  const [pageInfo, setPageInfo] = useState({
    count: 0,
    totalPage: 0,
    page: 0
  });

  const searchTodo = async () => {
    let param = {
      page: pageInfo.page,
      size: 5
    };

    if (search.subject !== "") {
      param = ({...param, subject: search.subject})
    }

    if (search.done !== -1) {
      param = ({...param, done: search.done})
    }

    if (search.createDate !== "") {
      param = ({...param, createDate: search.createDate})
    }

    const response = await axios.get("http://localhost:8080/todo", {params: param});
    setTodoList(response.data.content);
    setPageInfo({...pageInfo, totalPage: response.data.totalPages});
  };

  const onAddEvent = () => {
    searchTodo();
  };

  const deleteTodo = async (id) => {
    const response = await axios.delete(`http://localhost:8080/todo/${id}`);
    if (response.status !== 200) {
      alert(response.data.message);
    }
    searchTodo();
  };

  const doneStatusChangeTodo = async (id) => {
    const response = await axios.put(`http://localhost:8080/todo/done/${id}`);
    if (response.status !== 200) {
      alert(response.data.message);
    }
    searchTodo();
  };

  useEffect(() => {
    searchTodo();
  }, [pageInfo.page]);

  return (
    <TodoListTemplate 
      form={<Form addEvent={onAddEvent} />} 
      searchForm={<SearchForm search={search} setSearch={setSearch} onSearch={searchTodo} />}
      paging={
        <Pagination 
          count={pageInfo.totalPage} 
          onChange={(e, page) => {setPageInfo({...pageInfo, page: page - 1 })}}
          page={pageInfo.page + 1}
          variant="outlined" />
        } 
    >
      <TodoItemList todos={todoList} onDelete={deleteTodo} onDone={doneStatusChangeTodo} addEvent={onAddEvent} />
    </TodoListTemplate>
  )
}

export default App;
