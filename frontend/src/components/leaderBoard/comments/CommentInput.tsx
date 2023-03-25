import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import FormControl from '@mui/material/FormControl';
import Input from '@mui/material/Input';
import CommentIcon from '@mui/icons-material/Comment';
import SendIcon from '@mui/icons-material/Send';

const CommentInput = () => {
  return (
    <FormControl fullWidth sx={{ mb: 4 }} variant="standard">
      <InputLabel htmlFor="standard-adornment-amount">댓글 작성</InputLabel>
      <Input
        id="standard-adornment-amount"
        placeholder="댓글을 작성하세요"
        endAdornment={
          <InputAdornment position="start">
            <SendIcon />
          </InputAdornment>
        }
      />
    </FormControl>
  );
};

export default CommentInput;
