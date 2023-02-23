import React, { useState, useEffect } from "react";
import axios from 'axios';

const LoginPage = () => {
    const [resText, setResText] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        const form = e.target;
        const formData = new FormData(form);
        const jsonData = Object.fromEntries(formData.entries());
        axios.post('http://localhost:8080/login', jsonData)
            .then(function(response) {
                // console.log(response);
                // console.log(response.data.isValid)
                if (response.data.isValid) {
                    // console.log("登陆成功");
                    setResText("登陆成功");
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
            <form onSubmit={handleSubmit}>
                <label for="id">用户名</label>
                <input type="id" placeholder="your user name" id="id" name="id" />
                <label for="password">密码</label>
                <input type="password" placeholder="*********" id="password" name="password" />
                <button type="submit">登陆</button>
            </form>
            <h3>{resText}</h3>
        </div>
    )
}

export default LoginPage;
