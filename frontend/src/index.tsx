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
import IngContentItem from './components/mypage/ing_contents/IngContentItem';
import EndContentItem from './components/mypage/end_contents/EndContentItem';
import IngContentItemList from './components/mypage/ing_contents/IngContentItemList';
import EndContentItemList from './components/mypage/end_contents/EndContentItemList';
import AlertItemList from './components/mypage/alert_contents/AlertItemList';

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
        path: 'regist',
        element: <RegisterPage />,
      },
      {
        path: 'auction/product/:id',
        element: <ProductPage />,
      },
    ],
  },

  {
    path: '/mypage',
    element: <ProfilePage />,
    children: [
      {
        path: 'action-item',
        element: <IngContentItemList />,
      },
      {
        path: 'action-history',
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
  {
    path: '/test',
    element: <TestContainer />,
  },
]);

const root = ReactDOM.createRoot(document.getElementById('root') as HTMLElement);
root.render(
  // <React.StrictMode>
  <RouterProvider router={router}></RouterProvider>,
  // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
