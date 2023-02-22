import React, { useState, useEffect } from 'react';
import './App.css';
import axios from 'axios';


const LoginPage = () => {
    const validateUserInfo = () => {
        axios.post('http://localhost:8080/login', {
            id: '001',
            password: 'password'
        })
            .then(function(response) {
                console.log(response);
            })
            .catch(function(error) {
                console.log(error);
            });
    };

    useEffect(() => {
        validateUserInfo();
    }, []);

    return <h1>Hello</h1>
}

function App() {
    return (
        <div className="App">
            <LoginPage />
        </div>
    );
}

export default App;
