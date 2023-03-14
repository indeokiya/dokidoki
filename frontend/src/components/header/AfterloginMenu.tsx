import { Grid, Button, Avatar, Badge } from "@mui/material";
import imgSrc from "../../../src/assets/image/profile.png"
import styled from "styled-components";

const AfterLoginMenu = () => {

    const StyledBadge = styled(Badge)(({ theme }) => ({
        '& .MuiBadge-badge': {
          backgroundColor: '#44b700',
          color: '#44b700',
          boxShadow: `0 0 0 2px `,
          '&::after': {
            position: 'absolute',
            top: 0,
            left: 0,
            width: '100%',
            height: '100%',
            borderRadius: '50%',
            animation: 'ripple 1.2s infinite ease-in-out',
            border: '1px solid currentColor',
            content: '""',
          },
        },
        '@keyframes ripple': {
          '0%': {
            transform: 'scale(.8)',
            opacity: 1,
          },
          '100%': {
            transform: 'scale(2.4)',
            opacity: 0,
          },
        },
      }));

  const loginUser={name : "김범식"}
  return (
    <Grid container alignItems={"center"} spacing={1}>
      <Grid item>
        <Button> Action </Button>
      </Grid>
      <Grid item>
        <Button> MyPage </Button>
      </Grid>
      <Grid item>
        <p>{loginUser.name}님 환영합니다.</p>
      </Grid>
      <Grid item>
      <StyledBadge
  overlap="circular"
  anchorOrigin={{ vertical: 'bottom', horizontal: 'right' }}
  variant="dot"
>
  <Avatar alt="Remy Sharp" src={imgSrc} />
</StyledBadge>
      </Grid>
    </Grid>
  );
};

export default AfterLoginMenu;
