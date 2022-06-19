import React from 'react';
import { useCookies } from 'react-cookie';

// CSS
import './css/GeneralNavbar.css'

function GeneralNavbar() {

    const [cookies, setCookie] = useCookies(['logged_user', 'token'])

    return <>
        <nav className="navMenu">
            <a href="/">Home</a>
            <a href="/stores">Stores</a>
            {cookies.logged_user != undefined && cookies.logged_user != "" ?
                <a href="/account">Account</a>
                :
                <></>
            }

            <a href="/login">Login</a>
            <div className="dot"></div>
        </nav>
    </>;
}

export default GeneralNavbar;