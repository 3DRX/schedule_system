import React, { useEffect, useState } from "react";
import axios from "axios";
import "./TableCell.css";
import Button from 'react-bootstrap/Button';
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';
import Popover from 'react-bootstrap/Popover';

export default function TableCell({ startTime, week, day, userName, isAdmin, setShowModal, setAddClassInfo, refresh }) {
    const [name, setName] = useState("");
    const [location, setLocation] = useState("");
    const [students, setStudents] = useState([]);
    const [hover, setHover] = useState(false);

    useEffect(() => {
        axios.get("http://" + window.location.hostname + ":8080/getCourseStatusByTime", {
            params: {
                time: startTime + '-' + (startTime + 1),
                week: week,
                day: day,
                userName: userName,
            }
        })
            .then((response) => {
                // console.log(response.data)
                setName(response.data.name)
                setLocation(response.data.location)
                setStudents(response.data.students)
            })
    }, [startTime, week, day, userName, refresh]);

    let studentsHover;
    if (isAdmin) {
        if (students.length === 0) {
            studentsHover = (
                <div onClick={() => {
                    // console.log(`在第${week}周，周${day}，${startTime}-${startTime + 1}添加课程`);
                    setAddClassInfo({
                        week: week,
                        day: day,
                        startTime: startTime
                    })
                    setShowModal(true);
                }}
                    style={{
                        backgroundColor: hover ? "lightgrey" : "white",
                    }}
                    onMouseEnter={() => { setHover(true) }}
                    onMouseLeave={() => { setHover(false) }}
                >+
                </div>
            );
        }
        else {
            let studentList = students.map((item, index) => {
                return (
                    <a key={index}>{item}{index === students.length - 1 ? "" : ","}</a>
                )
            })
            const popover = (
                <Popover id="popover-basic">
                    <Popover.Header as="h3">Students</Popover.Header>
                    <Popover.Body>
                        {studentList}
                    </Popover.Body>
                </Popover>
            );
            studentsHover = (
                <OverlayTrigger trigger="click" placement="top" overlay={popover}>
                    <Button className="btn" variant="info" >detail</Button>
                </OverlayTrigger>
            )
        }
    }
    else {
        studentsHover = <></>
    }

    return (
        <div className="TableCell" >
            <p>{name}</p>
            <p>{location}</p>
            {studentsHover}
        </div>
    );
}
