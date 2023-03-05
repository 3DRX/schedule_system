import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import { useTable } from "react-table";
import TableCell from "../components/TableCell";
import Button from 'react-bootstrap/Button';
import Modal from 'react-bootstrap/Modal';
import Form from 'react-bootstrap/Form';
import { NumberPicker } from "react-widgets";

export default function ClassTable({ isAdmin, week }) {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [showModal, setShowModal] = useState(false);
    const [addClassInfo, setAddClassInfo] = useState({});

    const data = React.useMemo(
        () => {
            let res = [];
            for (let i = 8; i <= 19; i++) {
                let cell = { col0: i + '-' + (i + 1) };
                for (let j = 1; j <= 5; j++) {
                    let key = "col" + j;
                    cell[key] = <TableCell setAddClassInfo={setAddClassInfo} setShowModal={setShowModal} startTime={i} week={week} day={j} userName={userName} isAdmin={isAdmin} />;
                }
                res.push(cell);
            }
            return res;
        }, [week]
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

    const handleClose = () => setShowModal(false);

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
            <Modal show={showModal} onHide={handleClose} backdrop="static">
                <Modal.Header closeButton>
                    <Modal.Title>添加课程</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {`在第${addClassInfo.week}周，周${addClassInfo.day}，${addClassInfo.startTime}-${addClassInfo.startTime + 1}添加课程：`}
                    <p>
                        <Form.Control size="sm" type="text" placeholder="课程名称" />
                    </p>
                    <p>
                        开始周
                        <NumberPicker defaultValue={1} step={1} max={20} min={1} onChange={(value) => {
                            console.log(`开始周：${value}`);
                        }}
                            style={{
                                width: "10ex",
                            }}
                        />
                    </p>
                    <p>
                        结束周
                        <NumberPicker defaultValue={1} step={1} max={20} min={1} onChange={(value) => {
                            console.log(`结束周：${value}`);
                        }}
                            style={{
                                width: "10ex",
                            }}
                        />
                    </p>
                    <p>
                        考试周
                        <NumberPicker defaultValue={1} step={1} max={20} min={1} onChange={(value) => {
                            console.log(`考试周：${value}`);
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
                            console.log(`星期${value}`);
                        }}
                            style={{
                                width: "10ex",
                            }}
                        />
                    </p>
                    <p>
                        <NumberPicker defaultValue={8} step={1} max={20} min={8} onChange={(value) => {
                            console.log(`${value}点`);
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
                            style={{
                                width: "10ex",
                            }}
                        >
                            <option>1h</option>
                            <option>2h</option>
                            <option>3h</option>
                        </Form.Select>
                    </p>
                    <p>
                        考试时间
                    </p>
                    <p>
                        星期
                        <NumberPicker defaultValue={1} step={1} max={5} min={1} onChange={(value) => {
                            console.log(`星期${value}`);
                        }}
                            style={{
                                width: "10ex",
                            }}
                        />
                    </p>
                    <p>
                        <NumberPicker defaultValue={8} step={1} max={20} min={8} onChange={(value) => {
                            console.log(`${value}点`);
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
                            style={{
                                width: "10ex",
                            }}
                        >
                            <option>1h</option>
                            <option>2h</option>
                            <option>3h</option>
                        </Form.Select>
                    </p>
                    <p>
                        <Form.Control size="sm" type="text" placeholder="上课地点" />
                    </p>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleClose}>
                        Close
                    </Button>
                    <Button variant="primary" onClick={handleClose}>
                        Save Changes
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    )
}
