import React, { useEffect, useRef, useState } from "react";
import axios from "axios";
import "./TableCell.css";
import CellWithCourse from "./CellWithCourse";

// TableCell组件：
// 1. 发送http请求，查询当前时段是否有课程。
// 2. 若有课程，显示课程名称和上课地点，点击按钮打开详细内容浮窗，其中有学生名单和删除课程按钮。
// 3. 若无课程，显示添加课程按钮。
export default function TableCell({ startTime, week, day, userName, isAdmin, setShowModal, setAddClassInfo, refresh, setRefresh }) {
    const [courses, setCourses] = useState([]);
    const [hover, setHover] = useState(false);

    // 在特定条件下重新发送请求，刷新表格内容
    useEffect(() => {
        axios.get("http://" + window.location.hostname + ":8888/getCourseStatusByTime", {
            params: {
                time: startTime + '-' + (startTime + 1),
                week: week,
                day: day,
                userName: userName,
            }
        })
            .then((response) => {
                setCourses(response.data);
            })
    }, [startTime, week, day, userName, refresh]);

    const renderContent = () => {
        if (courses.length === 0) {
            return (
                <div onClick={() => {
                    // console.log(`在第${week}周，周${day}，${startTime}-${startTime + 1}添加课程`);
                    setAddClassInfo({
                        week: week,
                        day: day,
                        startTime: startTime
                    })
                    setShowModal(true);
                }}
                    style={{
                        backgroundColor: hover ? "lightgrey" : "white",
                    }}
                    onMouseEnter={() => { setHover(true) }}
                    onMouseLeave={() => { setHover(false) }}
                >+
                </div>
            );
        }
        else {
            return (
                <CellWithCourse courses={courses} refresh={refresh} setRefresh={setRefresh} isAdmin={isAdmin} />
            )
        }
    }

    return (
        <div className="TableCell"
            style={{
                width: "20ex",
                height: "20ex",
            }}
        >
            {renderContent()}
        </div >
    );
}
