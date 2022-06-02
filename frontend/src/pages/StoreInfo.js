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

function StoreInfo() {
  return (
    <>
      <GeneralNavbar />

      <Container>
        <h1 style={{ marginTop: '5%' }}>STORE INFO</h1>
      </Container>

      <Container style={{ marginTop: '2%' }}>
        <Row>
          <Col sm={4}>
            <Link to="/">
              <FontAwesomeIcon icon={faArrowLeft} style={{ fontSize: '30px', textDecoration: 'none', color: '#06113C' }} />
            </Link>
          </Col>
          <Col sm={8}>
            <Container className='informationSheet'>
              <Row>
                <Col className='align-self-center'>
                  <h3 style={{ marginLeft: '5%' }}>WINE DELIGHT</h3>
                  <p style={{ marginLeft: '5%' }}>
                    <strong>Store ID: </strong>3<br />
                    [More information will be displayed here...]
                  </p>
                </Col>
                <Col className="col-xs-1" align="center">
                  <img src="https://png.pngtree.com/element_our/20190603/ourlarge/pngtree-shop-store-cartoon-illustration-image_1433180.jpg" className="rounded mr-2" alt="Store Pic" style={{ height: '200px' }}></img>
                </Col>
              </Row>
            </Container>
          </Col>
        </Row>
      </Container>
    </>);
}

export default StoreInfo