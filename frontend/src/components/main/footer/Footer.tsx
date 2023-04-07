import styled from 'styled-components';
import Grid from '@mui/material/Grid';
import Typography from '@mui/material/Typography';
import Stack from '@mui/material/Stack';
import Avatar from '@mui/material/Avatar';
import Divider from '@mui/material/Divider';
import Tooltip from '@mui/material/Tooltip';
const Footer = () => {
  const member = [
    {
      name: '전인덕',
      link: 'https://github.com/indeokiya',
    },
    {
      name: '임혜진',
      link: 'https://github.com/hjlim7831',
    },
    {
      name: '윤재휘',
      link: 'https://github.com/HwiHwi523',
    },
    {
      name: '신민혜',
      link: '',
    },
    {
      name: '오종석',
      link: 'https://github.com/jongseok-oh',
    },
    {
      name: '김범식',
      link: 'https://github.com/kimbeomsick',
    },
  ];

  return (
    <>
      <Grid
        container
        width={'100%'}
        sx={{ backgroundColor: '#3A77EE', padding: '1rem' }}
        gap={2}
      >
        <Grid item xs={12} textAlign="center">
          <Typography variant="h5" color="white">
            DOKIDOKI
          </Typography>
        </Grid>
        <Grid item xs={12} textAlign="center">
          <Stack
            direction="row"
            spacing={3}
            divider={<Divider orientation="vertical" color="white" flexItem />}
            justifyContent="center"
          >
            {member.map((data, i) => {
              let linkActive = data.name !== '신민혜';
              return (
                <Tooltip
                  key={i}
                  title={data.name + '의 깃허브로 이동' + (linkActive ? '' : ' 못함.. ')}
                  sx={{cursor:"pointer"}}
                >
                  <StyledLink
                    href={linkActive ? data.link : ''}
                    target="_blank"
                    style={
                      linkActive
                        ? { textDecoration: 'none' }
                        : {
                            textDecoration: 'line-through',
                            pointerEvents: 'none',
                            color: '#9cbbf6',
                          }
                    }
                  >
                    {data.name}
                  </StyledLink>
                </Tooltip>
              );
            })}
          </Stack>
        </Grid>

        <Grid item xs={12} justifyContent="center" textAlign={'center'}>
          <Typography variant="subtitle1" color="#9cbbf6">
            2023. COPYRIGHT <span>©</span> SSAFY. All rights reserved.
          </Typography>
        </Grid>
        <Grid item xs={12}>
          <Stack
            direction="row"
            spacing={2}
            justifyContent="center"
            textAlign="center"
            alignItems="center"
          >
            <Tooltip title="깃허브">
              <Avatar
                src="https://static-00.iconduck.com/assets.00/gitlab-logo-illustration-512x471-9t1zrpas.png"
                onClick={() => {
                  window.open('https://lab.ssafy.com/s08-bigdata-dist-sub2/S08P22A202');
                }}
              />
            </Tooltip>
            <Tooltip title="싸피">
              <Avatar
                src="https://www.ssafy.com/swp_m/images/common/logo3.png"
                onClick={() => {
                  window.open('https://edu.ssafy.com/comm/login/SecurityLoginForm.do');
                }}
              />
            </Tooltip>
            <Tooltip title="지라">
              <Avatar
                src="https://logos-world.net/wp-content/uploads/2021/02/Jira-Emblem.png"
                onClick={() => {
                  window.open(
                    'https://ssafy.atlassian.net/jira/software/c/projects/S08P22A202/boards/1750',
                  );
                }}
              />
            </Tooltip>
          </Stack>
        </Grid>
      </Grid>
    </>
  );
};

export default Footer;

const StyledLink = styled.a`
  color: white;
  cursor:pointer;
`;
