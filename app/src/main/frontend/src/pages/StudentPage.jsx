import React from 'react'
import { useLocation } from 'react-router-dom';
import NavBar from '../components/NavBar'

function StudentPage() {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");

    return (
        <div>
            <NavBar isAdmin="false" userName={userName} />
            <h1>welcome, {userName}.</h1>
        </div>
    )
}

export default StudentPage
