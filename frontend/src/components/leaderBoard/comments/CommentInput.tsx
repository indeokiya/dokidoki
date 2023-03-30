import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import FormControl from '@mui/material/FormControl';
import Input from '@mui/material/Input';
import SendIcon from '@mui/icons-material/Send';
import { useState } from "react";
import { auctionAPI } from 'src/api/axios';


const CommentInput: React.FC<{auction_id: string, parent_id: number | null, refetch: Function}> = (props) => {
  const { auction_id, parent_id, refetch } = props

  // 댓글 State
  const [content, setContent] = useState("")

  const createComment = (event: any) => {
    event.preventDefault()
    auctionAPI.post(`${auction_id}/comments`, {
      content: content,
      parent_id: parent_id
    }).then(() => {
      refetch()  // 댓글 다시 조회하기
      setContent("")  // 댓글 초기화
      alert("댓글이 등록되었습니다.")
    }).catch(err => {
      console.error("ERROR >>", err)
      alert("댓글이 등록되지 않았습니다.")
    });
  }

  // Enter Event
  const handleKeyDown = (event: any) => {
    if (event.key === 'Enter') {
      createComment(event)
    }
  }

  return (
    <FormControl fullWidth sx={{ mb: 4 }} variant="standard">
      <InputLabel htmlFor="standard-adornment-amount">댓글 작성</InputLabel>
      <Input
        id="standard-adornment-amount"
        value={content}
        onChange={(e) => { setContent(e.target.value) }}
        placeholder="댓글을 작성하세요"
        onKeyDown={handleKeyDown}
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
