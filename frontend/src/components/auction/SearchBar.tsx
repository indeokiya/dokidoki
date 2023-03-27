import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import OutlinedInput from '@mui/material/OutlinedInput';
import SearchIcon from '@mui/icons-material/Search';
import { useRef } from 'react';
import styled from 'styled-components';

const SearchBar: React.FC<{ setKeyword: (data: string) => void }> = (props) => {
  const { setKeyword } = props;

  const searchBarRef = useRef<any>();

  const SearchHandler = (event: any) => {
    event.preventDefault(); // 기본동작 중지
    let keyword = searchBarRef.current.value;
    setKeyword(keyword);
  };



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

const StyledForm = styled.form`
  margin: 5% auto;
  width: 80%;
  text-align: center;
`;
