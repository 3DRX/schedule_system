import React, { useState } from "react";
import axios from 'axios';
import './LoginPage.css';
import { Form, Button } from 'react-bootstrap';

const LoginPage = () => {
    const [resText, setResText] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        const form = e.target;
        const formData = new FormData(form);
        const jsonData = Object.fromEntries(formData.entries());
        const userName = formData.getAll("id")[0];
        // 仍然不能在其他设备上登陆
        axios.post("http://" + window.location.hostname + ":8888/login", jsonData)
            .then(function (response) {
                // console.log(response);
                // console.log(response.data.isValid)
                if (response.data.isValid) {
                    // console.log("登陆成功");
                    const prefix = "http://" + window.location.host;
                    if (response.data.isAdmin) {
                        window.open(`${prefix}/admin?userName=${userName}`);
                    }
                    else {
                        window.open(`${prefix}/student?userName=${userName}`);
                    }
                }
                else {
                    //console.log("登陆失败");
                    setResText("登陆失败");
                }
            })
            .catch(function (error) {
                console.log(error);
            });
    };

    return (
        <div>
            <view className="AllPage">
                <view className="LoginPageContent">
                    <view className="userProfilePhotoArea">
                    </view>
                    <form onSubmit={handleSubmit}>
                        <view className="inputArea">
                            <view className="account">
                                <input type="text" name="id" bindinput="EmailInput" placeholder="请输入账号"
                                    placeholder-style="font-size: 1.2rem;" />
                            </view>
                            <view className="password">
                                <input type="password" name="password" bindinput="passwordInput"
                                    placeholder=" 请输入密码" placeholder-style="font-size: 1.2rem;" />
                            </view>
                        </view>
                        <view className="buttonArea">
                            <Button type="submit" id="startButton" className="loginButton">
                                <span className="shadow"></span>
                                <span className="edge"></span>
                                <span className="front text">登录</span>
                            </Button>
                        </view>
                        <div id="loginFail">{resText}</div>
                    </form>
                </view>
            </view>
        </div>
    )
}

export default LoginPage;
