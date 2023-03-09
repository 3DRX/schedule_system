import React, { useEffect, useRef, useState } from "react";
import axios from "axios";
import "./TableCell.css";
import Button from 'react-bootstrap/Button';
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';
import Popover from 'react-bootstrap/Popover';
import Modal from 'react-bootstrap/Modal';
import Overlay from 'react-bootstrap/Overlay';

export default function TableCell({ startTime, week, day, userName, isAdmin, setShowModal, setAddClassInfo, refresh }) {
    const [name, setName] = useState("");
    const [location, setLocation] = useState("");
    const [students, setStudents] = useState([]);
    const [hover, setHover] = useState(false);
    const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
    const [showOverlay, setShowOverlay] = useState(false);
    const [refreshOnDel, setRefreshOnDel] = useState(false);
    const target = useRef(null);

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
    }, [startTime, week, day, userName, refresh, refreshOnDel]);

    const setDeleteCourseRequest = () => {
        axios.delete(`http://localhost:8080/deleteCourse?courseName=${name}`)
            .then((response) => {
                if (response) {
                    setRefreshOnDel(!refreshOnDel);
                }
                else {
                    console.warn(`删除课程${name}失败`);
                }
            });
    }

    let studentsHover;
    if (isAdmin) {
        if (name === "") {
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
                    <li key={index}>{item}</li>
                )
            })
            studentsHover = (
                <>
                    <Button className="btn" variant="info" ref={target} onClick={() => setShowOverlay(!showOverlay)}>detail</Button>
                    <Overlay target={target.current} show={showOverlay} placement="right">
                        <Popover id="popover-basic" show={showOverlay}>
                            <Popover.Header as="h3">{name}</Popover.Header>
                            <Popover.Body>
                                <h5>Students</h5>
                                <ul>
                                    {studentList}
                                </ul>
                                <Button className="btn" variant="outline-danger" size="sm" onClick={() => {
                                    setShowOverlay(false);
                                    setShowDeleteConfirm(true);
                                }}>delete</Button>
                            </Popover.Body>
                        </Popover>
                    </Overlay>
                </>
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
            <Modal
                show={showDeleteConfirm}
                onHide={() => setShowDeleteConfirm(false)}
                backdrop="static"
                keyboard={false}
            >
                <Modal.Header closeButton>
                    <Modal.Title>Modal title</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    I will not close if you click outside me. Don't even try to press
                    escape key.
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowDeleteConfirm(false)}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={() => {
                        setDeleteCourseRequest();
                        setShowDeleteConfirm(false);
                    }}>确认删除课程</Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}
