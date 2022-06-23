import React, { useState, useEffect } from 'react';
import { Link, useNavigate } from "react-router-dom";
import { useCookies } from 'react-cookie';
import axios from "axios";

// Components
import GeneralNavbar from '../components/GeneralNavbar';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Toast from 'react-bootstrap/Toast'
import Button from 'react-bootstrap/Button'

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'
import { faBarsProgress } from '@fortawesome/free-solid-svg-icons'

const endpoint_orders = "api/orders";

function OrderHistory() {

    const [cookies, setCookie] = useCookies(['logged_user', 'token']);
    let headers = { "headers": { "Authorization": "Bearer " + cookies.token } };
    let navigate = useNavigate();

    const [orders, setOrders] = useState([]);

    useEffect(() => {

        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_orders, headers).then((response) => {
            console.log(response.data);
            setOrders(response.data);
        });

    }, []);


    return (
        <>

            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>ORDER HISTORY</h1>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                <Row>
                    <Col sm={4}>
                        <Link to="/account">
                            <FontAwesomeIcon icon={faArrowLeft} style={{ fontSize: '30px', textDecoration: 'none', color: '#06113C' }} />
                        </Link>
                    </Col>
                    <Col sm={8}>

                        <Row className="d-flex justify-content-center">

                            {orders.map((callbackfn, idx) => (

                                <Toast key={"product_key" + orders[idx].id} style={{ margin: '1%', width: '20vw' }} className="employeeCard">
                                    <Toast.Header closeButton={false}>
                                        <Container>
                                            <Row>
                                                <Col style={{ display: 'flex', justifyContent: 'left' }}>
                                                    <strong className="me-auto">Order #{orders[idx].id} </strong>
                                                    {orders[idx].order.orderStatus != "complete" ?
                                                        <Button onClick={() => { navigate("/follow_order/" + orders[idx].id) }}><FontAwesomeIcon icon={faBarsProgress} /></Button>
                                                        :
                                                        <></>
                                                    }
                                                </Col>
                                            </Row>
                                        </Container>
                                    </Toast.Header>
                                    <Toast.Body>
                                        <Container>
                                            <Row>
                                                <p style={{ marginLeft: '5%' }}>
                                                    <strong>Order Status: </strong>{orders[idx].order.orderStatus}<br />
                                                    <strong>Order Details: </strong>{orders[idx].order.orderDetails}<br />
                                                    <strong>Estimated Delivery Time: </strong>{orders[idx].order.estimatedDeliveryTime}<br />
                                                </p>
                                            </Row>
                                        </Container>
                                    </Toast.Body>
                                </Toast>

                            ))}

                        </Row>

                    </Col>
                </Row >
            </Container >

        </>);

}

export default OrderHistory