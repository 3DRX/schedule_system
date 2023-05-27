import React, { useEffect } from "react";
import { useState } from "react";
import axios from "axios";
import "./DailyDashBoard.css";
const DailyDashBoard = ({ studentName, week, day, refresh }) => {
    const [data, setData] = useState([]);

    useEffect(() => {
        axios.get("http://"
            + window.location.hostname
            + ":8888/getDashBoard"
            + `?week=${week}`
            + `&day=${day}`
            + `&studentName=${studentName}`)
            .then((response) => {
                setData(response.data);
            })
            .catch((error) => {
                console.log(error);
            })
            .finally(() => {
                console.log(data);
            });
    }, [refresh, week, day]);

    return (
        <div id="notifyContentBoard">
            <div id="notifyContentBoardTitle">
                <h3>当天所有日程</h3>
            </div>
            <div id="notifyItem">
                {data.map((item) => {
                    return (
                        <div key={item.name} className="item">
                            <a>Name: </a>
                            <p>{item.name}</p>
                            <a>Students: </a>
                            {item.students.map((student) => {
                                return <p>{student}</p>
                            })}
                            <a>Location: </a>
                            <p>{item.location}</p>
                            <a>Type: </a>
                            <p>{item.isActivity ? "Activity" : "Course"}</p>
                        </div>
                    );
                })}
            </div>

        </div>
    );
};

export default DailyDashBoard;
