import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';
import NavBar from '../components/NavBar'
import { InputNumber } from 'rsuite';
import { notification } from 'antd';
import Button from 'react-bootstrap/Button';
import "./StudentPage.css";
import DailyDashBoard from '../components/DailyDashBoard';

function StudentPage() {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [start, setStart] = useState(false);
    const [data, setData] = useState("");
    const [eventSource, setEventSource] = useState(null);
    const [week, setWeek] = useState(1);
    const [day, setDay] = useState(1);
    const [time, setTime] = useState(6);
    const [refreshDashBoard, setRefreshDashBoard] = useState(false);

    const [api, contextHolder] = notification.useNotification();

    useEffect(() => {
        if (data.split(",")[0].length !== 0) {
            openNotification();
        }
    }, [data]);

    function isValidUrl(string) {
        try {
            new URL(string);
            return true;
        } catch (err) {
            return false;
        }
    }


    const openNav = () => {
        const location = data.split(",")[1];
        if (isValidUrl(location)) {
            window.open(location);
        }
        else {
            const prefix = "http://" + window.location.host;
            console.log(`data: ${data}`);
            const courseName = data.split(",")[0];
            const isCourse = data.split(",")[3];
            console.log("open navigation");
            console.log(`isCourse: ${isCourse}`);
            window.open(`${prefix}/student/nav?courseName=${courseName}&location=${location}&isCourse=${isCourse}&userName=${userName}`, "_self");
            api.destroy();
        }
    }

    const openNotification = () => {
        const key = `open${Date.now()}`;
        const btn = (
            <>
                <Button size='sm' variant='primary' onClick={openNav}>
                    去上课
                </Button>
            </>
        );
        api.open({
            message: '事件提醒：' + data.split(",")[0],
            description: `地点：${data.split(",")[1]}`,
            btn,
            key,
            onClose: () => { },
        });
    };

    const handleOnClick = () => {
        setStart(!start);
        if (eventSource !== null) {
            console.log("connection closed");
            eventSource.close();
            setEventSource(null);
        }
        else {
            const index = (112 * (week - 1)) + (16 * (day - 1)) + (time - 6);
            console.log("http://" + window.location.hostname + ":8888/time/" + userName + "/" + index);
            const newEventSource = new EventSource(
                "http://"
                + window.location.hostname
                + ":8888/time"
                + "?id="
                + userName
                + "&start="
                + index);
            setEventSource(newEventSource);
            newEventSource.onopen = (_) => {
                console.log("connection opened");
            }
            newEventSource.onmessage = (event) => {
                // event.data 格式: name,location,x,y,newIndex,isCourse
                console.log("result", event.data);
                const reIndex = parseInt(event.data.split(",")[2]);
                console.log(reIndex);
                setWeek(parseInt(reIndex / 112) + 1);
                setDay(parseInt((reIndex % 112) / 16) + 1);
                setTime((reIndex % 16) + 6);
                setData(event.data);
            }
            newEventSource.onerror = (event) => {
                console.log(event.target.readyState);
                if (event.target.readyState === EventSource.CLOSED) {
                    console.log(`eventsource closed (${event.target.readyState})`);
                }
                newEventSource.close();
            }
            setEventSource(newEventSource);
        }
    }

    return (
        <div>
            <div className="bg"></div>
            <div className="bg bg2"></div>
            <div className="bg bg3"></div>
            <NavBar isAdmin="false" userName={userName} enabled={!start} />
            <div id="studentPageContent">
                <div id="welcome">
                    <h1>welcome, {userName}.</h1>
                </div>
                <div id="timeForm">
                    <div id="twoButtons">
                        <div id="buttonBox">
                            <div>
                                <Button size='sm' variant='outline-primary' onClick={handleOnClick}>
                                    {start ? "暂停" : "开始"}
                                </Button>
                            </div>
                            <div>
                                <Button size='sm' variant='outline-secondary' onClick={() => {
                                    setWeek(1);
                                    setDay(1);
                                    setTime(6);
                                    setData("");
                                    setRefreshDashBoard(!refreshDashBoard);
                                }}>重置</Button>
                            </div>
                        </div>
                    </div>
                    <div id="timeWeek">
                        <div>第</div>
                        <div>
                            <InputNumber
                                id="weekInput-time"
                                value={week}
                                onChange={(value) => {
                                    if (value === "") {
                                        setWeek("");
                                    }
                                    else if (value > 20) {
                                        setWeek(20);
                                    }
                                    else if (value < 1) {
                                        setWeek(1);
                                    }
                                    else {
                                        setWeek(parseInt(value));
                                    }
                                }}
                                step={1}
                                max={20}
                                min={1}
                            />
                        </div>
                        <div>周</div>
                    </div>
                    <div id="timeHour">
                        <div>周</div>
                        <div>
                            <InputNumber
                                id="dayInput"
                                value={day}
                                onChange={(value) => {
                                    if (value === "") {
                                        setDay("");
                                    }
                                    else if (value > 7) {
                                        setDay(7);
                                    }
                                    else if (value < 1) {
                                        setDay(1);
                                    }
                                    else {
                                        setDay(parseInt(value));
                                    }
                                }}
                                step={1}
                                max={7}
                                min={1}
                            />
                        </div>
                        <div>
                            <InputNumber
                                id="timeInput"
                                value={time}
                                onChange={(value) => {
                                    if (value === "") {
                                        setTime("");
                                    }
                                    else if (value > 22) {
                                        setTime(22);
                                    }
                                    else if (value < 6) {
                                        setTime(6);
                                    }
                                    else {
                                        setTime(parseInt(value));
                                    }
                                }}
                                step={1}
                                max={22}
                                min={6}
                            />
                        </div>
                        <div>点</div>
                    </div>
                    {contextHolder}
                </div>
                <div id="nextdayNotifyBox">
                    <DailyDashBoard
                        id="nextdayNotify"
                        studentName={userName}
                        week={week}
                        day={day}
                        refresh={refreshDashBoard}
                    />
                </div>

            </div>
        </div>
    )
}

export default StudentPage
