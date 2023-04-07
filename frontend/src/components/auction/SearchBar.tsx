import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import OutlinedInput from '@mui/material/OutlinedInput';
import SearchIcon from '@mui/icons-material/Search';
import { useRef, useState } from 'react';
import styled from 'styled-components';
import MenuItem from '@mui/material/MenuItem';
import Select, { SelectChangeEvent } from '@mui/material/Select';
import Grid from '@mui/material/Grid';

const SearchBar:React.FC<{ setKeyword: (data: string) => void, setSize:(data:number)=>void, size:number }> = (props) => {
  const { setKeyword, setSize, size } = props;

  const handleChange = (event: SelectChangeEvent) => {
    setSize(parseInt(event.target.value));
  };

  const searchBarRef = useRef<any>();

  const handleKeyDown = (event: any) => {
    if (event.key === 'Enter') {
      event.preventDefault(); // 폼 제출 방지
      let keyword = searchBarRef.current.value;
      setKeyword(keyword);
    }
  }


 

  return (
    <>
      <StyledForm>
        <Grid container spacing={2}>
          <Grid item xs={9}>
            <FormControl fullWidth sx={{ m: 1 }}>
              <InputLabel htmlFor="outlined-adornment-amount">search</InputLabel>
              <OutlinedInput
              onKeyDown={handleKeyDown}
                inputRef={searchBarRef}
                id="outlined-adornment-amount"
                endAdornment={
                  <InputAdornment position="end">
                    <SearchIcon></SearchIcon>
                  </InputAdornment>
                }
                label="Amount"
              />
            </FormControl>
          </Grid>
          <Grid item xs={2} sx={{ m:1 }}>
            <FormControl fullWidth>
              <InputLabel id="demo-simple-select-label">count</InputLabel>
              <Select
                labelId="demo-simple-select-label"
                id="demo-simple-select"
                value={size.toString()}
                label="검색수"
                onChange={handleChange}
              >
                <MenuItem value={12}>12</MenuItem>
                <MenuItem value={24}>24</MenuItem>
                <MenuItem value={36}>36</MenuItem>
              </Select>
            </FormControl>
          </Grid>
        </Grid>
      </StyledForm>
    </>
  );
};


export default SearchBar;


const StyledForm = styled.form`
margin: 5% auto;
width: 80%;
text-align: center;
`;
