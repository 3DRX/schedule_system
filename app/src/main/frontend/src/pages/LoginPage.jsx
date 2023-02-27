import React, { useState } from "react";
import axios from 'axios';
import './LoginPage.css';

const LoginPage = () => {
    const [resText, setResText] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        const form = e.target;
        const formData = new FormData(form);
        const jsonData = Object.fromEntries(formData.entries());
        // 仍然不能在其他设备上登陆
        axios.post("http://" + window.location.hostname + ":8080/login", jsonData)
            .then(function(response) {
                // console.log(response);
                // console.log(response.data.isValid)
                if (response.data.isValid) {
                    // console.log("登陆成功");
                    const prefix = "http://" + window.location.host;
                    if (response.data.isAdmin) {
                        window.open(prefix + "/admin", "_self");
                    }
                    else {
                        window.open(prefix + "/student", "_self");
                    }
                }
                else {
                    // console.log("登陆失败");
                    setResText("登陆失败");
                }
            })
            .catch(function(error) {
                console.log(error);
            });
    };

    return (
        <div>
            <view className="AllPage">
                <view className="content">
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
                            <input type="submit" value="登录" bindtap="Login" className="loginButton"></input>
                        </view>
                        <text>{resText}</text>
                    </form>
                </view>
            </view>
        </div>
    )
}

export default LoginPage;
