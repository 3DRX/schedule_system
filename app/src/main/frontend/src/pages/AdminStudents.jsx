import React, { useEffect, useState } from "react";
import { Table } from 'antd';
import NavBar from "../components/NavBar";
import axios from "axios";
import Button from 'react-bootstrap/Button';
import { useLocation } from "react-router-dom";

const columns = [
    {
        title: '用户名',
        dataIndex: 'name',
        key: 'name',
        render: (text) => <a>{text}</a>,
    },
    {
        title: '操作',
        dataIndex: 'action',
        key: 'action',
    }
];

const AdminStudents = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const [data, setData] = useState([]);

    useEffect(() => {
        axios.get("http://"
            + window.location.hostname
            + ":8888/studentList")
            .then((response) => {
                let tableData = [];
                response.data.forEach((element, index) => {
                    tableData.push({
                        key: index,
                        name: element,
                        action: <Button variant="danger"
                            onClick={() => {
                                axios.post("http://"
                                    + window.location.hostname
                                    + ":8888/deleteStudent"
                                    + "?id=" + element)
                                    .then((_) => {
                                    })
                                    .catch((error) => {
                                        console.log(error);
                                    });
                            }}
                        > 删除</Button>
                    });
                });
                setData(tableData);
            })
            .catch((error) => {
                console.log(error);
            });
    }, []);

    return (
        <>
            <NavBar isAdmin="true" userName={userName} />
            <h1>用户管理</h1>
            <Table columns={columns} dataSource={data} />
        </>
    );
};

export default AdminStudents;
