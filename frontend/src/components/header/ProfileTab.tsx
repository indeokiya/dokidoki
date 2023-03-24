import styled from 'styled-components';
import { List, ListItem, IconButton, ListItemText } from '@mui/material';

const Profile = () => {
  const profileList = [
    {
      text: 'profile',
    },
    {
      text: 'alert',
    },
  ];

  //커스텀 메뉴
  const ProfileDiv = styled.div`
    width: 150px;
    padding: 5px;
  `;

  const Demo = styled('div')(({ theme }) => ({
    backgroundColor: theme.palette.background.paper,
  }));

  return (
    <ProfileDiv>
      <Demo>
        <List>
          {profileList.map((data) => (
            <ListItem
              secondaryAction={
                <IconButton edge="end" aria-label="delete">
                  <h1>icon</h1>
                </IconButton>
              }
            >
              <ListItemText primary="Single-line item" />
            </ListItem>
          ))}
        </List>
      </Demo>
    </ProfileDiv>
  );
};

export default Profile;
