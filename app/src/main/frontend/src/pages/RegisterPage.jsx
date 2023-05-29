import React, { useState } from "react";
import axios from 'axios';
import { Form, Button } from 'react-bootstrap';

const RegisterPage = () => {
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [userNameValid, setUserNameValid] = useState(false);
    const [passwordValid, setPasswordValid] = useState(false);

    const handleRegister = (e) => {
        e.preventDefault();
        if (userNameValid && passwordValid) {
            axios.post("http://" + window.location.hostname + ":8888/register", {
                id: userName,
                password: password,
                isAdmin: false
            })
                .then((_) => {
                })
                .catch((error) => {
                    console.log(error);
                })
                .finally(() => {
                    window.open("http://" + window.location.host, "_self");
                });
        }
    };

    return (
        <>
            <h1>用户注册</h1>
            <Form noValidate onSubmit={handleRegister} validated={userNameValid && passwordValid}>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <Form.Label>用户名</Form.Label>
                    <Form.Control
                        required
                        type="text"
                        placeholder="只能包含汉字、字母、数字、下划线"
                        defaultValue=""
                        isInvalid={!userNameValid}
                        onChange={({ target: { value } }) => {
                            if (value.match(/^[\u4e00-\u9fa5_a-zA-Z0-9]+$/)) {
                                setUserName(value);
                                setUserNameValid(true);
                            }
                            else {
                                setUserName("");
                                setUserNameValid(false);
                            }
                        }}
                    />
                    <Form.Control.Feedback type="invalid">
                        用户名只能包含汉字、字母、数字、下划线
                    </Form.Control.Feedback>
                </Form.Group>
                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <Form.Label>密码</Form.Label>
                    <Form.Control
                        required
                        type="password"
                        placeholder="只能包含字母、数字"
                        defaultValue=""
                        isInvalid={!passwordValid}
                        onChange={({ target: { value } }) => {
                            if (value.match(/^[a-zA-Z0-9]+$/)) {
                                setPassword(value);
                                setPasswordValid(true);
                            }
                            else {
                                setPassword("");
                                setPasswordValid(false);
                            }
                        }}
                    />
                    <Form.Control.Feedback type="invalid">
                        密码只能包含字母、数字
                    </Form.Control.Feedback>
                </Form.Group>
                <Button variant="primary" type="submit" disabled={!(passwordValid && userNameValid)}>确认注册</Button>
            </Form>
        </>
    );
};

export default RegisterPage;
