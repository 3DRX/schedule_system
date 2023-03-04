import React from "react";
import { useLocation } from "react-router-dom";
import ClassTable from "../components/ClassTable";
import NavBar from "../components/NavBar";

const AdminCourse = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");

    return (
        <>
            <NavBar isAdmin="true" userName={userName} />
            <ClassTable isAdmin={true} />
        </>
    )
}

export default AdminCourse;
