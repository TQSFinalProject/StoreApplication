// React
import React from 'react';
import { useCookies } from 'react-cookie';
import { Link } from "react-router-dom";

// Components
import GeneralNavbar from '../components/GeneralNavbar';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'

// CSS
import '../components/css/HomepageTitle.css'

function Homepage() {

    const [cookies, setCookie] = useCookies(['logged_user', 'token'])

    return (
        <>
            <GeneralNavbar />

            <Container>
                <Row style={{ marginTop: '20%' }}>
                    <Col sm={8} style={{ textAlign: "center" }}>
                        <span className='mainTitle'>CHATEAU DU VIN</span>
                        <Row style={{ marginTop: '5%' }}>
                            <span className='presentationText'>Welcome to our store!</span>
                        </Row>
                    </Col>
                    <Col sm={4}>
                        <div style={{ width: '100%', paddingTop: '20%', textAlign: "center" }}>
                            <Link to="/choose_store">
                                <button className="learn-more">
                                    <span className="circle" aria-hidden="true">
                                        <span className="icon arrow"></span>
                                    </span>
                                    <span className="button-text">Start Ordering</span>
                                </button>
                            </Link>
                            {/* {cookies.logged_user != undefined && cookies.logged_user != "" ?
                                <Link to="/choose_store">
                                    <button className="learn-more">
                                        <span className="circle" aria-hidden="true">
                                            <span className="icon arrow"></span>
                                        </span>
                                        <span className="button-text">Start Ordering</span>
                                    </button>
                                </Link>
                                :
                                <Link to="/login">
                                    <button className="learn-more">
                                        <span className="circle" aria-hidden="true">
                                            <span className="icon arrow"></span>
                                        </span>
                                        <span className="button-text">Login</span>
                                    </button>
                                </Link>
                            } */}
                        </div>
                    </Col>
                </Row>
            </Container>
        </>);
}

export default Homepage;