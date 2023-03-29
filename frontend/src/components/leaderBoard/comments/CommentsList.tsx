import styled from 'styled-components';
import avatarImgSrc from '../../../assets/image/profile.png';
import Comment from './Comment';
import CommentInput from './CommentInput';
import Typography from '@mui/material/Typography';
import { CommentType } from 'src/datatype/datatype';


const CommentsList: React.FC<{ auction_id: string, comments: CommentType[], seller_id: number }> = (props) => {
  const { auction_id, comments, seller_id  } = props

  // 현재 사용자
  const loginUser = { id: null }
  const userInfo_json = localStorage.getItem("user_info")
  if (userInfo_json != null) {
    loginUser.id = JSON.parse(userInfo_json).user_id
  }

  return (
    <>
      <StyledDiv>
        <Typography variant="h6" sx={{ color: '#3A77EE' }}>
          {' '}
          Q & A
        </Typography>
        <CommentInput auction_id={auction_id} parent_id={null} />
        {comments.map((data) => {
          return (
            <>
              <Comment
                key={data.id}
                name={data.member_name}
                avatar={data.member_profile}
                text={data.content}
                isChild={false}
                isWriter={seller_id === data.member_id}
                isMine={data.member_id === loginUser.id}
                written_time={data.written_time}
                isColor={true}
              ></Comment>
              {data.sub_comments.length !== 0 &&
                data.sub_comments.map((data, i) => {
                  return (
                    <>
                      <Comment
                        isColor={i % 2 === 0 ? false : true}
                        key={data.id}
                        name={data.member_name}
                        avatar={data.member_profile}
                        text={data.content}
                        isChild={true}
                        isWriter={seller_id === data.member_id}
                        isMine={data.member_id === loginUser.id}
                        written_time={data.written_time}
                      ></Comment>
                    </>
                  );
                })}
              <br />
              <br />
              <br />
              <br />
            </>
          );
        })}
      </StyledDiv>
    </>
  );
};

export default CommentsList;

const StyledDiv = styled.div`
padding: 0 12rem;
box-sizing: border-box;
`;