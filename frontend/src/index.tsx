import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import RootLayout from './routes/RootLayout';
import RendingPageContainer from './routes/RendingPageContainer';
import ActionPageContent from './routes/AuctionPageContent';
import TestContainer from './routes/TestContainer';
import ProfilePage from './routes/MyPage';
import LoginPage from './routes/LoginPage';
import RegisterPage from './routes/RegisterPage';
import ProductPage from './routes/ProductPage';
import AuctionUpdatePage from './routes/AuctionUpdatePage';
import TokenRedirectPage from './routes/TokenRedirectPage';
import IngContentItemList from './components/mypage/ing_contents/IngContentItemList';
import EndContentItemList from './components/mypage/end_contents/EndContentItemList';
import AlertItemList from './components/mypage/alert_contents/AlertItemList';
import { QueryClient, QueryClientProvider } from 'react-query';
import { RecoilRoot } from 'recoil';
import GlobalFont from './styles/GlobalFont';
import { SnackbarProvider } from 'notistack';


const queryClient = new QueryClient();

const router = createBrowserRouter([
  {
    path: '/',
    element: <RootLayout />,
    children: [
      {
        path: '',
        element: <RendingPageContainer />,
      },
      {
        path: 'auction',
        element: <ActionPageContent />,
      },

      {
        path: 'login',
        element: <LoginPage />,
      },
      {
        path: 'redirect',
        element: <TokenRedirectPage />,
      },
      {
        path: 'regist',
        element: <RegisterPage />,
      },
      {
        path: 'auction/product/:id',
        element:<ProductPage />
      },
      {
        path: 'auction/update/:id',
        element: <AuctionUpdatePage />
      },
      {
        path: 'mypage',
        element: <ProfilePage />,
        children: [
          {
            path: 'auction-item', //기본 페이지라 path가 없음 
            element: <IngContentItemList />,
          },
          {
            path: 'auction-history',
            element: <EndContentItemList />,
          },
          {
            path: 'sale-item',
            element: <IngContentItemList />,
          },
          {
            path: 'sale-history',
            element: <EndContentItemList />,
          },
          {
            path: 'bookmark-list',
            element: <IngContentItemList />,
          },
          {
            path: 'alert-history',
            element: <AlertItemList />,
          },
        ],
      },
    ],
  },


  {
    path: '/test',
    element: <TestContainer />,
  },
]);

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  // <React.StrictMode>
  <RecoilRoot>
    <SnackbarProvider maxSnack={5}>
    <GlobalFont />
    <QueryClientProvider client={queryClient}>
    <SnackbarProvider maxSnack={10}>
      <RouterProvider router={router} ></RouterProvider>,
     </SnackbarProvider>
    </QueryClientProvider>
    </SnackbarProvider>
  </RecoilRoot>,

  // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
