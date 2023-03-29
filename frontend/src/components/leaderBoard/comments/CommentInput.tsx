import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import FormControl from '@mui/material/FormControl';
import Input from '@mui/material/Input';
import SendIcon from '@mui/icons-material/Send';
import { useState } from "react";
import { auctionAPI } from 'src/api/axios';


const CommentInput: React.FC<{auction_id: string, parent_id: number | null}> = (props) => {
  const { auction_id, parent_id } = props

  // 댓글 State
  const [content, setContent] = useState("")

  const createComment = () => {
    auctionAPI.post(`${auction_id}/comments`, {
      content: content,
      parent_id: parent_id
    }).then(res => {
      console.log(res)
    }).catch(err => {
      console.log("!", err)
    });
  }

  return (
    <FormControl fullWidth sx={{ mb: 4 }} variant="standard">
      <InputLabel htmlFor="standard-adornment-amount">댓글 작성</InputLabel>
      <Input
        id="standard-adornment-amount"
        value={content}
        onChange={(e) => { setContent(e.target.value) }}
        placeholder="댓글을 작성하세요"
        endAdornment={
          <InputAdornment onClick={createComment} position="start">
            <SendIcon />
          </InputAdornment>
        }
      />
    </FormControl>
  );
};

export default CommentInput;
