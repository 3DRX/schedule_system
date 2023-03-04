import React from "react";
import { useLocation } from "react-router-dom";
import NavBar from "../components/NavBar";

const StudentCourse = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");

    return (
        <>
            <NavBar isAdmin="false" userName={userName} />
            <h1>Student Course</h1>
        </>
    )
}

export default StudentCourse;
