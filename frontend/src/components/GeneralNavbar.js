import React from 'react';

// CSS
import './css/GeneralNavbar.css'

function GeneralNavbar() {
    return <>
        <nav className="navMenu">
            <a href="/">Home</a>
            <a href="/stores">Stores</a>
            <a href="/account">Account</a>
            {/* <a href="/riders">Riders</a>
            <a href="/stores">Stores</a> */}
            <div className="dot"></div>
        </nav>
    </>;
}

export default GeneralNavbar;