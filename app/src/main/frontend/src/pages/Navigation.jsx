import React, { useRef, useState } from "react";
import { useLocation } from "react-router-dom";
import { Form, Button } from 'react-bootstrap';
import axios from "axios";

const Navigation = () => {
    const query = new URLSearchParams(useLocation().search);
    const courseName = query.get("courseName");
    const location = query.get("location");

    const [locationInput, setLocationInput] = useState("");
    const canvasRef = useRef(null);

    const drawLine = (ctx, x1, y1, x2, y2) => {
        ctx.moveTo(x1, y1);
        ctx.lineTo(x2, y2);
        ctx.stroke();
    };

    const onPaint = (path) => {
        if (path === null) {
            return;
        }
        const canvas = canvasRef.current;
        const ctx = canvas.getContext("2d");
        ctx.strokeStyle = "red";
        ctx.lineWidth = 15;
        ctx.lineCap = "round";
        path.forEach((point, index) => {
            // console.log(point);
            if (index !== 0) {
                drawLine(ctx, path[index - 1].x, path[index - 1].y, point.x, point.y);
            }
        });
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        const jsonData = {
            start: locationInput,
            end: location,
        };
        axios.post("http://" + window.location.hostname + ":8888/navigate", jsonData)
            .then((response) => {
                const path = JSON.parse(response.request.response);
                onPaint(path);
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
            <canvas
                id="map"
                ref={canvasRef}
                style={{
                    width: "100%",
                    height: "100%",
                    border: "1px solid #000",
                }}
            >
                您的浏览器不支持canvas，请更换浏览器
            </canvas>
        </>
    )
};

export default Navigation;
