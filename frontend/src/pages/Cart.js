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
import Button from 'react-bootstrap/Button'
import Toast from 'react-bootstrap/Toast'

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'

// functions
import { typeToColor, getWineTypes, addWineToCart } from './StoreProducts';

const endpoint_cart = "api/cart";
const endpoint_wines = "api/wines/";

function Cart() {

    const [cookies, setCookie] = useCookies(['logged_user', 'token'])
    const [cart, setCart] = useState([]);
    const [cartWines, setCartWines] = useState([]);

    let headers = { "headers": { "Authorization": "Bearer " + cookies.token } };
    let navigate = useNavigate();

    useEffect(() => {
        axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_cart, headers).then((response) => {
            setCart(response.data);
        });
    }, []);

    useEffect(() => {

        for (const cart_item in cart) {
            axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_wines + cart_item, headers).then((response) => {
                let wine = response.data;
                setCartWines(cartWines => [...cartWines, wine]);
            });
        }

    }, [cart]);

    return (
        <>
            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>YOUR CART</h1>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                <Row>
                    <Col sm={4}>
                        <Link to="/store/products">
                            <FontAwesomeIcon icon={faArrowLeft} style={{ fontSize: '30px', textDecoration: 'none', color: '#06113C' }} />
                        </Link>
                    </Col>
                    <Col sm={8}>
                        <Container className='informationSheet'>
                            {cartWines.length == 0 ?
                                <>
                                    <Row className="align-items-center">
                                        <h5>You haven't ordered anything yet.</h5>
                                    </Row>
                                </>
                                :
                                cartWines.map((callbackfn, idx) => (

                                    <Row className="align-items-center">
                                        <Col style={{ display: 'flex', justifyContent: 'center' }}>
                                            <h1>{cart[cartWines[idx].id]}x</h1>
                                        </Col>
                                        <Col>
                                            <Toast key={"product_key" + cartWines[idx].id} style={{ margin: '1%', width: '30vw' }} className="employeeCard">
                                                <Toast.Header closeButton={false}>
                                                    <Container>
                                                        <Row>
                                                            <Col style={{ display: 'flex', justifyContent: 'left' }}>
                                                                <strong className="me-auto">{cartWines[idx].name} </strong>
                                                            </Col>
                                                            <Col style={{ display: 'flex', justifyContent: 'right' }}>
                                                                {getWineTypes(cartWines[idx].types).map((callbackfn, idx2) => (
                                                                    <span key={"product_type_key" + idx2} className="badge" style={{ backgroundColor: typeToColor(getWineTypes(cartWines[idx].types)[idx2]), margin: "1%" }}>{getWineTypes(cartWines[idx].types)[idx2]}</span>
                                                                ))}
                                                            </Col>
                                                        </Row>
                                                    </Container>
                                                </Toast.Header>
                                                <Toast.Body>
                                                    <Container>
                                                        <Row>
                                                            <Col className='align-self-center col-xs-1' align='center'>
                                                                {"Alcohol: " + cartWines[idx].alcohol + "%"}<br />
                                                                {"Price: " + cartWines[idx].price + "£"}
                                                            </Col>
                                                            <Col className='align-self-center col-xs-1' align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                                                                <img src={cartWines[idx].img} className="rounded mr-2" alt="Product Pic" style={{ height: '50px' }}></img>
                                                            </Col>
                                                        </Row>
                                                    </Container>
                                                </Toast.Body>
                                            </Toast>
                                        </Col>
                                    </Row>

                                ))
                            }
                            {cartWines.length == 0 ?
                                <></>
                                :
                                <Row className="row justify-content-md-center">
                                    <Button style={{ width: '250px', marginTop: '5%' }} size="lg" onClick={() => { navigate('/checkout') }}>Finish Up Your Order</Button>
                                </Row>
                            }
                        </Container>


                    </Col>
                </Row >
            </Container >

        </>);
}

export default Cart