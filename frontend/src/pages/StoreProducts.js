import React, { useState, useEffect } from 'react';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Toast from 'react-bootstrap/Toast'
import Dropdown from 'react-bootstrap/Dropdown'
import Button from 'react-bootstrap/Button'

// Material UI
import { Slider } from '@mui/material';
import Typography from '@mui/material/Typography';

// Components
import GeneralNavbar from '../components/GeneralNavbar';

// Product Data
import { products } from "../App"

// FA
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPlus } from '@fortawesome/free-solid-svg-icons'
import { useNavigate } from 'react-router-dom';

// axios
import axios from "axios";

const endpoint_wines = "api/wines";

function valuetextPrice(value) {
  return `${value}£`;
}

function valuetextAlcohol(value) {
  return `${value}%`;
}

function StoreProducts() {

  useEffect(() => {

    // let config = {
    //   headers: {
    //     'Access-Control-Allow-Origin': '*'
    //   }
    // }

    axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_wines, {
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
      }
    }).then((response) => {
      console.log(response.data);
    });

  }, []);

  let navigate = useNavigate();
  let local_products = [...products]
  let minPrice = Math.min(...local_products.map(o => o.price))
  let maxPrice = Math.max(...local_products.map(o => o.price))
  let marksPrice = [
    {
      "value": minPrice,
      "label": minPrice + "£"
    },
    {
      "value": maxPrice,
      "label": maxPrice + "£"
    }
  ]

  let minAlcohol = Math.min(...local_products.map(o => o.alcohol))
  let maxAlcohol = Math.max(...local_products.map(o => o.alcohol))
  let marksAlcohol = [
    {
      "value": minAlcohol,
      "label": minAlcohol + "%"
    },
    {
      "value": maxAlcohol,
      "label": maxAlcohol + "%"
    }
  ]

  function redirectCartPage() {
    navigate('/cart/')
  }

  const [valuePrice, setValuePrice] = React.useState([minPrice, maxPrice]);
  const [valueAlcohol, setValueAlcohol] = React.useState([minAlcohol, maxAlcohol]);

  const handleChangePrice = (event, newValue) => {
    setValuePrice(newValue);
  };

  const handleChangeAlcohol = (event, newValue) => {
    setValueAlcohol(newValue);
  };

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

  function typeToColor(type) {
    let colorMap = {
      "red": "DarkRed",
      "white": "Khaki",
      "rose": "LightCoral",
      "sparkling": "LightBlue",
      "dry": "YellowGreen"
    }

    return colorMap[type]
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

            <Typography id="input-slider" gutterBottom>
              Price
            </Typography>

            <Slider
              getAriaLabel={() => 'Temperature range'}
              value={valuePrice}
              onChange={handleChangePrice}
              valueLabelDisplay="auto"
              getAriaValueText={valuetextPrice}
              max={maxPrice}
              min={minPrice}
              step={0.01}
              marks={marksPrice}
              disableSwap
            />

            <Typography id="input-slider" gutterBottom>
              Alcohol
            </Typography>

            <Slider
              getAriaLabel={() => 'Temperature range'}
              value={valueAlcohol}
              onChange={handleChangeAlcohol}
              valueLabelDisplay="auto"
              getAriaValueText={valuetextAlcohol}
              max={maxAlcohol}
              min={minAlcohol}
              step={0.5}
              marks={marksAlcohol}
              disableSwap
            />

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
          <Col sm={8}>
            <Row className="d-flex justify-content-center">
              {local_products.map((callbackfn, idx) => (
                <Toast key={"product_key" + local_products[idx].id} style={{ margin: '1%', width: '20vw' }} className="employeeCard">
                  <Toast.Header closeButton={false}>
                    <Container>
                      <Row>
                        <Col style={{ display: 'flex', justifyContent: 'left' }}>
                          <strong className="me-auto">{local_products[idx].name} </strong>
                        </Col>
                        <Col style={{ display: 'flex', justifyContent: 'right' }}>
                          {local_products[idx].types.map((callbackfn, idx2) => (
                            <span key={"product_type_key" + idx2} className="badge" style={{ backgroundColor: typeToColor(local_products[idx].types[idx2]), margin: "1%" }}>{local_products[idx].types[idx2]}</span>
                          ))}
                        </Col>
                      </Row>
                    </Container>
                  </Toast.Header>
                  <Toast.Body>
                    <Container>
                      <Row>
                        <Col className='align-self-center col-xs-1' align='center'>
                          {"Alcohol: " + local_products[idx].alcohol + "%"}<br />
                          {"Price: " + local_products[idx].alcohol + "£"}

                        </Col>
                        <Col className='align-self-center col-xs-1' align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                          <img src={local_products[idx].img} className="rounded mr-2" alt="Product Pic" style={{ height: '50px' }}></img>                                                </Col>
                        <Col className='align-self-center col-xs-1' align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                          <Button onClick={() => { }}><FontAwesomeIcon icon={faPlus} /></Button>
                        </Col>
                      </Row>
                    </Container>
                  </Toast.Body>
                </Toast>
              ))}
            </Row>
            <Row>
              <Col className='align-self-center col-xs-1' align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                <Button size="lg" onClick={() => { redirectCartPage() }}>Go To Cart</Button>
              </Col>
            </Row>
          </Col>
        </Row>
      </Container>
    </>
  )
}

export default StoreProducts