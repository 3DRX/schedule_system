import React from "react";
import { useLocation } from "react-router-dom";
import NavBar from "../components/NavBar";

const AdminCourse = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");

    return (
        <>
            <NavBar isAdmin="true" userName={userName} />
            <h1>Admin Course</h1>
        </>
    )
}

export default AdminCourse;
