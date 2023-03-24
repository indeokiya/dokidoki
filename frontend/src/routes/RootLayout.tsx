import React from 'react';
import Header from '../components/header/Header';
import { Outlet } from 'react-router-dom';

const RootLayout = () => {
  return (
    <>
      <Header></Header>
      <Outlet />
    </>
  );
};

export default RootLayout;
