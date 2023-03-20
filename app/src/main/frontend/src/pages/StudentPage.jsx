import React, { useState } from 'react'
import { useLocation } from 'react-router-dom';
import NavBar from '../components/NavBar'

function StudentPage() {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [start, setStart] = useState(false);
    const [data, setData] = useState("");
    const [eventSource, setEventSource] = useState(null);

    const handleOnClick = () => {
        setStart(!start);
        if (eventSource !== null) {
            console.log("connection closed");
            eventSource.close();
            setEventSource(null);
        }
        else {
            const newEventSource = new EventSource("http://localhost:8888/time/" + userName);
            setEventSource(newEventSource);
            newEventSource.onopen = (event) => {
                console.log("connection opened");
            }
            newEventSource.onmessage = (event) => {
                console.log("result", event.data);
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
            <NavBar isAdmin="false" userName={userName} />
            <h1>welcome, {userName}.</h1>
            <button onClick={handleOnClick}>{start ? "stop" : "start"}</button>
            <h3> Received Data </h3>
            <h4>{data}</h4>
        </div>
    )
}

export default StudentPage
