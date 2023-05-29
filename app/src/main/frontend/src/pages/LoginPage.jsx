import React, { useState, useEffect, useRef } from 'react'
import BIRDS from 'vanta/dist/vanta.birds.min';
import axios from 'axios';
import './LoginPage.css';
import { Form, Button } from 'react-bootstrap';
import RegisterPage from './RegisterPage';

const LoginPage = () => {
    const [resText, setResText] = useState("");
    const [vantaEffect, setVantaEffect] = useState(null)
    const myRef = useRef(null)
    const [showRegisterPage, setShowRegisterPage] = useState(false);
    const [userName, setUserName] = useState("");
    const [password, setPassword] = useState("");
    const [userNameValid, setUserNameValid] = useState(false);
    const [passwordValid, setPasswordValid] = useState(false);

    useEffect(() => {
        if (!vantaEffect) {
            setVantaEffect(BIRDS({
                el: myRef.current,
                mouseControls: true,
                touchControls: true,
                gyroControls: false,
                minHeight: 200.00,
                minWidth: 200.00,
                scale: 1.00,
                scaleMobile: 1.00,
                backgroundColor: 0xffffff,
                color1: 0x796400,
                color2: 0xcf8e03,
                birdSize: 2.40,
                wingSpan: 29.00,
                quantity: 3.00,
                backgroundAlpha: 0.00
            }))
        }
        return () => {
            if (vantaEffect) vantaEffect.destroy()
        }
    }, [vantaEffect])

    const handleSubmit = (e) => {
        e.preventDefault();
        let jsonData = {
            id: userName,
            password: password
        };
        axios.post("http://" + window.location.hostname + ":8888/login", jsonData)
            .then(function(response) {
                if (response.data.isValid) {
                    const prefix = "http://" + window.location.host;
                    setResText("登陆成功");
                    if (response.data.isAdmin) {
                        window.open(`${prefix}/admin?userName=${userName}`);
                    }
                    else {
                        window.open(`${prefix}/student?userName=${userName}`);
                    }
                }
                else {
                    setResText("登陆失败");
                }
            })
            .catch(function(error) {
                console.log(error);
            });
    };

    if (!showRegisterPage) {
        return (
            <div className="AllPage" ref={myRef}>
                <div className="LoginPageContent">
                    <div id="SMS"><h1>学生日程管理系统</h1></div>
                    <Form noValidate onSubmit={handleSubmit} validated={userNameValid && passwordValid}>
                        <Form.Group className="mb-3" controlId="formBasicEmail">
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
                        </Form.Group>
                        <Form.Group className="mb-3" controlId="formBasicPassword">
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
                        </Form.Group>
                        <div className="buttonArea">
                            <Button onClick={() => setShowRegisterPage(true)}
                                id="startButton" className="loginButton">
                                <span className="shadow"></span>
                                <span className="edge"></span>
                                <span className="front text">注册</span>
                            </Button>
                            <Button disabled={!(passwordValid && userNameValid)}
                                type="submit" id="startButton" className="loginButton">
                                <span className="shadow"></span>
                                <span className="edge"></span>
                                <span className="front text">登录</span>
                            </Button>
                        </div>
                        <div id="loginFail">{resText}</div>
                    </Form>
                </div>
            </div>
        );
    } else {
        return (
            <div className="AllPage" ref={myRef}>
                <RegisterPage setShowRegisterPage={setShowRegisterPage} />
            </div>
        );
    }
};

export default LoginPage;
