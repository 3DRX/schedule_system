import React from 'react'
import NavBar from '../components/NavBar'
import 'bootstrap/dist/css/bootstrap.min.css';

function AdminPage() {
    return (
        <div>
            <NavBar isAdmin="true" />
        </div>
    )
}

export default AdminPage
