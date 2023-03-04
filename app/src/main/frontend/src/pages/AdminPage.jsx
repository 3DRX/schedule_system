import React from 'react'
import { useLocation } from 'react-router-dom';
import NavBar from '../components/NavBar'

function AdminPage() {
    const query = new URLSearchParams(useLocation().search);
    const userName = query.get("userName");

    return (
        <div>
            <NavBar isAdmin="true" userName={userName} />
            <h1>Welcome, {userName}.</h1>
        </div>
    )
}

export default AdminPage
