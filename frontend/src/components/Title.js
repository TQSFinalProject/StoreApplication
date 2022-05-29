import React from 'react'
import { Container } from 'react-bootstrap'
// Bootstrap
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import '../components/css/Title.css'

function Title({title,subtitle}) {
  return (
    <Container>
        <Row style={{textAlign:'center',marginTop:'5%'}}>
            <h2 className='titleH2'>{title}</h2>
            <p className='titleSubtitle'>{subtitle}</p>

        </Row>
    </Container>
  )
}

export default Title