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
    const stores = ["Wine Store 1","Wine Store 2","Wine Store 3","Wine Store 4"]

    return (
        <>
            <GeneralNavbar />
            <Title title="List of Stores" subtitle="Get to know our partner stores"/>
            <Container className="storeContainer">
                {stores.map((title, idx) => (
                    <Row key={idx} className="row justify-content-center">
                        <Card08
                            bgPhoto="https://picsum.photos/740/420/?random"
                            preTitle="November 2017"
                            tag="Wine"
                            cta="View more"
                            title={title}
                            pageToGo={"/store/"+(idx+1)+"/info"}
                        />
                    </Row>
                ))}
            </Container>
        </>);
}

export default Stores;