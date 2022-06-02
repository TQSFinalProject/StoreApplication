import React from 'react'
import { Link, useParams, useNavigate } from "react-router-dom";

// Components
import GeneralNavbar from '../components/GeneralNavbar';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Button from 'react-bootstrap/Button'

// Font Awesome
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'

function Account() {
    return (
        <>
            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>ACCOUNT</h1>
            </Container>

            <Container style={{ marginTop: '2%' }}>
                <Row>
                    <Col sm={4}>
                        <Link to="/choose_store">
                            <FontAwesomeIcon icon={faArrowLeft} style={{ fontSize: '30px', textDecoration: 'none', color: '#06113C' }} />
                        </Link>
                    </Col>
                    <Col sm={8}>
                        <Container className='informationSheet'>
                            <Row>
                                <Col className='align-self-center'>
                                    <h3 style={{ marginLeft: '5%' }}>JOHN SMITH</h3>
                                    <p style={{ marginLeft: '5%' }}>
                                        <strong>User ID: </strong>34<br />
                                        {/* <strong>Name: </strong> John Smith<br /> */}
                                        <strong>E-mail: </strong> john.smith@ua.pt<br />
                                    </p>
                                </Col>
                                <Col className="col-xs-1" align="center">
                                    <img src="https://cdn-icons-png.flaticon.com/512/147/147144.png" className="rounded mr-2" alt="Employee Pic" style={{ height: '200px' }}></img>
                                </Col>
                            </Row>
                            <Row className="d-flex justify-content-center">
                                <Link to='/' style={{ marginTop: '5%', width: '20%' }}>
                                    <Button>Your orders</Button>
                                </Link>
                            </Row>
                        </Container>
                    </Col>
                </Row>
            </Container>
        </>);
}

export default Account