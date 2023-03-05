import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import ClassTable from "../components/ClassTable";
import NavBar from "../components/NavBar";
import "react-widgets/styles.css";
import { NumberPicker } from "react-widgets";

const StudentCourse = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [week, setWeek] = useState(1);

    return (
        <>
            <NavBar isAdmin="false" userName={userName} />
            <div className="setWeekTab">
                <a>set week</a>
                <NumberPicker defaultValue={1} step={1} max={20} min={1} onChange={(value) => {
                    if (value !== null && (value >= 1 && value <= 20)) {
                        // console.log(`set value to ${value}`)
                        setWeek(value);
                    }
                }}
                    style={{
                        width: "10ex",
                    }}
                />
            </div>
            <ClassTable isAdmin={false} week={week} />
        </>
    )
}

export default StudentCourse;
