import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import { useTable } from "react-table";
import TableCell from "../components/TableCell";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import axios from "axios";
import Form from 'react-bootstrap/Form';
import { NumberPicker } from "react-widgets";
import "./ClassTable.css";

export default function ClassTable({ isAdmin, week, refresh, setRefresh }) {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [showModal, setShowModal] = useState(false);
    const [addClassInfo, setAddClassInfo] = useState({});

    const [newName, setNewName] = useState("");
    const [startWeek, setStartWeek] = useState(1);
    const [endWeek, setEndWeek] = useState(1);
    const [testWeek, setTestWeek] = useState(1);
    const [classDay, setClassDay] = useState(1);
    const [classTime, setClassTime] = useState(8);
    const [classDuration, setClassDuration] = useState(1);
    const [location, setLocation] = useState("");
    const [studentList, setStudentList] = useState([]);

    const [checkedState, setCheckedState] = useState(
        new Array(studentList.length).fill(false)
    );

    useEffect(() => {
        axios.get("http://" + window.location.hostname + ":8888/studentList")
            .then((response) => {
                setStudentList(response.data);
            })
    }, [refresh]);

    const handleOnChange = (position) => {
        const updatedCheckedState = checkedState.map((item, index) =>
            index === position ? !item : item
        );
        setCheckedState(updatedCheckedState);
    };

    const data = React.useMemo(
        () => {
            let res = [];
            for (let i = 8; i <= 19; i++) {
                let cell = { col0: i + '-' + (i + 1) };
                for (let j = 1; j <= 5; j++) {
                    let key = "col" + j;
                    cell[key] = <TableCell
                        setAddClassInfo={setAddClassInfo}
                        setShowModal={setShowModal}
                        startTime={i}
                        week={week}
                        day={j}
                        userName={userName}
                        isAdmin={isAdmin}
                        refresh={refresh}
                        setRefresh={setRefresh}
                    />;
                }
                res.push(cell);
            }
            return res;
        }, [week, refresh]
    )

    const columns = React.useMemo(
        () => [
            {
                Header: '',
                accessor: 'col0',
            },
            {
                Header: '一',
                accessor: 'col1',
            },
            {
                Header: '二',
                accessor: 'col2',
            },
            {
                Header: '三',
                accessor: 'col3',
            },
            {
                Header: '四',
                accessor: 'col4',
            },
            {
                Header: '五',
                accessor: 'col5',
            },
        ], []
    )

    const {
        getTableProps,
        getTableBodyProps,
        headerGroups,
        rows,
        prepareRow,
    } = useTable({ columns, data });

    const generateOptions = () => {
        if (addClassInfo.startTime === 19) {
            return (
                <option>1h</option>
            )
        }
        else if (addClassInfo.startTime === 18) {
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
    }

    const handleSubmit = (e) => {
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
                location: {
                    x: 0,
                    y: 0,
                    name: location
                }
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
                setShowModal(false);
            });
    }

    return (
        <div className='ClassTableContent'>
            <table {...getTableProps()}   class="table">
                <thead class="head">
                    {headerGroups.map(headerGroup => (
                        <tr {...headerGroup.getHeaderGroupProps()} >
                            {headerGroup.headers.map(column => (
                                <th class="headBlocks"
                                    {...column.getHeaderProps()}

                                >
                                    {column.render('Header')}
                                </th>
                            ))}
                        </tr>
                    ))}
                </thead>
                <tbody {...getTableBodyProps()}>
                    {rows.map(row => {
                        prepareRow(row)
                        return (
                            <tr {...row.getRowProps()}>
                                {row.cells.map(cell => {
                                    return (
                                        <td class="time"
                                            {...cell.getCellProps()}
                                            style={{

                                                padding: '10px',
                                                border: 'solid 1px gray',
                                                background: 'white',
                                            }}
                                        >
                                            {cell.render('Cell')}
                                        </td>
                                    )
                                })}
                            </tr>
                        )
                    })}
                </tbody>
            </table>
            <Modal show={showModal} onHide={() => setShowModal(false)} backdrop="static">
                <Modal.Header closeButton>
                    <Modal.Title>添加课程</Modal.Title>
                    {/* `周${addClassInfo.day}，${addClassInfo.startTime}-${addClassInfo.startTime + 1}：`*/}
                </Modal.Header>
                <Form onSubmit={handleSubmit}>
                    <Modal.Body>
                        <Form.Control size="sm" type="text" placeholder="课程名称"
                            onChange={({ target: { value } }) => {
                                if (value !== "") {
                                    setNewName(value)
                                }
                            }}
                        />
                        <p>
                            开始周
                            <NumberPicker defaultValue={null} step={1} max={20} min={1} onChange={(value) => {
                                if (value !== null && value >= 1 && value <= 20) {
                                    setStartWeek(value);
                                    // console.log(`开始周：${value}`);
                                }
                            }}
                                style={{
                                    width: "10ex",
                                }}
                            />
                        </p>
                        <p>
                            结束周
                            <NumberPicker defaultValue={null} step={1} max={20} min={startWeek} onChange={(value) => {
                                if (value !== null && value >= 1 && value <= 20) {
                                    setEndWeek(value);
                                    // console.log(`结束周：${value}`);
                                }
                            }}
                                style={{
                                    width: "10ex",
                                }}
                            />
                        </p>
                        <p>
                            考试周
                            <NumberPicker defaultValue={null} step={1} max={20} min={endWeek + 1} onChange={(value) => {
                                if (value !== null && value >= 1 && value <= 20) {
                                    setTestWeek(value);
                                    // console.log(`考试周：${value}`);
                                }
                            }}
                                style={{
                                    width: "10ex",
                                }}
                            />
                        </p>
                        <p>
                            上课时间
                        </p>
                        <p>
                            星期
                            <NumberPicker defaultValue={null} step={1} max={5} min={1} onChange={(value) => {
                                if (value !== null && value >= 1 && value <= 5) {
                                    setClassDay(value);
                                    // console.log(`星期：${value}`);
                                }
                            }}
                                style={{
                                    width: "10ex",
                                }}
                            />
                        </p>
                        <p>
                            <NumberPicker defaultValue={null} step={1} max={20} min={8} onChange={(value) => {
                                if (value !== null && value >= 8 && value <= 20) {
                                    setClassTime(value);
                                    // console.log(`${value}点`);
                                }
                            }}
                                style={{
                                    width: "10ex",
                                }}
                            />
                            点
                        </p>
                        <p>
                            时长
                            <Form.Select size="sm"
                                controlId="exampleForm.duration"
                                style={{
                                    width: "10ex",
                                }}
                                onChange={({ target: { value } }) => {
                                    setClassDuration(parseInt(value[0]));
                                }}
                            >
                                {generateOptions()}
                            </Form.Select>
                        </p>
                        <p>
                            <Form.Control size="sm" type="text" placeholder="上课地点"
                                controlId="exampleForm.location"
                                onChange={({ target: { value } }) => {
                                    if (value !== "") {
                                        setLocation(value)
                                    }
                                }}
                            />
                        </p>
                        <p>
                            参与学生：
                            {studentList.map((name, index) => (
                                <div key={`${name}`} className="mb-3">
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
                        <Button variant="secondary" onClick={() => setShowModal(false)}>
                            Close
                        </Button>
                        <Button variant="primary" type="submit">
                            Save Changes
                        </Button>
                    </Modal.Footer>
                </Form>
            </Modal>
        </div >
    )
}
