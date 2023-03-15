import React from "react";
import Carousel from 'react-bootstrap/Carousel';
import CellDetail from "./CellDetail";

const CellWithCourse = ({ courses, refresh, setRefresh, isAdmin }) => {

    if (courses.length === 1) {
        return <CellDetail course={courses[0]} refresh={refresh} setRefresh={setRefresh} isAdmin={isAdmin} />
    }
    else {
        return (
            <Carousel variant="dark" interval={null}>
                {courses.map((course) => {
                    return (
                        <Carousel.Item>
                            < CellDetail course={course} refresh={refresh} setRefresh={setRefresh} isAdmin={isAdmin} />
                        </Carousel.Item>
                    )
                })}
            </Carousel>
        )
    }
}

export default CellWithCourse;
