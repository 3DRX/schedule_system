import React from "react";
import { useLocation } from "react-router-dom";

const Navigation = () => {
    const query = new URLSearchParams(useLocation().search);
    const courseName = query.get("courseName");
    const location = query.get("location");
    const x = query.get("x");
    const y = query.get("y");

    return (
        <>
            <h1>Navigation</h1>
            <p>Go to {courseName}, at {location}, ({x}, {y}).</p>
        </>
    )
};

export default Navigation;
