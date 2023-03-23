import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import OutlinedInput from '@mui/material/OutlinedInput';
import SearchIcon from '@mui/icons-material/Search';
import { useRef } from 'react';
import styled from 'styled-components';

const SearchBar = () => {
  const searchBarRef = useRef<any>();

  const SearchHandler = (event: any) => {
    event.preventDefault();
    console.log(searchBarRef.current.value);
    searchBarRef.current.value = '';
  };

  const StyledForm = styled.form`
    margin: 5% auto;
    width: 80%;
    text-align: center;
  `;

  return (
    <>
      <StyledForm onSubmit={SearchHandler}>
        <FormControl fullWidth sx={{ m: 1 }}>
          <InputLabel htmlFor="outlined-adornment-amount">search</InputLabel>
          <OutlinedInput
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
      </StyledForm>
    </>
  );
};

export default SearchBar;
