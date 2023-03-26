import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';
import NavBar from '../components/NavBar'
import { InputNumber } from 'rsuite';
import { notification } from 'antd';
import Button from 'react-bootstrap/Button';

function StudentPage() {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [start, setStart] = useState(false);
    const [data, setData] = useState("");
    const [eventSource, setEventSource] = useState(null);
    const [week, setWeek] = useState(1);
    const [day, setDay] = useState(1);
    const [time, setTime] = useState(8);

    const [api, contextHolder] = notification.useNotification();

    useEffect(() => {
        if (data.split(",")[0].length !== 0) {
            openNotification();
        }
    }, [data]);

    const openNav = () => {
        console.log("open navigation");
        // TODO
        api.destroy();
    }

    const openNotification = () => {
        const key = `open${Date.now()}`;
        const btn = (
            <>
                <Button size='sm' variant='primary' onClick={openNav} >
                    导航
                </Button>
            </>
        );
        api.open({
            message: '上课提醒：' + data.split(",")[0],
            description:
                `地点：${data.split(",")[1]}`,
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
            const index = (60 * (week - 1)) + (12 * (day - 1)) + (time - 8);
            console.log("http://localhost:8888/time/" + userName + "/" + index);
            const newEventSource = new EventSource("http://localhost:8888/time/" + userName + "/" + index);
            setEventSource(newEventSource);
            newEventSource.onopen = (event) => {
                console.log("connection opened");
            }
            newEventSource.onmessage = (event) => {
                // event.data 格式: name,location,x,y,newIndex
                console.log("result", event.data);
                const reIndex = parseInt(event.data.split(",")[4]);
                console.log(reIndex);
                setWeek(parseInt(reIndex / 60) + 1);
                setDay(parseInt((reIndex % 60) / 12) + 1);
                setTime((reIndex % 12) + 8);
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
            <NavBar isAdmin="false" userName={userName} enabled={!start} />
            <h1>welcome, {userName}.</h1>
            <Button size='sm' variant='outline-primary' onClick={handleOnClick}>{start ? "stop" : "start"}</Button>
            <Button size='sm' variant='outline-secondary' onClick={() => {
                setWeek(1);
                setDay(1);
                setTime(8);
                setData("");
            }}>reset</Button>
            <p>
                第
                <InputNumber
                    value={week}
                    onChange={setWeek}
                    step={1}
                    max={20}
                    min={1}
                    style={{
                        width: "10ex",
                    }}
                />
                周
            </p>
            <p>
                周
                <InputNumber
                    value={day}
                    onChange={setDay}
                    step={1}
                    max={5}
                    min={1}
                    style={{
                        width: "10ex",
                    }}
                />
            </p>
            <p>
                <InputNumber
                    value={time}
                    onChange={setTime}
                    step={1}
                    max={20}
                    min={8}
                    style={{
                        width: "10ex",
                    }}
                />
                点
            </p>
            {contextHolder}
            <ul>
                Received Data
                <li>{data.split(",")[0]}</li>
                <li>{data.split(",")[1]}</li>
                <li>{data.split(",")[2]}</li>
                <li>{data.split(",")[3]}</li>
            </ul>
        </div>
    )
}

export default StudentPage
