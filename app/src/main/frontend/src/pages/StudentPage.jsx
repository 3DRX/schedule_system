import React, { useState } from 'react'
import { useLocation } from 'react-router-dom';
import NavBar from '../components/NavBar'

function StudentPage() {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [start, setStart] = useState(false);
    const [data, setData] = useState("");
    const [eventSource, setEventSource] = useState(null);
    const [index, setIndex] = useState(0);

    const handleOnClick = () => {
        setStart(!start);
        if (eventSource !== null) {
            console.log("connection closed");
            eventSource.close();
            setEventSource(null);
        }
        else {
            const newEventSource = new EventSource("http://localhost:8888/time/" + userName + "/" + index);
            setEventSource(newEventSource);
            newEventSource.onopen = (event) => {
                console.log("connection opened");
            }
            newEventSource.onmessage = (event) => {
                // event.data 格式: name,location,x,y,newIndex
                console.log("result", event.data);
                setIndex(parseInt(event.data.split(",")[4]));
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
                setIndex(0);
                setData("");
            }}>reset</button>
            <h4>index = {index}</h4>
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
