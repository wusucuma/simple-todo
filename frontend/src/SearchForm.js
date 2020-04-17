import 'date-fns';
import React from 'react';
import './Form.css';
import { InputLabel, Select, MenuItem, FormControl, Input, Grid, Button } from '@material-ui/core';
import DateFnsUtils from '@date-io/date-fns';
import {
  MuiPickersUtilsProvider,
  KeyboardDatePicker
} from '@material-ui/pickers';
import moment from "moment";

const SearchForm = ({ search, setSearch, onSearch }) => {

  return (
    <>
      <div>
        <FormControl style={{width: "50%"}}>
          <InputLabel>할일</InputLabel>
          <Input
            value={search.subject}
            onChange={(e) => setSearch({ ...search, subject: e.target.value })} 
          />
        </FormControl>
        <FormControl style={{width: "50%"}}>
          <InputLabel>진행여부</InputLabel>
          <Select
            value={search.done}
            onChange={(e) => setSearch({ ...search, done: e.target.value })} 
          >
            <MenuItem value={-1}>전체</MenuItem>
            <MenuItem value={false}>진행중</MenuItem>
            <MenuItem value={true}>완료</MenuItem>
          </Select>
        </FormControl>
      </div>
      <div className="form">
        <MuiPickersUtilsProvider utils={DateFnsUtils}>
          <Grid container justify="space-around">
            <KeyboardDatePicker
              disableToolbar
              variant="inline"
              format="yyyy-MM-dd"
              margin="normal"
              label="생성일"
              autoOk={true}
              value={search.createDate}
              onChange={(date) => setSearch({...search, createDate : moment(date).format("YYYY-MM-DDT00:00:00")})}
              KeyboardButtonProps={{
                'aria-label': 'change date',
              }}
            />
            <Button 
              variant="contained" 
              color="primary" 
              style={{marginTop: "20px", marginBottom: "20px"}}
              onClick={onSearch}
            >
              검색
            </Button>
        </Grid>
      </MuiPickersUtilsProvider> 

        </div>
    </>
  );
};

export default SearchForm;
