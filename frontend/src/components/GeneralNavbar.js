import React from 'react';
import { useCookies } from 'react-cookie';
import { useNavigate } from "react-router-dom";

// CSS
import './css/GeneralNavbar.css'

function GeneralNavbar() {

    const [cookies, setCookie] = useCookies(['logged_user', 'token'])
    let navigate = useNavigate();

    function Logout() {
        setCookie('logged_user', '', { path: '/' })
        setCookie('token', '', { path: '/' })
        navigate('/');
    }

    return <>
        <nav className="navMenu">
            <a href="/">Home</a>
            <a href="/stores">Stores</a>
            {cookies.logged_user != undefined && cookies.logged_user != "" ?
                <a href="/account">Account</a>
                :
                <></>
            }
            {cookies.logged_user != undefined && cookies.logged_user != "" ?
                <a href="#" onClick={() => {Logout()}}>Logout</a>
                :
                <a href="/login">Login</a>
            }
            <div className="dot"></div>
        </nav>
    </>;
}

export default GeneralNavbar;