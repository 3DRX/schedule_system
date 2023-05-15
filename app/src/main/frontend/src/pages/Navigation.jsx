import React from "react";
import { useLocation } from "react-router-dom";
import "./Navigation.css";

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
            <div id="navigateContent">

                <div id="controllerBox">
                    <input type="text"/>
                </div>
                <div id="mapBox">
                    <div id="Map">
                        {/*在这个div里显示路径 */}

                    </div>

                </div>


                
            </div>
        </>
    )
};

export default Navigation;
