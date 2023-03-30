import styled from 'styled-components';
import Comment from './Comment';
import CommentInput from './CommentInput';
import Typography from '@mui/material/Typography';
import { CommentType } from 'src/datatype/datatype';
import { useReadCommentsQuery } from 'src/hooks/comment';
import { useState, useEffect } from "react";
import { useSetRecoilState } from 'recoil';
import { commentAuctionIdState } from 'src/store/CommentStates';


const CommentsList: React.FC<{ auction_id: string, comments: CommentType[], seller_id: number }> = (props) => {
  const { auction_id, comments, seller_id } = props

  // Comment 관련 컴포넌트에서 사용할 auction_id state
  const setAuctionIdState = useSetRecoilState(commentAuctionIdState)
  useEffect(() => {
    // 렌더링 시 초기값 삽입
    setAuctionIdState(auction_id)
  }, [])

  // 댓글 리스트
  const [commentList, setCommentList] = useState(comments)
  
  // 현재 사용자
  const loginUser = { id: null }
  const userInfo_json = localStorage.getItem("user_info")
  if (userInfo_json != null) {
    loginUser.id = JSON.parse(userInfo_json).user_id
  }

  // 댓글 조회 useQuery. data가 변경되면 comments 갱신
  const { data, refetch } = useReadCommentsQuery(auction_id)
  useEffect(() => {
    if (data !== undefined) {
      setCommentList(data)
    }
  }, [data])

  return (
    <>
      <StyledDiv>
        <Typography variant="h6" sx={{ color: '#3A77EE' }}>
          {' '}
          Q & A
        </Typography>
        <CommentInput parentId={null} refetch={refetch} />
        {commentList.map((data) => {
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
                commentId={data.id}
                refetch={refetch}
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
                        commentId={data.id}
                        refetch={refetch}
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