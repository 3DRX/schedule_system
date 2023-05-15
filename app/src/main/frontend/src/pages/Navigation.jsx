import React, { useRef, useState } from "react";
import { useLocation } from "react-router-dom";
import { Form, Button } from 'react-bootstrap';
import mapImage from "./mapImage.png";
import axios from "axios";
import { useEffect } from "react";

const Navigation = () => {
    const query = new URLSearchParams(useLocation().search);
    const courseName = query.get("courseName");
    const location = query.get("location");

    const [locationInput, setLocationInput] = useState("");
    const [scale, setScale] = useState(2);
    const [path, setPath] = useState([]);
    const canvasRef = useRef(null);
    const imgRef = useRef(null);

    const drawLine = (context, x1, y1, x2, y2) => {
        console.log(context.type, 'drawLine', x1, y1, x2, y2);
        context.beginPath();
        context.moveTo(x1, y1);
        context.lineTo(x2, y2);
        context.stroke();
    };

    useEffect(() => {
        if (path === null || path.length === 0) {
            return;
        }
        const canvas = canvasRef.current;
        const context = canvas.getContext("2d");
        console.log(canvas.type);
        context.strokeStyle = "red";
        context.lineWidth = 15;
        context.lineCap = "round";
        context.lineJoin = "round";
        const img = imgRef.current;
        context.drawImage(img, 0, 0, img.width / scale, img.height / scale);
        path.forEach((point, index) => {
            if (index !== 0) {
                drawLine(
                    context,
                    path[index - 1].x / scale,
                    path[index - 1].y / scale,
                    point.x / scale,
                    point.y / scale
                );
            }
        });
    }, [path]);

    const handleSubmit = (e) => {
        e.preventDefault();
        const jsonData = {
            start: locationInput,
            end: location,
        };
        axios.post("http://" + window.location.hostname + ":8888/navigate", jsonData)
            .then((response) => {
                const path = JSON.parse(response.request.response);
                setPath(path);
            })
            .catch((error) => {
                console.log(error);
            });
    }

    return (
        <>
            <h1>Navigation</h1>
            <p>Go to {courseName}, at {location}.</p>
            <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3" controlId="formBasicText">
                    <Form.Label>起点</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="输入起点"
                        onChange={({ target: { value } }) => {
                            setLocationInput(value)
                        }}
                    />
                </Form.Group>
                <Button variant="primary" type="submit">
                    确认
                </Button>
            </Form>
            <img
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
                id="map"
                ref={canvasRef}
                width={imgRef.current ? imgRef.current.width / scale : 0}
                height={imgRef.current ? imgRef.current.height / scale : 0}
            >
                您的浏览器不支持canvas，请更换浏览器
            </canvas>
        </>
    )
};

export default Navigation;
