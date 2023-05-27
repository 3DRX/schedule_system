import React, { useRef, useState } from "react";
import Button from 'react-bootstrap/Button';
import Popover from 'react-bootstrap/Popover';
import Overlay from 'react-bootstrap/Overlay';
import Modal from 'react-bootstrap/Modal';
import axios from "axios";

const CellDetail = ({ course, refresh, setRefresh, isAdmin }) => {
    const [showOverlay, setShowOverlay] = useState(false);
    const [showDeleteConfirm, setShowDeleteConfirm] = useState(false);
    const target = useRef(null);
    const [showActivityDetail, setShowActivityDetail] = useState(false);
    const [showDeleteActivityModal, setShowDeleteActivityModal] = useState(false);
    // 发送删除课程的请求
    const setDeleteCourseRequest = () => {
        axios.delete("http://" + window.location.hostname + `:8888/deleteCourse?courseName=${course.name}`)
            .then((response) => {
                if (response) {
                    setRefresh(!refresh);
                }
                else {
                    console.warn(`删除课程${course.name}失败`);
                }
            });
    }

    const renderButton = () => {
        if (isAdmin) {
            return (
                <>
                    <Button className="btn" variant="info" ref={target} onClick={() => setShowOverlay(!showOverlay)}>detail</Button>
                    <Overlay target={target.current} show={showOverlay} placement="right">
                        <Popover id="popover-basic" show={showOverlay}>
                            <Popover.Header as="h3">{course.name}</Popover.Header>
                            <Popover.Body>
                                <h5>Students</h5>
                                <ul>
                                    {course.students.map((student) => {
                                        return <li>{student}</li>
                                    })}
                                </ul>
                                <Button className="btn" variant="outline-danger" size="sm" onClick={() => {
                                    setShowOverlay(false);
                                    setShowDeleteConfirm(true);
                                }}>delete</Button>
                            </Popover.Body>
                        </Popover>
                    </Overlay>
                    <Modal
                        show={showDeleteConfirm}
                        onHide={() => setShowDeleteConfirm(false)}
                        backdrop="static"
                        keyboard={false}
                    >
                        <Modal.Header closeButton>
                            <Modal.Title>删除课程：{course.name}</Modal.Title>
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
                </>
            )
        }
        else if(course.isActivity)
        {
            return (
                <>
                    <Button className="btn" variant="info" ref={target} onClick={() => {}}>detail</Button>
                    <Overlay target={target.current} show={showActivityDetail} placement="right">
                        <Popover id="popover-basic" show={showActivityDetail}>
                            <Popover.Header as="h3">{course.name}</Popover.Header>
                            <Popover.Body>
                                <h5>Students</h5>
                                <ul>
                                    {course.students.map((student) => {
                                        return <li>{student}</li>
                                    })}
                                </ul>
                                <Button className="btn" variant="outline-danger" size="sm" onClick={() => {
                                }}>delete</Button>
                            </Popover.Body>
                        </Popover>
                    </Overlay>
                    <Modal
                        show={showDeleteActivityModal}
                        onHide={() => {}}
                        backdrop="static"
                        keyboard={false}
                    >
                        <Modal.Header closeButton>
                            <Modal.Title>删除课程：{course.name}</Modal.Title>
                        </Modal.Header>
                        <Modal.Body>
                            此课程一经删除，无法恢复。
                        </Modal.Body>
                        <Modal.Footer>
                            <Button variant="secondary" onClick={() => {}}>
                                取消
                            </Button>
                            <Button variant="primary" onClick={() => {
                            }}>确认删除</Button>
                        </Modal.Footer>
                    </Modal>
                </>
            )
        }
    }

    return (
        <>
            <p>{course.name}</p>
            <p>{course.location}</p>
            {renderButton()}
        </>
    )
}

export default CellDetail;
