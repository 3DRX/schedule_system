import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import {
    createBrowserRouter,
    RouterProvider,
    Route,
} from "react-router-dom";
import LoginPage from './pages/LoginPage';
import StudentPage from './pages/StudentPage';
import AdminPage from './pages/AdminPage';
import AdminCourse from './pages/AdminCourse';
import AdminActivities from './pages/AdminActivities';
import AdminOthers from './pages/AdminOthers';
import StudentCourse from './pages/StudentCourse';
import StudentActivities from './pages/StudentActivities';
import StudentOthers from './pages/StudentOthers';
import Navigation from './pages/Navigation';

// 不同URL对应不同的页面
const router = createBrowserRouter([
    {
        path: "/",
        element: <LoginPage />
    },
    {
        path: "/student",
        element: <StudentPage />
    },
    {
        path: "/admin",
        element: <AdminPage />
    },
    {
        path: "/admin/course",
        element: <AdminCourse />
    },
    {
        path: "/admin/activities",
        element: <AdminActivities />
    },
    {
        path: "/admin/others",
        element: <AdminOthers />
    },
    {
        path: "/student/course",
        element: <StudentCourse />
    },
    {
        path: "/student/activities",
        element: <StudentActivities />
    },
    {
        path: "/student/others",
        element: <StudentOthers />
    },
    {
        path: "/student/nav",
        element: <Navigation />
    },
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <RouterProvider router={router} />
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
