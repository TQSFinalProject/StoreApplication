import React, { useState, useEffect } from 'react';
import { useCookies } from 'react-cookie';
import axios from "axios";

const endpoint_cart = "api/cart"

function Cart() {

    const [cookies, setCookie] = useCookies(['logged_user', 'token'])

    useEffect(() => {
        let headers = { "headers": { "Authorization": "Bearer " + cookies.token } };
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_cart, headers).then((response) => {
            console.log(response.data);
            // setWines(response.data.content);
        });

    }, []);

}

export default Cart