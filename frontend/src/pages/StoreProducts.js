import React, { useState, useEffect } from 'react';
import { useCookies } from 'react-cookie';
import { useNavigate } from 'react-router-dom';

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
import Pagination from '../components/Pagination';

// Product Data
import { products } from "../App"

// FA
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPlus } from '@fortawesome/free-solid-svg-icons'

// axios
import axios from "axios";

const endpoint_wines = "api/wines";
const endpoint_cart = "api/cart/"

function valuetextPrice(value) {
  return `${value}£`;
}

function valuetextAlcohol(value) {
  return `${value}%`;
}

export function typeToColor(type) {
  let colorMap = {
    "red": "DarkRed",
    "white": "Khaki",
    "rose": "LightCoral",
    "sparkling": "LightBlue",
    "dry": "YellowGreen"
  }

  return colorMap[type]
}

export function getWineTypes(typesString) {
  const typesArray = typesString.split(";");
  return typesArray;
}

function StoreProducts() {

  const [wines, setWines] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [cookies, setCookie] = useCookies(['logged_user', 'token']);
  let headers = { "headers": { "Authorization": "Bearer " + cookies.token } };

  let navigate = useNavigate();

  useEffect(() => {
    axios.get(process.env.REACT_APP_BACKEND_URL + endpoint_wines, headers).then((response) => {
      // console.log(response.data.content);
      setWines(response.data.content);
      setTotalPages(response.data.totalPages);
    });

  }, []);

  let minPrice = Math.min(...wines.map(o => o.price))
  let maxPrice = Math.max(...wines.map(o => o.price))
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

  let minAlcohol = Math.min(...wines.map(o => o.alcohol))
  let maxAlcohol = Math.max(...wines.map(o => o.alcohol))
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

  const [valuePrice, setValuePrice] = React.useState([minPrice, maxPrice]);
  const [valueAlcohol, setValueAlcohol] = React.useState([minAlcohol, maxAlcohol]);

  const handleChangePrice = (event, newValue) => {
    setValuePrice(newValue);
  };

  const handleChangeAlcohol = (event, newValue) => {
    setValueAlcohol(newValue);
  };

  function handleCallback(page) {

    let url = process.env.REACT_APP_BACKEND_URL + endpoint_wines + "?page=" + (page - 1);

    axios.get(url, headers).then((response) => {
      setWines(response.data.content);
    });

  }

  function addWineToCart(wineId) {
    let url = process.env.REACT_APP_BACKEND_URL + endpoint_cart + wineId;
    let headers = { "headers": { "Authorization": "Bearer " + cookies.token } };

    axios.put(url, {}, headers)
      .then((response) => {
        console.log(response)
      });
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
                <input id='checkboxR' type="checkbox" />
                &nbsp; Red
              </label>
              <label className="container">
                <input id='checkboxW' type="checkbox" />
                &nbsp; White
              </label>
              <label className="container">
                <input id='checkboxRose' type="checkbox" />
                &nbsp; Rose
              </label>
              <label className="container">
                <input id='checkboxS' type="checkbox" />
                &nbsp; Sparkling
              </label>
            </div>

          </Col>
          <Col sm={8}>
            <Row className="d-flex justify-content-center">

              {wines.map((callbackfn, idx) => (
                  wines[idx].stock != 0 ?

                    <Toast key={"product_key" + wines[idx].id} style={{ margin: '1%', width: '20vw' }} className="employeeCard">
                      <Toast.Header closeButton={false}>
                        <Container>
                          <Row>
                            <Col style={{ display: 'flex', justifyContent: 'left' }}>
                              <strong className="me-auto">{wines[idx].name} </strong>
                            </Col>
                            <Col style={{ display: 'flex', justifyContent: 'right' }}>
                              {getWineTypes(wines[idx].types).map((callbackfn, idx2) => (
                                <span key={"product_type_key" + idx2} className="badge" style={{ backgroundColor: typeToColor(getWineTypes(wines[idx].types)[idx2]), margin: "1%" }}>{getWineTypes(wines[idx].types)[idx2]}</span>
                              ))}
                            </Col>
                          </Row>
                        </Container>
                      </Toast.Header>
                      <Toast.Body>
                        <Container>
                          <Row>
                            <Col className='align-self-center col-xs-1' align='center'>
                              {"Alcohol: " + wines[idx].alcohol + "%"}<br />
                              {"Price: " + wines[idx].price + "£"}
                            </Col>
                            <Col className='align-self-center col-xs-1' align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                              <img src={wines[idx].img} className="rounded mr-2" alt="Product Pic" style={{ height: '50px' }}></img>                                                </Col>
                            <Col className='align-self-center col-xs-1' align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                              <Button onClick={() => { addWineToCart(wines[idx].id) }}><FontAwesomeIcon icon={faPlus} /></Button>
                            </Col>
                          </Row>
                        </Container>
                      </Toast.Body>
                    </Toast>
                    :
                    <></>
              ))}

            </Row>
            <Row className="d-flex justify-content-center">
              <Pagination pageNumber={totalPages} parentCallback={handleCallback} />
            </Row>
            <Row>
              <Col className='align-self-center col-xs-1' align='center' style={{ marginTop: '3%', marginBottom: '3%' }}>
                <Button size="lg" onClick={() => { navigate('/cart') }}>Go To Cart</Button>
              </Col>
            </Row>
          </Col>
        </Row>
      </Container>
    </>
  )
}

export default StoreProducts