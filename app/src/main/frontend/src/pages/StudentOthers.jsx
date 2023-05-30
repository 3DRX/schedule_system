import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import NavBar from "../components/NavBar";
import Button from 'react-bootstrap/Button';
import { Table } from 'antd';
import axios from "axios";
import Modal from 'react-bootstrap/Modal';
import { NumberPicker } from "react-widgets";
import Form from 'react-bootstrap/Form';
import "./StudentOthers.css";

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
    const [name, setName] = useState("");
    const [week, setWeek] = useState(1);
    const [day, setDay] = useState(1);
    const [time, setTime] = useState(8);
    const [location, setLocation] = useState("");

    // refresh table data
    useEffect(() => {
        axios.get("http://"
            + window.location.hostname
            + ":8888/referEvents?studentName="
            + userName)
            .then((response) => {
                const data = response.data;
                let tableData = [];
                data.forEach((element, index) => {
                    console.log(element)
                    tableData.push({
                        key: index,
                        name: element.name,
                        time: `第${element.time.week}周，周${element.time.day}，${element.time.time}点`,
                        location: element.locationName,
                        action: <Button variant="secondary"
                            onClick={() => {
                                axios.get("http://"
                                    + window.location.hostname
                                    + ":8888/deleteEvent"
                                    + "?studentName="
                                    + userName
                                    + "&eventName="
                                    + element.name)
                                    .then((_) => {
                                        setRefresh(!refresh);
                                    })
                                    .catch((error) => {
                                        console.log(error);
                                    });
                            }}

                        >完成</Button>
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
        const newEvent = {
            student: userName,
            name: name,
            week: week,
            day: day,
            time: time,
            location: location,
        };
        axios.post("http://"
            + window.location.hostname
            + ":8888/addEvent", newEvent)
            .then((_) => {
            })
            .catch((error) => {
                console.log(error);
            })
            .finally(() => {
                setRefresh(!refresh);
                setShowModal(false);
            });
    };

    return (
        <>
            <div className="bg"></div>
            <div className="bg bg2"></div>
            <div className="bg bg3"></div>
            <NavBar isAdmin="false" userName={userName} />
            <div id="StudentOthersContent">
                <h1>临时事务管理</h1>
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
                >添加临时事务
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
                        <Modal.Title>添加临时事务</Modal.Title>
                    </Modal.Header>
                    <Form id="formContent" onSubmit={handleSubmit}>
                        <Modal.Body>
                            <p id="tempEventInfo">
                            <p id="courseName">名称</p>
                            <Form.Control size="sm" type="text" placeholder="在此输入临时事务……"
                                          onChange={({ target: { value } }) => {
                                              if (value !== "") {
                                                  setName(value);
                                              }
                                          }}
                            />
                            <p >时间</p>
                            <div id="tempEventDuration">
                                <div className="weeks" id="tempEventWeek">
                                    <p className="TEtext">第</p>
                                    <NumberPicker defaultValue={week} step={1} max={20} min={1} onChange={(value) => {
                                        if (value !== null && value >= 1 && value <= 20) {
                                            setWeek(value);
                                        }
                                    }}
                                                  style={{
                                                      width: "10ex",
                                                  }}
                                    />
                                    <p className="TEtext">周</p>
                                </div>
                                <div className="weeks" id="tempEventDay">
                                    <p className="TEtext">周</p>
                                    <NumberPicker defaultValue={day} step={1} max={7} min={1} onChange={(value) => {
                                        if (value !== null && value >= 1 && value <= 7) {
                                            setDay(value);
                                        }
                                    }}
                                                  style={{
                                                      width: "10ex",
                                                  }}
                                    />
                                </div>
                                <div className="weeks" id="tempEventTime">
                                    <NumberPicker defaultValue={time} step={1} max={21} min={6} onChange={(value) => {
                                        if (value !== null && value >= 6 && value <= 21) {
                                            setTime(value);
                                        }
                                    }}
                                                  style={{
                                                      width: "10ex",
                                                  }}
                                    />
                                    <p className="TEtext">点</p>
                                </div>

                            </div>
                            </p>
                            <div id="tempEventLocation">
                                <p>地点</p>
                                <Form.Control size="sm" type="text" placeholder="在此输入地点……"
                                              onChange={({ target: { value } }) => {
                                                  if (value !== "") {
                                                      setLocation(value);
                                                  }
                                              }}
                                />
                            </div>
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
            </div>
        </>
    );
};

export default StudentOthers;
