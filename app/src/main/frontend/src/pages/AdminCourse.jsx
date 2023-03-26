import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import ClassTable from "../components/ClassTable";
import Button from 'react-bootstrap/Button';
import NavBar from "../components/NavBar";
import "react-widgets/styles.css";
import { NumberPicker } from "react-widgets";
import "./AdminCourse.css";
const AdminCourse = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [week, setWeek] = useState(1);
    const [refresh, setRefresh] = useState(false);
    const [showModal, setShowModal] = useState(false);

    return (
        <>
            <NavBar isAdmin="true" userName={userName} />
            <div className="setWeekTab">
                <div id="texts">set week</div>
                <NumberPicker defaultValue={1} step={1} max={20} min={1} onChange={(value) => {
                    if (value !== null && (value >= 1 && value <= 20)) {
                        // console.log(`set value to ${value}`)
                        setWeek(value);
                    }
                }}
                    class="input"
                />
                <Button variant="secondary"
                    onClick={() => {
                        setRefresh(!refresh);
                    }}
                    id="refreshButton"
                >刷新</Button>
                <Button variant="secondary"
                    onClick={() => {
                        // console.log(`在第${week}周，周${day}，${startTime}-${startTime + 1}添加课程`);
                        setShowModal(true);
                    }}
                    id="globalAddButton"
                >添加课程
                </Button>
            </div>

            <ClassTable
                isAdmin={true}
                week={week}
                refresh={refresh}
                setRefresh={setRefresh}
                setShowModal={setShowModal}
                showModal={showModal}
            />
        </>
    )
}

export default AdminCourse;
