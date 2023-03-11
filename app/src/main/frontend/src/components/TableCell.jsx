import React, { useEffect, useRef, useState } from "react";
import axios from "axios";
import "./TableCell.css";
import Button from 'react-bootstrap/Button';
import Popover from 'react-bootstrap/Popover';
import Modal from 'react-bootstrap/Modal';
import Overlay from 'react-bootstrap/Overlay';

// TableCell组件：
// 1. 发送http请求，查询当前时段是否有课程。
// 2. 若有课程，显示课程名称和上课地点，点击按钮打开详细内容浮窗，其中有学生名单和删除课程按钮。
// 3. 若无课程，显示添加课程按钮。
export default function TableCell({ startTime, week, day, userName, isAdmin, setShowModal, setAddClassInfo, refresh }) {
    const [name, setName] = useState("");
    const [location, setLocation] = useState("");
    const [students, setStudents] = useState([]);
    const [hover, setHover] = useState(false);
    const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
    const [showOverlay, setShowOverlay] = useState(false);
    const [refreshOnDel, setRefreshOnDel] = useState(false);
    const target = useRef(null);

    // 在特定条件下重新发送请求，刷新表格内容
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

    // 发送删除课程的请求
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
        // 若当前格无课程，显示添加课程按钮
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
        // 当前格有课程，显示detail按钮
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
                    <Modal.Title>删除课程：{name}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    此课程一经删除，无法恢复。
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowDeleteConfirm(false)}>
                        取消
                    </Button>
                    <Button variant="primary" onClick={() => {
                        setDeleteCourseRequest();
                        setShowDeleteConfirm(false);
                    }}>确认删除</Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
}
