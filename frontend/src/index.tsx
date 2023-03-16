import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import reportWebVitals from "./reportWebVitals";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import RootLayout from "./routes/RootLayout";
import RendingPageContainer from "./routes/RendingPageContainer";
import ActionPageContent from "./routes/ActionPageContent";
import TestContainer from "./routes/TestContainer";

const router = createBrowserRouter([
  {
    path: "/",
    element: <RootLayout />,
    children: [
      {
        path: "",
        element: <RendingPageContainer />,
      },
      {
        path: "action",
        element: <ActionPageContent />,
        children: [
          {
            path: ":id",
            element: <h1>이곳은 모달로띄우는 페이지 입니다.</h1>,
          },
        ],
      },
    ],
  },
  {
    path: "/test",
    element: <TestContainer />,
  },
]);

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  // <React.StrictMode>
  <RouterProvider router={router}></RouterProvider>
  // </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
