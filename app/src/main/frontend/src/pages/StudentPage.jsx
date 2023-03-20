import React, { useEffect, useState } from 'react'
import { useLocation } from 'react-router-dom';
import NavBar from '../components/NavBar'

function StudentPage() {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");

    const [listening, setListening] = useState(false);
    const [data, setData] = useState([]);
    let eventSource = undefined;

    useEffect(() => {
        if (!listening) {
            eventSource = new EventSource("http://localhost:8888/time");
            eventSource.onopen = (event) => {
                console.log("connection opened");
            }
            eventSource.onmessage = (event) => {
                console.log("result", event.data);
                setData(old => [...old, event.data]);
            }
            eventSource.onerror = (event) => {
                console.log(event.target.readyState);
                if (event.target.readyState === EventSource.CLOSED) {
                    console.log(`eventsource closed (${event.target.readyState})`);
                }
                eventSource.close();
            }
            setListening(true);
        }
        return () => {
            eventSource.close();
            console.log("eventsource closed");
        }
    }, []);

    return (
        <div>
            <NavBar isAdmin="false" userName={userName} />
            <h1>welcome, {userName}.</h1>
            <ul>
                Received Data
                {data.map(d => <li key={d}>{d}</li>)}
            </ul>
        </div>
    )
}

export default StudentPage
