import React, { useState } from 'react'
import { useLocation } from 'react-router-dom';
import NavBar from '../components/NavBar'
import { NumberPicker } from "react-widgets";
import { InputNumber } from 'rsuite';

function StudentPage() {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [start, setStart] = useState(false);
    const [data, setData] = useState("");
    const [eventSource, setEventSource] = useState(null);
    const [week, setWeek] = useState(1);
    const [day, setDay] = useState(1);
    const [time, setTime] = useState(8);

    const handleOnClick = () => {
        setStart(!start);
        if (eventSource !== null) {
            console.log("connection closed");
            eventSource.close();
            setEventSource(null);
        }
        else {
            const index = 60 * (week - 1) + 12 * (day - 1) + time - 7;
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
                setTime(parseInt(reIndex % 12) + 7);
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
            <button onClick={handleOnClick}>{start ? "stop" : "start"}</button>
            <button onClick={() => {
                setWeek(1);
                setDay(1);
                setTime(8);
                setData("");
            }}>reset</button>
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
                />
                点
            </p>
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
