// React
import React from 'react';
// import { Link } from "react-router-dom";
import Card08 from '../components/Card08';
// Bootstrap
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Container from 'react-bootstrap/Container'

// Components
import GeneralNavbar from '../components/GeneralNavbar';
import Title from '../components/Title';

function Stores() {

    return (
        <>
            <GeneralNavbar />
            <Title title="List of Stores" subtitle="Get to know our partner stores"/>
            <Container className="storeContainer">
                <Row className="row justify-content-center">
                    <Card08
                        bgPhoto="https://picsum.photos/740/420/?random"
                        preTitle="November 2017"
                        tag="Wine"
                        cta="View more"
                        title="Wine Store 1"
                    />
                </Row>
                <Row className="row justify-content-center">
                    <Card08
                        bgPhoto="https://picsum.photos/740/420/?random"
                        preTitle="November 2017"
                        tag="Wine"
                        cta="View more"
                        title="Wine Store 2"
                    />
                </Row>
                <Row className="row justify-content-center">
                    <Card08
                        bgPhoto="https://picsum.photos/740/420/?random"
                        preTitle="November 2017"
                        tag="Wine"
                        cta="View more"
                        title="Wine Store 3"
                    />
                </Row>
                <Row className="row justify-content-center">
                    <Card08
                        bgPhoto="https://picsum.photos/740/420/?random"
                        preTitle="November 2017"
                        tag="Wine"
                        cta="View more"
                        title="Wine Store 4"
                    />
                </Row>
            </Container>
        </>);
}

export default Stores;