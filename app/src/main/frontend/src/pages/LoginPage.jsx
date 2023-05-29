import React, { useState } from "react";
import axios from 'axios';
import './LoginPage.css';
import { Button } from 'react-bootstrap';

const LoginPage = () => {
    const [resText, setResText] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        const form = e.target;
        const formData = new FormData(form);
        const jsonData = Object.fromEntries(formData.entries());
        const userName = formData.getAll("id")[0];
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

    return (
        <div>
            <div className="AllPage">
                <div className="LoginPageContent">
                    <div className="userProfilePhotoArea">
                    </div>
                    <form onSubmit={handleSubmit}>
                        <div id="SMS"><h1>学生日程管理系统</h1></div>
                        <div className="inputArea">
                            <div className="account">
                                <input type="text" name="id" bindinput="EmailInput" placeholder="请输入账号"
                                    placeholder-style="font-size: 1.2rem;" />
                            </div>
                            <div className="password">
                                <input type="password" name="password" bindinput="passwordInput"
                                    placeholder=" 请输入密码" placeholder-style="font-size: 1.2rem;" />
                            </div>
                        </div>
                        <div className="buttonArea">
                            <Button onClick={() => {
                                window.open("http://" + window.location.host + "/register", "_self");
                            }} id="startButton" className="loginButton">
                                <span className="shadow"></span>
                                <span className="edge" id="registerEdge"></span>
                                <span className="front text" id="registerFront">注册</span>
                            </Button>
                            <Button type="submit" id="startButton" className="loginButton">
                                <span className="shadow"></span>
                                <span className="edge"></span>
                                <span className="front text">登录</span>
                            </Button>

                        </div>
                        <div id="loginFail">{resText}</div>
                    </form>
                </div>
            </div>
        </div>
    );
};

export default LoginPage;
