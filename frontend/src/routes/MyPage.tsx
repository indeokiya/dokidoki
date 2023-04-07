import { createTheme, ThemeProvider } from '@mui/material/styles';
import CssBaseline from '@mui/material/CssBaseline';
import Box from '@mui/material/Box';
import MypageHeader from '../components/mypage/MypageHeader';
import MypageNavigator from '../components/mypage/MypageNavigator';
import { useState, useEffect } from 'react';
import { Outlet } from 'react-router';
import { useRecoilState, useRecoilValue } from 'recoil';
import { myPageMenuState, userInfoState } from 'src/store/userInfoState';
import { useLocation } from 'react-router';
import { useNavigate } from 'react-router';
let theme = createTheme({
  palette: {
    primary: {
      light: '#63ccff',
      main: '#009be5',
      dark: '#006db3',
    },
  },
  typography: {
    h5: {
      fontWeight: 500,
      fontSize: 26,
      letterSpacing: 0.5,
    },
  },
  shape: {
    borderRadius: 8,
  },
  components: {
    MuiTab: {
      defaultProps: {
        disableRipple: true,
      },
    },
  },
  mixins: {
    toolbar: {
      minHeight: 48,
    },
  },
});

theme = {
  ...theme,
  components: {
    MuiDrawer: {
      styleOverrides: {
        paper: {
          backgroundColor: '#081627',
        },
      },
    },
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: 'none',
        },
        contained: {
          boxShadow: 'none',
          '&:active': {
            boxShadow: 'none',
          },
        },
      },
    },
    MuiTabs: {
      styleOverrides: {
        root: {
          marginLeft: theme.spacing(1),
        },
        indicator: {
          height: 3,
          borderTopLeftRadius: 3,
          borderTopRightRadius: 3,
          backgroundColor: theme.palette.common.white,
        },
      },
    },
    MuiTab: {
      styleOverrides: {
        root: {
          textTransform: 'none',
          margin: '0 16px',
          minWidth: 0,
          padding: 0,
          [theme.breakpoints.up('md')]: {
            padding: 0,
            minWidth: 0,
          },
        },
      },
    },
    MuiIconButton: {
      styleOverrides: {
        root: {
          padding: theme.spacing(1),
        },
      },
    },
    MuiTooltip: {
      styleOverrides: {
        tooltip: {
          borderRadius: 4,
        },
      },
    },
    MuiDivider: {
      styleOverrides: {
        root: {
          backgroundColor: 'rgb(255,255,255,0.15)',
        },
      },
    },
    MuiListItemButton: {
      styleOverrides: {
        root: {
          '&.Mui-selected': {
            color: '#4fc3f7',
          },
        },
      },
    },
    MuiListItemText: {
      styleOverrides: {
        primary: {
          fontSize: 14,
          fontWeight: theme.typography.fontWeightMedium,
        },
      },
    },
    MuiListItemIcon: {
      styleOverrides: {
        root: {
          color: 'inherit',
          minWidth: 'auto',
          marginRight: theme.spacing(2),
          '& svg': {
            fontSize: 20,
          },
        },
      },
    },
    MuiAvatar: {
      styleOverrides: {
        root: {
          width: 32,
          height: 32,
        },
      },
    },
  },
};

const drawerWidth = 256;

export default function Paperbase() {
  const [menu, setMenu] = useRecoilState(myPageMenuState);
  const [selectedMenu, setSelectedMenu] = useState(menu.menu);
  const param = useLocation();
  const loginUser = useRecoilValue(userInfoState)
  const navigate = useNavigate();

  //전역 변수로 메뉴 넣어줌use
  useEffect(() => {
    // setMenu({ menu: selectedMenu })
    if(!loginUser.is_logged_in){
      navigate("/login");
    }
    // console.log(" 지금 페이지의 url paht >>> ",param.pathname)
    if (param.pathname.split('/')[2] === "auction-item") {
      setMenu({ menu: '입찰 중' });
    }
    if (param.pathname.split('/')[2] === 'auction-history') {
      setMenu({ menu: '구매 내역' });
    }
    if (param.pathname.split('/')[2] === 'sale-item') {
      setMenu({ menu: '판매 중' });
    }
    if (param.pathname.split('/')[2] === 'sale-history') {
      setMenu({ menu: '판매 내역' });
    }
    if (param.pathname.split('/')[2] === 'bookmark-list') {
      setMenu({ menu: '관심 내역' });
    }
    if (param.pathname.split('/')[2] === 'aler-history') {
      setMenu({ menu: '알림 내역' });
    }
  }, [param.pathname]);

  return (
    <ThemeProvider theme={theme}>
      <Box sx={{ display: 'flex', minHeight: '100vh' }}>
        <CssBaseline />
        <Box component="nav" sx={{ width: { sm: drawerWidth }, flexShrink: { sm: 0 } }}>
          {/* 왼쪽 네이게이션 바 */}
          <MypageNavigator
            PaperProps={{ style: { width: drawerWidth } }}
            sx={{ display: { sm: 'block', xs: 'none' } }}
            setSelectedMenu={setSelectedMenu}
          />
        </Box>
        <Box sx={{ flex: 1, display: 'flex', flexDirection: 'column' }}>
          {/* 상단 해더 */}
          <MypageHeader selectedMenu={selectedMenu} />
          <Box component="main" sx={{ flex: 1, py: 6, px: 4, bgcolor: '#eaeff1' }}>
            <Outlet />
          </Box>
          <Box component="footer" sx={{ p: 2, bgcolor: '#eaeff1' }}></Box>
        </Box>
      </Box>
    </ThemeProvider>
  );
}
