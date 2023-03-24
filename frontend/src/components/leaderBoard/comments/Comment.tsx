import Avatar from '@mui/material/Avatar';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import styled from 'styled-components';
import EastIcon from '@mui/icons-material/East';
import Button from '@mui/material/Button';
//댓글 하나의 형태 만들기
//child : 대댓글 인지 여부
//isWriter : 자신이 작성자 인지 확인 id값을 비교해서 true false로 넘겨준다.
//isMine : 사진의 댓글이면 삭제가 허용된다. => 이것은 삭제된 댓글입니다 넣을지 고민해야함
const Comment: React.FC<{
  name: string;
  avatar: string;
  text: string;
  isChild: boolean;
  isWriter: boolean;
  isMine: boolean;
  written_time: string;
  isColor: boolean;
}> = (props) => {
  const StyledSpan = styled.span`
    border-radius: 4px;
    margin: 10px;
    padding: 8px;
    box-sizing: border-box;
    background-color: dodgerBlue;
    color: white;
    font-weight: bold;
  `;

  const StyledDiv = styled.div`
    display: flex;
    align-items: center;
  `;

  return (
    <Grid container sx={{ background: props.isColor ? 'whitesmoke' : 'white', padding: '1rem' }}>
      {/* 대댓글인지 표시 */}
      {props.isChild && (
        <Grid item xs={1} alignContent="end">
          <EastIcon />
        </Grid>
      )}

      {/* 작성시간 */}
      <Grid item xs={10}>
        <Grid container>
          <Grid item xs={12}>
            <StyledDiv>
              {/* 아바타 표시 */}
              <Avatar alt="Remy Sharp" src={props.avatar} />

              {/* 글작성자 인지아닌지 표시 */}
              {props.isWriter ? (
                <StyledSpan>{props.name}</StyledSpan>
              ) : (
                <Typography variant="body1" mx="1rem">
                  {props.name}
                </Typography>
              )}
              <Typography variant="subtitle2" align="right">
                {props.written_time.substr(0, 10)}
              </Typography>
              {/* 댓글의 주인이라면 삭제버튼을 볼 수 있다. */}
              {props.isMine && (
                <Button variant="text" color="error">
                  삭제
                </Button>
              )}
            </StyledDiv>
          </Grid>

          {/* 댓글이 삭제되면 삭제되었다고 표시됨 이것은 대댓글이 달려있을 때를 위함임   */}
          {props.text.length !== 0 ? (
            <Grid item xs={12}>
              <Typography variant="body1" my={1}>
                {props.text}
              </Typography>
            </Grid>
          ) : (
            <Grid item xs={12}>
              <Typography variant="body1" color="GrayText">
                삭제된 댓글입니다.
              </Typography>
            </Grid>
          )}
        </Grid>
      </Grid>
    </Grid>
  );
};

export default Comment;
