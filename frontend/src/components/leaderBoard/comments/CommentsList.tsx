import styled from 'styled-components';
import avatarImgSrc from '../../../assets/image/profile.png';
import Comment from './Comment';
import CommentInput from './CommentInput';
import Typography from '@mui/material/Typography';
const writer = {
  //게시글을작성한 사람
  id: 2,
};

const loginUser = {
  //지금 로그인한 사람
  name: '전인덕',
  id: 2,
};

const CommentsList = () => {
  const commentslist = [
    {
      id: 1,
      avatar: avatarImgSrc,
      member_id: 1,
      name: '김범식',
      content: '제품 색상이 어떻게 되나요?',
      written_time: '2023-03-12T15:17:43.589+00:00',
      sub_comments: [
        {
          id: 2,
          avatar: avatarImgSrc,
          member_id: 2,
          name: '김범식',
          content: '봉고블루입니다.',
          written_time: '2023-03-12T15:18:08.445+00:00',
          sub_comments: [],
        },
        {
          id: 3,
          avatar: avatarImgSrc,
          member_id: 1,
          name: '김범식',
          content: '그럼 메모리는 얼마나 되나요?',
          written_time: '2023-03-12T15:20:59.215+00:00',
          sub_comments: [],
        },
        {
          id: 4,
          avatar: avatarImgSrc,
          member_id: 2,
          name: '김범식',
          content: '뭐 이리 궁금한 게 많아요?',
          written_time: '2023-03-12T15:21:18.355+00:00',
          sub_comments: [],
        },
        {
          id: 11,
          avatar: avatarImgSrc,
          member_id: 1,
          name: '김범식',
          content: '고소할게요.',
          written_time: '2023-03-12T15:32:44.293+00:00',
          sub_comments: [],
        },
      ],
    },
    {
      id: 1,
      avatar: avatarImgSrc,
      member_id: 1,
      name: '김범식',
      content: '제품 색상이 어떻게 되나요?',
      written_time: '2023-03-12T15:17:43.589+00:00',
      sub_comments: [
        {
          id: 2,
          avatar: avatarImgSrc,
          member_id: 2,
          name: '김범식',
          content: '봉고블루입니다.',
          written_time: '2023-03-12T15:18:08.445+00:00',
          sub_comments: [],
        },
        {
          id: 3,
          avatar: avatarImgSrc,
          member_id: 1,
          name: '김범식',
          content: '그럼 메모리는 얼마나 되나요?',
          written_time: '2023-03-12T15:20:59.215+00:00',
          sub_comments: [],
        },
        {
          id: 4,
          avatar: avatarImgSrc,
          member_id: 2,
          name: '김범식',
          content: '뭐 이리 궁금한 게 많아요?',
          written_time: '2023-03-12T15:21:18.355+00:00',
          sub_comments: [],
        },
        {
          id: 11,
          avatar: avatarImgSrc,
          member_id: 1,
          name: '김범식',
          content: '고소할게요.',
          written_time: '2023-03-12T15:32:44.293+00:00',
          sub_comments: [],
        },
      ],
    },
  ];

  const StyledDiv = styled.div`
    padding: 0 12rem;
    box-sizing: border-box;
  `;

  return (
    <>
      <StyledDiv>
        <Typography variant="h6" sx={{ color: '#3A77EE' }}>
          {' '}
          Q & A
        </Typography>
        <CommentInput />
        {commentslist.map((data) => {
          return (
            <>
              <Comment
                key={data.id}
                name={data.name}
                avatar={data.avatar}
                text={data.content}
                isChild={false}
                isWriter={writer.id === data.member_id}
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
                        name={data.name}
                        avatar={data.avatar}
                        text={data.content}
                        isChild={true}
                        isWriter={writer.id === data.member_id}
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
