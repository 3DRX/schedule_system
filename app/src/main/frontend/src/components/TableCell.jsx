import React, { useEffect, useState } from "react";
import axios from "axios";
import "./TableCell.css";

export default function TableCell({ startTime, week, day, userName, isAdmin }) {
    const [name, setName] = useState("");
    const [location, setLocation] = useState("");
    const [students, setStudents] = useState([]);
    const [hover, setHover] = useState(false);

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
                setStudents(response.data.students)
            })
    }, [startTime, week, day, userName]);

    const getStudents = () => {
        if (!isAdmin) {
            return React.useMemo(() => [], []);
        }
        else {
            return React.useMemo(
                () => {
                    let res = [];
                    for (let index = 0; index < students.length; index++) {
                        const element = students[index];
                        index === students.length - 1 ?
                            res.push(
                                <a>{element}</a>
                            ) :
                            res.push(
                                <a>{element},</a>
                            );
                    }
                    return res;
                }
            );
        }
    }

    const addNewClassByClick = () => {
        if (!isAdmin || (name !== "" && location !== "" && students !== [])) {
            return React.useMemo(() => [], []);
        }
        else {
            return React.useMemo(
                () => {
                    let res = [];
                    res.push(
                        <div
                            onClick={() => {
                                console.log(`在第${week}周，周${day}，${startTime}-${startTime + 1}添加课程`);
                            }}
                        >+</div>
                    );
                    return res;
                }
            );
        }
    }

    return (
        <div className="TableCell"
            style={{
                backgroundColor: hover ? "lightgrey" : "white",
                fontWeight: "bold",
            }}
            onMouseEnter={() => { setHover(true) }}
            onMouseLeave={() => { setHover(false) }}
        >
            <p>{name}</p>
            <p>{location}</p>
            {getStudents()}
            {addNewClassByClick()}
        </div>
    );
}
