import React, { useEffect, useState } from "react";
import axios from "axios";

export default function TableCell({ startTime, week, day, userName }) {
    const [name, setName] = useState("");
    const [location, setLocation] = useState("");

    useEffect(() => {
        axios.get("http://" + window.location.hostname + ":8080/getCourseStatusByTime", {
            params: {
                time: startTime + '-' + (startTime + 1),
                week: week,
                day: day,
                userName: userName,
            }
        })
            .then((response) => {
                // console.log(response.data)
                setName(response.data.name)
                setLocation(response.data.location)
            })
    }, [startTime, week, day, userName]);

    return (
        <>
            <p>{name}</p>
            <p>{location}</p>
        </>
    );
}
