import React, { useState } from "react";
import { useLocation } from "react-router-dom";
import { Form, Button } from 'react-bootstrap';
import axios from "axios";

const Navigation = () => {
    const query = new URLSearchParams(useLocation().search);
    const courseName = query.get("courseName");
    const location = query.get("location");

    const [locationInput, setLocationInput] = useState("");

    const onPaint = (path) => {
        if (path === null) {
            return;
        }
        console.log("onPaint");
        path.forEach((element, index) => {
            console.log(element);
            console.log(index);
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
        </>
    )
};

export default Navigation;
