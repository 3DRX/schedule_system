import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import { useTable } from "react-table";
import TableCell from "../components/TableCell";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import { NumberPicker } from "react-widgets";

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
                    />;
                }
                res.push(cell);
            }
            return res;
        }, [refresh, week]
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
        if (addClassInfo.startTime == 19) {
            return (
                <option>1h</option>
            )
        }
        else if (addClassInfo.startTime == 18) {
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
        };
        console.log(res);
        setRefresh(!refresh);
        setShowModal(false);
    }

    return (
        <div>
            <table {...getTableProps()} style={{ border: 'solid 1px blue' }}>
                <thead>
                    {headerGroups.map(headerGroup => (
                        <tr {...headerGroup.getHeaderGroupProps()}>
                            {headerGroup.headers.map(column => (
                                <th
                                    {...column.getHeaderProps()}
                                    style={{
                                        borderBottom: 'solid 3px red',
                                        background: 'aliceblue',
                                        color: 'black',
                                        fontWeight: 'bold',
                                    }}
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
                                        <td
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
                    {`周${addClassInfo.day}，${addClassInfo.startTime}-${addClassInfo.startTime + 1}：`}
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={handleSubmit}>
                        <Form.Control size="sm" type="text" placeholder="课程名称"
                            onChange={({ target: { value } }) => {
                                if (value !== "") {
                                    setNewName(value)
                                }
                            }}
                        />
                        <p>
                            开始周
                            <NumberPicker defaultValue={1} step={1} max={20} min={1} onChange={(value) => {
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
                            <NumberPicker defaultValue={null} step={1} max={20} min={startWeek + 1} onChange={(value) => {
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
                            <NumberPicker defaultValue={1} step={1} max={5} min={1} onChange={(value) => {
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
                            <NumberPicker defaultValue={addClassInfo.startTime} step={1} max={20} min={8} onChange={(value) => {
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
                        <Button variant="secondary" onClick={() => setShowModal(false)}>
                            Close
                        </Button>
                        <Button variant="primary" type="submit">
                            Save Changes
                        </Button>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                </Modal.Footer>
            </Modal>
        </div >
    )
}
