import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import NavBar from "../components/NavBar";
import { NumberPicker } from "react-widgets";
import { Table } from 'antd';
import axios from "axios";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import "./AdminOthers.css";

const columns = [
    {
        title: '日期',
        dataIndex: 'date',
        key: 'date',
        render: (text) => <a>{text}</a>,
    },
    {
        title: '时间',
        dataIndex: 'time',
        key: 'time',
    },
    {
        title: '级别',
        dataIndex: 'level',
        key: 'level',
    },
    {
        title: '模块',
        dataIndex: 'module',
        key: 'module',
    },
    {
        title: '信息',
        dataIndex: 'message',
        key: 'message',
    }
];

const AdminOthers = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [data, setData] = useState([]);
    const [year, setYear] = useState(2023);
    const [month, setMonth] = useState(5);
    const [day, setDay] = useState(26);
    const [level, setLevel] = useState("INFO");
    const [startTime, setStartTime] = useState(0);
    const [endTime, setEndTime] = useState(23);
    const [refresh, setRefresh] = useState(false);

    useEffect(() => {
        axios.post("http://"
            + window.location.hostname
            + ":8888/getLogs"
            + "?date=" + `${year.toString().padStart(4, '0')}-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}`
            + "&level=" + level
            + "&time=" + `${startTime}-${endTime}`)
            .then((response) => {
                let tableData = [];
                console.log(response.data);
                response.data.map((element, index) => {
                    tableData.push({
                        key: index,
                        date: element.date,
                        time: element.time,
                        level: element.level,
                        module: element.module,
                        message: element.message
                    });
                });
                setData(tableData);
            })
            .catch((_) => {
            });
    }, [refresh, year, month, day, level, startTime, endTime]);

    return (
        <>
            <div className="bg"></div>
            <div className="bg bg2"></div>
            <div className="bg bg3"></div>
            <NavBar isAdmin="true" userName={userName} />
            <div id="logPageContent">
                <h1>日志查看</h1>
                <div id="logPageSettings">
                    <div id="logDay">
                        <div className="logSettingText">日期</div>
                        <NumberPicker defaultValue={year} step={1} max={Infinity} min={2000} onChange={(value) => {
                            if (value !== null && value >= 2000) {
                                setYear(value);
                            }
                        }}
                                      style={{
                                          width: "12ex",
                                      }}
                        />
                        <div className="logSettingText">年</div>
                        <NumberPicker defaultValue={month} step={1} max={12} min={1} onChange={(value) => {
                            if (value !== null && value >= 1 && value <= 12) {
                                setMonth(value);
                            }
                        }}
                                      style={{
                                          width: "10ex",
                                      }}
                        />
                        <div className="logSettingText">月</div>
                        <NumberPicker defaultValue={day} step={1} max={31} min={1} onChange={(value) => {
                            if (value !== null && value >= 1 && value <= 31) {
                                setDay(value);
                            }
                        }}
                                      style={{
                                          width: "10ex",
                                      }}
                        />
                        <div className="logSettingText">日</div>

                    </div>

                    <div id="logTime">
                        <div className="logSettingText">时间</div>
                        <NumberPicker defaultValue={startTime} step={1} max={23} min={0} onChange={(value) => {
                            if (value !== null && value >= 0 && value <= 23) {
                                setStartTime(value);
                            }
                        }}
                                      style={{
                                          width: "10ex",
                                      }}
                        />
                        <div className="logSettingText">点~</div>
                        <NumberPicker defaultValue={endTime} step={1} max={23} min={0} onChange={(value) => {
                            if (value !== null && value >= 0 && value <= 23) {
                                setEndTime(value);
                            }
                        }}
                                      style={{
                                          width: "10ex",
                                      }}
                        />
                        <div className="logSettingText">点</div>
                    </div>
                    <div id="logType">
                        <div className="logSettingText">类型</div>
                        <Form.Select aria-label="Default select example"
                                     onChange={({ target: { value } }) => {
                                         if (value === "1") {
                                             setLevel("INFO");
                                         }
                                         else if (value === "2") {
                                             setLevel("WARN");
                                         }
                                         else if (value === "3") {
                                             setLevel("ERROR");
                                         }
                                     }}
                                     style={{ width: "100px" }}
                        >
                            <option value="1">INFO</option>
                            <option value="2">WARN</option>
                            <option value="3">ERROR</option>
                        </Form.Select>
                    </div>
                    <div>
                        <Button variant="primary" onClick={() => setRefresh(!refresh)}>
                            刷新
                        </Button>
                    </div>

                </div>
                <div id="logTableBox">
                    <Table id="logTable" columns={columns} dataSource={data} />
                </div>

            </div>

        </>
    )
}

export default AdminOthers;
