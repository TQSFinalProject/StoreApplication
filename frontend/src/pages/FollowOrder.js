import React, { useState, useEffect } from 'react';
import { Link } from "react-router-dom";
import { useCookies } from 'react-cookie';
import axios from "axios";
import { useParams } from 'react-router-dom';

// Components
import GeneralNavbar from '../components/GeneralNavbar';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'

const endpoint_orders = "api/orders/";

function FollowOrder() {

    let { orderId } = useParams();
    const [cookies, setCookie] = useCookies(['logged_user', 'token']);
    let headers = { "headers": { "Authorization": "Bearer " + cookies.token } };

    const [orderStatus, setOrderStatus] = useState("");
    const [orderDetails, setOrderDetails] = useState("");
    const [riderName, setRiderName] = useState("");
    const [estimatedDeliveryTime, setEstimatedDeliveryTime] = useState("");

    function fetchData() {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_orders + orderId, headers).then((response) => {
            console.log(response.data);
            setOrderStatus(response.data.order.order.orderStatus);
            setOrderDetails(response.data.order.order.orderDetails);
            let rider = response.data.rider;
            if (rider != null) {
                setRiderName(rider.firstName + " " + rider.lastName);
            }
            else {
                setRiderName("[Unassigned]");
            }
            setEstimatedDeliveryTime(response.data.order.order.estimatedDeliveryTime);
        });
    }

    useEffect(() => {

        fetchData(); // initial fetch
        const intervalId = setInterval(() => {
            fetchData(); // fetch again every 5 seconds
        }, 5000)

        return () => clearInterval(intervalId);

    }, []);

    return (
        <>

            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>FOLLOW ORDER</h1>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                <Row>
                    <Col sm={4}>
                        <Link to="/order_history">
                            <FontAwesomeIcon icon={faArrowLeft} style={{ fontSize: '30px', textDecoration: 'none', color: '#06113C' }} />
                        </Link>
                    </Col>
                    <Col sm={8}>
                        <Container className='informationSheet' style={{ width: '50vw' }}>

                            <Row>
                                <Col className='align-self-center'>
                                    <p style={{ marginLeft: '5%' }}>
                                        <strong>Order Status: </strong>{orderStatus}<br />
                                        <strong>Order Details: </strong>{orderDetails}<br />
                                        <strong>Rider Name: </strong>{riderName}<br />
                                        <strong>Estimated Delivery Time: </strong>{estimatedDeliveryTime}<br />
                                    </p>
                                </Col>
                            </Row>

                        </Container>
                    </Col>
                </Row >
            </Container >

        </>);

}

export default FollowOrder