import React, { useEffect, useState } from "react";
import { Table } from 'antd';
import NavBar from "../components/NavBar";
import axios from "axios";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import { useLocation } from "react-router-dom";

const columns = [
    {
        title: '用户名',
        dataIndex: 'name',
        key: 'name',
        render: (text) => <a>{text}</a>,
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
    }
];

const AdminStudents = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [data, setData] = useState([]);
    const [showConfirm, setShowConfirm] = useState(false);
    const [studentToBeDeleted, setStudentToBeDeleted] = useState("");
    const [refresh, setRefresh] = useState(false);

    useEffect(() => {
        axios.get("http://"
            + window.location.hostname
            + ":8888/studentList")
            .then((response) => {
                let tableData = [];
                response.data.forEach((element, index) => {
                    tableData.push({
                        key: index,
                        name: element,
                        action: <Button variant="danger"
                            onClick={() => {
                                setShowConfirm(true);
                                setStudentToBeDeleted(element);
                            }}
                        > 删除</Button>
                    });
                });
                setData(tableData);
            })
            .catch((error) => {
                console.log(error);
            });
    }, [refresh]);

    return (
        <>
            <div className="bg"></div>
            <div className="bg bg2"></div>
            <div className="bg bg3"></div>
            <NavBar isAdmin="true" userName={userName} />
            <div id="userContent" style={{paddingTop:50,}}>
                {/*<Button onClick={() => setRefresh(!refresh)}>刷新</Button>*/}
                <div id="logTableBox">
                    <Table id="logTable" columns={columns} dataSource={data} />
                </div>
            </div>


            <Modal
                show={showConfirm}
                onHide={() => setShowConfirm(false)}
                backdrop="static"
            >
                <Modal.Header closeButton>
                    <Modal.Title>删除学生：{studentToBeDeleted}</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    此操作一旦进行，无法撤销。
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowConfirm(false)}>
                        取消
                    </Button>
                    <Button variant="primary" onClick={() => {
                        axios.post("http://"
                            + window.location.hostname
                            + ":8888/deleteStudent"
                            + "?id=" + studentToBeDeleted)
                            .then((_) => {
                                setStudentToBeDeleted("");
                            })
                            .catch((error) => {
                                console.log(error);
                            })
                            .finally(() => {
                                setRefresh(!refresh);
                            });
                        setShowConfirm(false);
                    }}>确认删除</Button>
                </Modal.Footer>
            </Modal>
        </>
    );
};

export default AdminStudents;
