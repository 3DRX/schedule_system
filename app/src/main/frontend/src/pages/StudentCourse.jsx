import React, {useEffect, useState} from "react";
import { useLocation } from "react-router-dom";
import ClassTable from "../components/ClassTable";
import Button from 'react-bootstrap/Button';
import NavBar from "../components/NavBar";
import "react-widgets/styles.css";
import { NumberPicker } from "react-widgets";
import "./AdminCourse.css";
import Modal from "react-bootstrap/Modal";
import Form from "react-bootstrap/Form";
import axios from "axios";
import "./StudentCourse.css";

// 学生课程主页
// 1. 设置周数
// 2. 刷新按钮
// 3. 课程表（CourseTable组件）
const StudentCourse = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [week, setWeek] = useState(1);
    const [newName, setNewName] = useState("");
    const [startWeek, setStartWeek] = useState(1);
    const [endWeek, setEndWeek] = useState(1);
    const [testWeek, setTestWeek] = useState(1);
    const [classDay, setClassDay] = useState(1);
    const [classTime, setClassTime] = useState(8);
    const [classDuration, setClassDuration] = useState(1);
    const [location, setLocation] = useState("");
    const [refresh, setRefresh] = useState(false);
    const [showAddActivity, setShowAddActivity] = useState((false));
    const [addActivityInfo, setAddActivityInfo] = useState({});
    const [studentList, setStudentList] = useState([]);
    const [selectAllStudents, setSelectAllStudents] = useState(false);
    const [checkedState, setCheckedState] = useState(
        new Array(studentList.length).fill(false)
    );
    useEffect(() => {
        setCheckedState(new Array(studentList.length).fill(selectAllStudents));
    }, [selectAllStudents]);

    useEffect(() => {
        axios.get("http://" + window.location.hostname + ":8888/studentList")
            .then((response) => {
                setStudentList(response.data);
            })
    }, [refresh]);

    useEffect(() => {
        setClassDay(addActivityInfo.day);
        setClassTime(addActivityInfo.startTime);
        setStartWeek(addActivityInfo.week);
    }, [addActivityInfo]);


    const activityOnShow = () => {
        // console.log(`studentList: ${studentList}`);
        setCheckedState(new Array(studentList.length).fill(false));
        setSelectAllStudents(false);
    };
    const handleActivitySubmit = (e) => {
        e.preventDefault();
        let resStudents = [];
        for (let i = 0; i < studentList.length; i++) {
            if (checkedState[i]) {
                resStudents.push(studentList[i]);
            }
        }
        let res = {
            course: {
                startWeek: startWeek,
                endWeek: endWeek,
                testWeek: testWeek,
                classTime: {
                    day: classDay,
                    time: classTime,
                    duration: classDuration
                },
                examTime: {
                    day: classDay,
                    time: classTime,
                    duration: classDuration
                },
                name: newName,
                location: location
            },
            students: resStudents
        };
        // console.log(res);
        axios.post("http://" + window.location.hostname + ":8888/addCourse", res)
            .then((response) => {
                // console.log(response.data)
            })
            .finally(() => {
                setRefresh(!refresh);
                setShowAddActivity(false);
            });
    };
    const generateOptions = () => {
        if (addActivityInfo.startTime === 19) {
            return (
                <option>1h</option>
            )
        }
        else if (addActivityInfo.startTime === 18) {
            return (
                <>
                    <option>1h</option>
                    <option>2h</option>
                </>
            )
        }
        else {
            return (
                <>
                    <option>1h</option>
                    <option>2h</option>
                    <option>3h</option>
                </>
            )
        }
    };
    const handleOnChange = (position) => {
        const updatedCheckedState = checkedState.map((item, index) =>
            index === position ? !item : item
        );
        setCheckedState(updatedCheckedState);
    };

    return (
        <>
            <NavBar isAdmin="false" userName={userName} />
            <div id="searchArea">
                <div className="texts">搜索</div>
                <div><input id="searchBox" type="text"/></div>
                <button id="searchButton" type="submit"></button>
            </div>
            <div className="setWeekTab">
                <div className="texts">set week</div>
                <NumberPicker defaultValue={1} step={1} max={20} min={1} onChange={(value) => {
                    if (value !== null && (value >= 1 && value <= 20)) {
                        // console.log(`set value to ${value}`)
                        setWeek(value);
                    }
                }}
                              class="input"
                />
                <Button variant="secondary"
                        onClick={() => {
                            setRefresh(!refresh);
                        }}
                        id="refreshButton"
                >刷新</Button>
                <Button variant="secondary"
                        onClick={() => {
                            // console.log(`在第${week}周，周${day}，${startTime}-${startTime + 1}添加课程`);
                            setShowAddActivity(true);
                        }}
                        id="globalAddButton"
                >添加课外活动
                </Button>
            </div>
            <ClassTable isAdmin={false} week={week} refresh={refresh} setRefresh={setRefresh}
            />

            <Modal
                show={showAddActivity}
                onHide={() => {
                    setShowAddActivity(false);
                    setAddActivityInfo({});
                }}
                onShow={activityOnShow}
                backdrop="static"
            >
                <Modal.Header id="header" closeButton>
                    <Modal.Title>添加课外活动</Modal.Title>
                    {/* `周${addClassInfo.day}，${addClassInfo.startTime}-${addClassInfo.startTime + 1}：`*/}
                </Modal.Header>
                <Form id="formContent" onSubmit={handleActivitySubmit}>
                    <Modal.Body>
                        <p id="courseInfo">
                            <p id="courseName">
                                活动名称
                            </p>
                            <Form.Control id="courseNameInput" size="sm" type="text" placeholder="在此输入活动名称……"
                                          onChange={({ target: { value } }) => {
                                              if (value !== "") {
                                                  setNewName(value)
                                              }
                                          }}
                            />
                            <p id="duration">
                                <div className="weeks">
                                    <text>开始周</text>
                                    <NumberPicker defaultValue={week} step={1} max={20} min={1} onChange={(value) => {
                                        if (value !== null && value >= 1 && value <= 20) {
                                            setStartWeek(value);
                                            // console.log(`开始周：${value}`);
                                        }
                                    }}
                                                  style={{
                                                      width: "10ex",
                                                  }}
                                    />
                                </div>
                                <div className="weeks">
                                    <text>结束周</text>
                                    <NumberPicker defaultValue={null} step={1} max={20} min={startWeek}
                                                  onChange={(value) => {
                                                      if (value !== null && value >= 1 && value <= 20) {
                                                          setEndWeek(value);
                                                          // console.log(`结束周：${value}`);
                                                      }
                                                  }}
                                                  style={{
                                                      width: "10ex",
                                                  }}
                                    />
                                </div>
                                {/*<div className="weeks">
                                    <text>考试周</text>
                                    <NumberPicker defaultValue={null} step={1} max={20} min={endWeek + 1}
                                                  onChange={(value) => {
                                                      if (value !== null && value >= 1 && value <= 20) {
                                                          setTestWeek(value);
                                                          // console.log(`考试周：${value}`);
                                                      }
                                                  }}
                                                  style={{
                                                      width: "10ex",
                                                  }}
                                    />
                                </div>
                                */}
                            </p>
                        </p>

                        <p id="courseTime">
                            <p>
                                开始时间
                            </p>
                            <p id="classTime">
                                <div id="week">
                                    星期
                                </div>
                                <div id="weekInput">
                                    <NumberPicker defaultValue={addActivityInfo.day} step={1} max={5} min={1} onChange={(value) => {
                                        if (value !== null && value >= 1 && value <= 5) {
                                            setClassDay(value);
                                            // console.log(`星期：${value}`);
                                        }
                                    }}
                                                  style={{
                                                      width: "10ex",
                                                  }}
                                    />
                                </div>
                                <div>
                                    <NumberPicker defaultValue={addActivityInfo.startTime} step={1} max={20} min={8} onChange={(value) => {
                                        if (value !== null && value >= 8 && value <= 20) {
                                            setClassTime(value);
                                            // console.log(`${value}点`);
                                        }
                                    }}
                                                  style={{
                                                      width: "10ex",
                                                  }}
                                    />
                                </div>
                                <div id="hour">
                                    点
                                </div>
                                <div id="lasting">
                                    时长
                                </div>
                                <div>
                                    <Form.Select size="sm"
                                                 controlId="exampleForm.duration"
                                                 style={{
                                                     width: "10ex",
                                                     height: "5.1ex"
                                                 }}
                                                 onChange={({ target: { value } }) => {
                                                     setClassDuration(parseInt(value[0]));
                                                 }}
                                    >
                                        {generateOptions()}
                                    </Form.Select>
                                </div>
                            </p>
                            <p id="location">
                                活动地点
                            </p>
                            <p>
                                <Form.Control id="locationInput" size="sm" type="text" placeholder="在此输入活动地点……"
                                              controlId="exampleForm.location"
                                              onChange={({ target: { value } }) => {
                                                  if (value !== "") {
                                                      setLocation(value)
                                                  }
                                              }}
                                />
                            </p>
                        </p>

                        <p id="studentInfo">
                            <p id="studentTitle">
                                <div>参与学生</div>
                                <div id="allSelected">
                                    <Form.Check
                                        type={"checkbox"}
                                        id="allSelectedBox"
                                        checked={selectAllStudents}
                                        onClick={() => {
                                            setSelectAllStudents(!selectAllStudents);
                                        }}
                                    />  全选
                                </div>
                            </p>

                            {studentList.map((name, index) => (
                                <div key={`${name}`} id="mb-3">
                                    <Form.Check
                                        type={"checkbox"}
                                        id={`${index}`}
                                        label={`${name}`}
                                        checked={checkedState[index]}
                                        onChange={() => handleOnChange(index)}
                                    />
                                </div>
                            ))}
                        </p>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={() => setShowAddActivity(false)}>
                            Close
                        </Button>
                        <Button variant="primary" type="submit">
                            Save Changes
                        </Button>
                    </Modal.Footer>
                </Form>
            </Modal>
        </>
    )
}

export default StudentCourse;
