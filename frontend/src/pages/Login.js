import React from 'react'
import { Link, useParams, useNavigate } from "react-router-dom";

// Components
import GeneralNavbar from '../components/GeneralNavbar';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Button from 'react-bootstrap/Button'
import { Form } from 'react-bootstrap';

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'

// CSS
import SearchBar from '../components/css/SearchBar.css'

function Login() {
    return (
        <>
            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>LOGIN</h1>
            </Container>

            <Container>
                <Row>
                    <Col className='col-xs-1' align='center'>
                        <h4 style={{ marginTop: '5%' }}>SIGN IN</h4>
                        <Form style={{ marginTop: '3%' }}>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="searchAssignedRider" className="inp">
                                    <Form.Control type="text" id="searchAssignedRider" placeholder="&nbsp;" />
                                    <span className="label">Username</span>
                                    <span className="focus-bg"></span>
                                </Form.Label>
                            </Form.Group>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="searchStore" className="inp">
                                    <Form.Control type="password" id="searchStore" placeholder="&nbsp;" />
                                    <span className="label">Password</span>
                                    <span className="focus-bg"></span>
                                </Form.Label>
                            </Form.Group>
                            <Button variant="primary" type="submit" style={{ marginTop: '3%' }}>
                                Submit
                            </Button>
                        </Form>
                    </Col>

                    <Col className='col-xs-1' align='center'>
                        <h4 style={{ marginTop: '5%' }}>SIGN UP</h4>
                        <Form style={{ marginTop: '3%' }}>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="searchAssignedRider" className="inp">
                                    <Form.Control type="text" id="searchAssignedRider" placeholder="&nbsp;" />
                                    <span className="label">Username</span>
                                    <span className="focus-bg"></span>
                                </Form.Label>
                            </Form.Group>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="searchAssignedRider" className="inp">
                                    <Form.Control type="text" id="searchAssignedRider" placeholder="&nbsp;" />
                                    <span className="label">E-mail</span>
                                    <span className="focus-bg"></span>
                                </Form.Label>
                            </Form.Group>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="searchStore" className="inp">
                                    <Form.Control type="password" id="searchStore" placeholder="&nbsp;" />
                                    <span className="label">Password</span>
                                    <span className="focus-bg"></span>
                                </Form.Label>
                            </Form.Group>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="searchStore" className="inp">
                                    <Form.Control type="password" id="searchStore" placeholder="&nbsp;" />
                                    <span className="label">Confirm Password</span>
                                    <span className="focus-bg"></span>
                                </Form.Label>
                            </Form.Group>
                            <Button variant="primary" type="submit" style={{ marginTop: '3%' }}>
                                Submit
                            </Button>
                        </Form>
                    </Col>
                </Row>
            </Container>

        </>);
}

export default Login