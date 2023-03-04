import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import { useTable } from "react-table";
import TableCell from "../components/TableCell";

export default function ClassTable({ isAdmin, week }) {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");

    const data = React.useMemo(
        () => {
            let res = [];
            for (let i = 8; i <= 19; i++) {
                let cell = { col0: i + '-' + (i + 1) };
                for (let j = 1; j <= 5; j++) {
                    let key = "col" + j;
                    cell[key] = <TableCell startTime={i} week={week} day={j} userName={userName} isAdmin={isAdmin} />;
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

    return (
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
    )
}
