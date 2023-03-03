import React from 'react'
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Link } from "react-router-dom";
import "./NavBar.css";
import 'bootstrap/dist/css/bootstrap.min.css';

function NavBar({ isAdmin }) {
    const prefix = "http://" + window.location.host;

    let links;
    if (isAdmin === "true") {
        links = (
            <>
                <Link className='navBarItem' to="/admin/course">课程</Link>
                <Link className='navBarItem' to="/admin/activities">课外活动</Link>
                <Link className='navBarItem' to="/admin/others">其他</Link>
            </>
        )
    }
    else {
        links = (
            <>
                <Link className='navBarItem' to="/student/course">课程</Link>
                <Link className='navBarItem' to="/student/activities">课外活动</Link>
                <Link className='navBarItem' to="/student/others">其他</Link>
            </>
        )
    }

    return (
        <Navbar collapseOnSelect bg="dark" variant="dark" expand="lg">
            <Container>
                <Navbar.Brand href={prefix}>退出登录</Navbar.Brand>
                <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                <Navbar.Collapse id="responsive-navbar-nav">
                    <Navbar.Text>
                    </Navbar.Text>
                    <Nav className="me-auto">
                        {links}
                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    )
}

export default NavBar
