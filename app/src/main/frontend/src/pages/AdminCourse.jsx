import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import ClassTable from "../components/ClassTable";
import NavBar from "../components/NavBar";
import "react-widgets/styles.css";
import { NumberPicker } from "react-widgets";

const AdminCourse = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [week, setWeek] = useState(1);

    return (
        <>
            <NavBar isAdmin="true" userName={userName} />
            <NumberPicker defaultValue={1} step={1} max={20} min={1} onChange={(value) => {
                setWeek(value);
            }} />
            <ClassTable isAdmin={true} week={week} />
        </>
    )
}

export default AdminCourse;
