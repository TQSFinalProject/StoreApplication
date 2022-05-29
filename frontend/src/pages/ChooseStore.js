import React from 'react'

import Card08 from '../components/Card08';
// Bootstrap
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Container from 'react-bootstrap/Container'

// Components
import GeneralNavbar from '../components/GeneralNavbar';
import Title from '../components/Title';

function ChooseStore() {
    const stores = ["Wine Store 1","Wine Store 2","Wine Store 3","Wine Store 4"]
  return (
    <>
        <GeneralNavbar />
        <Title title="List of Stores" subtitle="Choose the store you want to buy from"/>
        <Container className="storeContainer">
            {stores.map((title, idx) => (
                <Row key={idx} className="row justify-content-center">
                    <Card08
                        bgPhoto="https://picsum.photos/740/420/?random"
                        preTitle="November 2017"
                        tag="Wine"
                        cta="Buy from this store"
                        title={title}
                        pageToGo={"/store/"+(idx+1)+"/products"}
                    />
                </Row>
            ))}
        </Container>
    </>);
}

export default ChooseStore