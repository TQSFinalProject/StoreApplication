import React, { useState } from 'react';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Toast from 'react-bootstrap/Toast'
import Dropdown from 'react-bootstrap/Dropdown'
import Button from 'react-bootstrap/Button'

// Components
import GeneralNavbar from '../components/GeneralNavbar';

// Product Data
import { products } from "../App"

function dynamicSort(property) {
  var sortOrder = 1;
  if (property[0] === "-") {
      sortOrder = -1;
      property = property.substr(1);
  }
  return function (a, b) {
      var result = (a[property] < b[property]) ? -1 : (a[property] > b[property]) ? 1 : 0;
      return result * sortOrder;
  }
}

function StoreProducts() {

  let local_products = [...products]

  let [orderedProducts, setOrderedProducts] = useState(orderedProducts);

  function productList() {
    return orderedProducts
  }

  function checkBoxFoolery() {

    let local_staff = [...products]

    let r = document.getElementById('checkboxR').checked == true
    let w = document.getElementById('checkboxW').checked == true
    let rose = document.getElementById('checkboxRose').checked == true
    let s = document.getElementById('checkboxS').checked == true


    // else {
    //     local_staff = [...staff]
    // }

  }


  return (
    <>
      <GeneralNavbar />

      <Container>
        <h1 style={{ marginTop: '5%' }}>AVAILABLE PRODUCTS</h1>
      </Container>

      <Container style={{ marginTop: '2%' }}>
        <Row>
          <Col sm={4}>

            <p><strong>Filters:</strong></p>

            <Dropdown className='filterDropdown'>
              <Dropdown.Toggle id="dropdown-basic">
                Price
              </Dropdown.Toggle>
              <Dropdown.Menu>
                <Dropdown.Item>1-5</Dropdown.Item>
                <Dropdown.Item>5-1</Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>

            <Dropdown className='filterDropdown'>
              <Dropdown.Toggle id="dropdown-basic">
                Alcohol
              </Dropdown.Toggle>
              <Dropdown.Menu>
                <Dropdown.Item>A-Z</Dropdown.Item>
                <Dropdown.Item>Z-A</Dropdown.Item>
              </Dropdown.Menu>
            </Dropdown>

            <div className='checkboxGroup' style={{ verticalAlign: 'middle', marginTop: '3%' }}>
              <p>Wine Type:</p>
              <label className="container">
                <input id='checkboxR' type="checkbox" onChange={checkBoxFoolery} />
                &nbsp; Red
              </label>
              <label className="container">
                <input id='checkboxW' type="checkbox" onChange={checkBoxFoolery} />
                &nbsp; White
              </label>
              <label className="container">
                <input id='checkboxRose' type="checkbox" onChange={checkBoxFoolery} />
                &nbsp; Rose
              </label>
              <label className="container">
                <input id='checkboxS' type="checkbox" onChange={checkBoxFoolery} />
                &nbsp; Sparkling
              </label>
            </div>

          </Col>
          {/* <Col sm={8}>
            <Row className="d-flex justify-content-center">
              {staffList().map((callbackfn, idx) => (
                <Toast key={"key" + staffList()[idx].id} style={{ margin: '1%', width: '20vw' }} className="employeeCard">
                  <Toast.Header closeButton={false}>
                    <strong className="me-auto">Employee #{staffList()[idx].id} </strong>
                  </Toast.Header>
                  <Toast.Body>
                    <Container>
                      <Row>
                        <Col className='align-self-center col-xs-1' align='center'>
                          {staffList()[idx].name}<br />
                          <StarRating rating={staffList()[idx].rating} />
                        </Col>
                        <Col className='align-self-center col-xs-1' align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                          <img src={staffList()[idx].img} className="rounded mr-2" alt="Employee Pic" style={{ height: '50px' }}></img>                                                </Col>
                        <Col className='align-self-center col-xs-1' align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                          <Button onClick={() => { redirectUserPage(staffList()[idx].id) }}><i className="fa fa-user"></i></Button>
                        </Col>
                      </Row>
                    </Container>
                  </Toast.Body>
                </Toast>
              ))}
            </Row>
            <Row className="d-flex justify-content-center">
              <Pagination pageNumber={Math.ceil(local_staff.length / 6)} parentCallback={handleCallback} />
            </Row>
          </Col> */}
        </Row>
      </Container>
    </>
  )
}

export default StoreProducts