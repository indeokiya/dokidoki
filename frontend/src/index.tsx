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
    ],
  },
  {
    path: 'product/:id',
    element: <ProductPage />,
  },
  {
    path: '/mypage',
    element: <ProfilePage />,
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
