import React, { useState } from "react";
import axios from 'axios';
import { Form, Button } from 'react-bootstrap';
import "./RegisterPage.css";

const RegisterPage = ({ setShowRegisterPage }) => {
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
                    setShowRegisterPage(false);
                });
        }
    };

    return (
        <div className="registerPageContent">
            <div id="registerTitle"><h1>用户注册</h1></div>
            <Form noValidate onSubmit={handleRegister} validated={userNameValid && passwordValid}>
                <Form.Group className="mb-3" controlId="formBasicEmail">
                    <div className="inputArea fixedHeight">
                        <div className="registerAccount">
                            <Form.Control
                                className="registerAccountInput"
                                required
                                type="text"
                                placeholder="用户名"
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
                            <Form.Control.Feedback type="invalid" className="warning">
                                只能包含汉字、字母、数字、下划线
                            </Form.Control.Feedback>
                        </div>
                    </div>
                </Form.Group>

                <Form.Group className="mb-3" controlId="formBasicPassword">
                    <div className="inputArea noMargin fixedHeight">
                        <div className="registerAccount">
                            <Form.Control
                                className="registerAccountInput"
                                required
                                type="password"
                                placeholder="密码"
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
                            <Form.Control.Feedback type="invalid" className="warning">
                                只能包含字母、数字
                            </Form.Control.Feedback>
                        </div>
                    </div>
                </Form.Group>
                <div className="registerButtonArea">
                    <Button variant="secondary"
                        id="startButton"
                        className="loginButton"
                        onClick={() => {
                            setShowRegisterPage(false);
                        }} >
                        <span className="shadow"></span>
                        <span className="edge"></span>
                        <span className="front text">取消</span>
                    </Button>
                    <Button variant="primary" type="submit" disabled={!(passwordValid && userNameValid)} id="startButton" className="loginButton">
                        <span className="shadow"></span>
                        <span className="edge"></span>
                        <span className="front text">确认注册</span>
                    </Button>
                </div>
            </Form>
        </div>
    );
};

export default RegisterPage;
