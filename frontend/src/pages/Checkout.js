import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import { useCookies } from 'react-cookie';
import axios from "axios";
import { useNavigate } from 'react-router-dom';

// Components
import GeneralNavbar from '../components/GeneralNavbar';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button';

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'

const coordinates = [
    [40.629284, -8.659071],
    [40.628410, -8.658471],
    [40.631085, -8.656079],
    [40.634257, -8.659592],
    [40.634151, -8.657157],
    [40.630144, -8.660702],
    [40.629613, -8.651812],
    [40.632802, -8.659764],
    [40.634786, -8.658187],
    [40.634612, -8.657347]
]

let endpoint_orders = "api/orders";

function onlyNumbers(str) {
    return /^[0-9]+$/.test(str);
}

function Checkout() {

    const [deliveryAddress, setDeliveryAddress] = useState("");
    const [orderDetails, setOrderDetails] = useState("");
    const [phoneNumber, setPhoneNumber] = useState("");
    const [cookies, setCookie] = useCookies(['logged_user', 'token']);

    let headers = { "headers": { "Authorization": "Bearer " + cookies.token } };
    let navigate = useNavigate();

    function validateOrderForm() {
        const validAddress = deliveryAddress.length > 15;
        const validPhone = onlyNumbers(phoneNumber) && phoneNumber.length == 9 && phoneNumber[0] == "9";
        return [validAddress, validPhone];
    }

    function processOrder() {

        let validation = validateOrderForm();
        if (!validation[0]) { // bad address
            document.getElementById("orderAddressWarning").style.display = 'block';
        }
        else { document.getElementById("orderAddressWarning").style.display = 'none'; }

        if (!validation[1]) { // bad phone number
            document.getElementById("orderPhoneWarning").style.display = 'block';
        }
        else { document.getElementById("orderPhoneWarning").style.display = 'none'; }

        if (validation[0] && validation[1]) {

            let coords = coordinates[Math.floor(Math.random() * 10)];
            let order = {
                "deliveryAddress": deliveryAddress,
                "deliveryLat": coords[0],
                "deliveryLong": coords[1],
                "orderDetails": orderDetails,
                "phone": phoneNumber
            }

            let url = process.env.REACT_APP_BACKEND_URL + endpoint_orders;

            axios.post(url, order, headers)
                .then((response) => {
                    console.log(response);
                    if (response.status == 200) {
                        let orderId = response.data.id;
                        navigate('/follow_order/' + orderId);
                    }
                }).catch(function (error) {
                    alert("Oops! Something went wrong with your order. Please try again!")
                    navigate('/store/products');
                });
        }
    }

    return (
        <>

            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>CHECKOUT</h1>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                <Row>
                    <Col sm={4}>
                        <Link to="/cart">
                            <FontAwesomeIcon icon={faArrowLeft} style={{ fontSize: '30px', textDecoration: 'none', color: '#06113C' }} />
                        </Link>
                    </Col>
                    <Col sm={8}>

                        <Form style={{ marginTop: '3%' }}>
                            <Form.Group>
                                <Form.Label htmlFor="orderDeliveryAddress" className="inp">
                                    <Form.Control type="text" id="orderDeliveryAddress" placeholder="&nbsp;" onChange={(e) => { setDeliveryAddress(e.target.value) }} />
                                    <span className="label">Delivery Address</span>
                                    <span className="focus-bg"></span>
                                </Form.Label>
                                <Form.Text className="text-muted" id="orderAddressWarning" style={{ display: 'none' }}>
                                    Your address is incomplete.
                                </Form.Text>
                            </Form.Group>
                            <Form.Group>
                                <Form.Label htmlFor="orderDetails" className="inp">
                                    <Form.Control type="textarea" rows="4" id="orderDetails" placeholder="&nbsp;" onChange={(e) => { setOrderDetails(e.target.value) }} />
                                    <span className="label">Order Details</span>
                                    <span className="focus-bg"></span>
                                </Form.Label>
                            </Form.Group>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="orderPhoneNumber" className="inp">
                                    <Form.Control type="text" id="orderPhoneNumber" placeholder="&nbsp;" onChange={(e) => { setPhoneNumber(e.target.value) }} />
                                    <span className="label">Phone Number</span>
                                    <span className="focus-bg"></span>
                                </Form.Label>
                                <Form.Text className="text-muted" id="orderPhoneWarning" style={{ display: 'none' }}>
                                    Your phone number is invalid.
                                </Form.Text>
                            </Form.Group>
                            <div className="col text-center">
                                <Button variant="primary" style={{ marginTop: '3%', align: 'center' }} onClick={() => { processOrder(); }} >
                                    Submit Order
                                </Button>
                            </div>
                        </Form>

                    </Col>
                </Row >
            </Container >

        </>);

}

export default Checkout