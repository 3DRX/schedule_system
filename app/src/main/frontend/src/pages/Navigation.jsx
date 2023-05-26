import React, { useRef, useState } from "react";
import { useLocation } from "react-router-dom";
import NavBar from '../components/NavBar'
import { Form, Button } from 'react-bootstrap';
import mapImage from "../image/BUPT_Map.jpg";
import logo from "../image/logo.png";
import axios from "axios";
import "./Navigation.css";
import { useEffect } from "react";

const Navigation = () => {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");
    const courseName = query.get("courseName");
    const location = query.get("location");
    const isCourse = query.get("isCourse");

    const [locationInput, setLocationInput] = useState("");
    const [scale, setScale] = useState(2);
    const [path, setPath] = useState([]);
    const canvasRef = useRef(null);
    const imgRef = useRef(null);

    useEffect(() => {
        if (path === null || path.length === 0) {
            return;
        }
        const canvas = canvasRef.current;
        const context = canvas.getContext("2d");
        // clear canvas
        canvas.height = canvas.height
        // 👆 somehow this sh*t works
        const img = imgRef.current;
        context.drawImage(img, 0, 0, img.width / scale, img.height / scale);
        context.strokeStyle = "black";
        context.lineWidth = 12;
        context.lineCap = "round";
        context.lineJoin = "round";
        path.forEach((point, index) => {
            console.log(point);
            if (index !== 0) {
                context.lineTo(point.x / scale, point.y / scale);
            } else {
                context.moveTo(point.x / scale, point.y / scale);
            }
        });
        context.stroke();
        context.strokeStyle = "gold";
        context.lineWidth = 8;
        path.forEach((point, index) => {
            if (index !== 0) {
                context.lineTo(point.x / scale, point.y / scale);
            } else {
                context.moveTo(point.x / scale, point.y / scale);
            }
        });
        context.stroke();
    }, [path]);

    const handleSubmit = (e) => {
        e.preventDefault();
        if (isCourse === "true") {
            const jsonData = {
                start: locationInput,
                end: location,
            };
            axios.post("http://" + window.location.hostname + ":8888/navigateToClass", jsonData)
                .then((response) => {
                    const path = JSON.parse(response.request.response);
                    setPath(path);
                })
                .catch((error) => {
                    console.log(error);
                });
        }
        else {
            let locations = [];
            axios.get("http://"
                + window.location.hostname
                + ":8888/getLocations?studentName="
                + userName
                + "&eventName="
                + courseName)
                .then((response) => {
                    // console.log(response);
                    locations = response.data;
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {
                    const jsonData = {
                        start: locationInput,
                        locations: locations
                    }
                    console.log(`jsonData: ${JSON.stringify(jsonData)}`);
                    axios.post("http://" + window.location.hostname + ":8888/navigateToEvents", jsonData)
                        .then((response) => {
                            const path = JSON.parse(response.request.response);
                            setPath(path);
                        })
                        .catch((error) => {
                            console.log(error);
                        });
                });
        }
    }

    const renderHeader = () => {
        if (isCourse === "true") {
            return (
                <>
                    <div id="destination">
                        <div className="format">前往</div>
                        <div> {courseName} </div>
                        <div className="format">,</div>
                        <div className="format">位于</div>
                        <div> {location}. </div>
                    </div>
                </>
            );
        }
        else {
            return (
                <>
                    <div id="destination">
                        <div className="format">临时事务导航</div>
                    </div>
                </>
            );
        }
    };

    return (
        <div id="navigationContent">
            <NavBar isAdmin="false" userName={userName} enabled={true} />
            <div id="titleBox"><div id="navigationTitle" className="card primary"><img id="logo" src={logo} alt=""/></div></div>
            {renderHeader()}
            <div className="center">
                <div id="start" >
                    <div id="map">
                        <img id="mapImg"
                            src={mapImage}
                            ref={imgRef}
                            className="mapImage"
                            alt="mapImage"
                            style={{
                                width: { scale },
                                height: { scale },
                                display: "none",
                            }}
                        />
                        <canvas
                            id="mapImg"
                            ref={canvasRef}
                            width={
                                imgRef.current && path.length !== 0
                                    ? imgRef.current.width / scale
                                    : 0
                            }
                            height={
                                imgRef.current && path.length !== 0
                                    ? imgRef.current.height / scale
                                    : 0
                            }
                        >
                            您的浏览器不支持canvas，请更换浏览器😡
                        </canvas>
                    </div>
                    <div className="center">
                        <Form onSubmit={handleSubmit}>
                            <div id="origin">
                                <Form.Group className="mb-3" controlId="formBasicText">
                                    <Form.Label className="center" id="current">当前位置</Form.Label>
                                    <Form.Control
                                        id="placeInput"
                                        type="text"
                                        placeholder="请输入起点"
                                        onChange={({ target: { value } }) => {
                                            setLocationInput(value)
                                        }}
                                    />
                                </Form.Group>
                            </div>
                            <div id="startButtonBox">
                                <Button type="submit" id="startButton">
                                    <span className="shadow"></span>
                                    <span className="edge"></span>
                                    <span className="front text">出发!</span>
                                </Button>
                            </div>
                        </Form>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Navigation;
