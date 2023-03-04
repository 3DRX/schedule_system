import React from "react";
import { useLocation } from "react-router-dom";
import ClassTable from "../components/ClassTable";
import NavBar from "../components/NavBar";

const StudentCourse = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");

    return (
        <>
            <NavBar isAdmin="false" userName={userName} />
            <ClassTable isAdmin={false} />
        </>
    )
}

export default StudentCourse;
