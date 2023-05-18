import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import NavBar from "../components/NavBar";
import Button from 'react-bootstrap/Button';
import { Table } from 'antd';
import axios from "axios";
import Modal from 'react-bootstrap/Modal';
import { NumberPicker } from "react-widgets";
import Form from 'react-bootstrap/Form';

const columns = [
    {
        title: '名称',
        dataIndex: 'name',
        key: 'name',
        render: (text) => <a>{text}</a>,
    },
    {
        title: '时间',
        dataIndex: 'time',
        key: 'time',
    },
    {
        title: '地点',
        dataIndex: 'location',
        key: 'location',
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
    }
];

const StudentOthers = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [data, setData] = useState([]);
    const [refresh, setRefresh] = useState(false);
    const [showModal, setShowModal] = useState(false);

    // refresh table data
    useEffect(() => {
        axios.get("http://"
            + window.location.hostname
            + ":8888/referEvents?studentName="
            + userName)
            .then((response) => {
                const data = response.data;
                let tableData = [];
                // TODO: set action to delete button in the following code
                data.forEach((element, index) => {
                    console.log(element);
                    tableData.push({
                        key: index,
                        name: element.name,
                        time: `第${element.time.week}周，周${element.time.day}，${element.time.time}点`,
                        location: element.location,
                    });
                });
                setData(tableData);
            })
            .catch((error) => {
                console.log(error);
            });
    }, [refresh]);

    const modalOnShow = () => {
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setRefresh(!refresh);
        setShowModal(false);
    };

    return (
        <>
            <NavBar isAdmin="false" userName={userName} />
            <h1>临时事物管理</h1>
            <Button variant="secondary"
                onClick={() => {
                    setRefresh(!refresh);
                }}
                id="refreshButton"
            >刷新</Button>
            <Button variant="secondary"
                onClick={() => {
                    setShowModal(true);
                }}
                id="globalAddButton"
            >添加临时事物
            </Button>
            <Table columns={columns} dataSource={data} />
            <Modal
                show={showModal}
                onHide={() => {
                    setShowModal(false);
                }}
                onShow={modalOnShow}
                backdrop="static"
            >
                <Modal.Header id="header" closeButton>
                    <Modal.Title>添加临时事物</Modal.Title>
                </Modal.Header>
                <Form id="form" onSubmit={handleSubmit}>
                    <Modal.Body>
                        <p>名称</p>
                        <Form.Control size="sm" type="text" placeholder="在此输入事物名称……"
                            onChange={({ target: { value } }) => {
                                if (value !== "") {
                                }
                            }}
                        />
                        <p>时间</p>
                        <a>第</a>
                        <NumberPicker defaultValue={1} step={1} max={20} min={1} onChange={(value) => {
                            if (value !== null && value >= 1 && value <= 20) {
                            }
                        }}
                            style={{
                                width: "10ex",
                            }}
                        />
                        <a>周，周</a>
                        <NumberPicker defaultValue={1} step={1} max={20} min={1} onChange={(value) => {
                            if (value !== null && value >= 1 && value <= 20) {
                            }
                        }}
                            style={{
                                width: "10ex",
                            }}
                        />
                        <a>，</a>
                        <NumberPicker defaultValue={1} step={1} max={20} min={1} onChange={(value) => {
                            if (value !== null && value >= 1 && value <= 20) {
                            }
                        }}
                            style={{
                                width: "10ex",
                            }}
                        />
                        <a>点</a>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={() => setShowModal(false)}>
                            Close
                        </Button>
                        <Button variant="primary" type="submit">
                            Save Changes
                        </Button>
                    </Modal.Footer>
                </Form>
            </Modal>
        </>
    )
}

export default StudentOthers;

