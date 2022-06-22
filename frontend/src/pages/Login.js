import React, { useState } from 'react'
import { useCookies } from 'react-cookie';
import axios from 'axios'
import { useNavigate } from "react-router-dom";

// Components
import GeneralNavbar from '../components/GeneralNavbar';

// Bootstrap
import Container from 'react-bootstrap/Container'
import Row from 'react-bootstrap/Row'
import Col from 'react-bootstrap/Col'
import Button from 'react-bootstrap/Button'
import { Form } from 'react-bootstrap';

// // Font Awesome
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
// import { faArrowLeft } from '@fortawesome/free-solid-svg-icons'

// CSS
import SearchBar from '../components/css/SearchBar.css'

const registerEndpoint = 'register';
const loginEndpoint = 'authenticate';

// function routeChange(path) { 
//     navigate(path);
// }

// INPUT FIELDS VALIDATION

function isAlphaNumeric(str) {
    let code, i, len

    for (i = 0, len = str.length; i < len; i++) {
        code = str.charCodeAt(i);
        if (!(code > 47 && code < 58) && // numeric (0-9)
            !(code > 64 && code < 91) && // upper alpha (A-Z)
            !(code > 96 && code < 123)) { // lower alpha (a-z)
            return false
        }
    }
    return true
}

function validateUsername(username) {
    if (username.length > 2 // length is 3+ characters
        && username.charAt(0).toLowerCase() != username.charAt(0).toUpperCase() // starts with a letter
        && isAlphaNumeric(username)) // is an alphanumeric string
    {
        return true
    }
    return false
}

function validateEmail(email) {
    // var atSymbol = email.indexOf("@");
    // if (atSymbol < 1) return false;

    // var dot = email.indexOf(".");
    // if (dot <= atSymbol + 2) return false;

    // if (dot === email.length - 1) return false;

    return true;
}

function validatePassword(password) {
    const lower = /[a-z]/
    const digit = /[0-9]/

    if (password.length > 4 // length is 5+ characters
        && lower.test(password) // has at least one lowercase letter
        && digit.test(password)) // has at least one digit
    {
        return true
    }
    return false
}

// LOGIN COMPONENT

function Login() {

    const [cookies, setCookie] = useCookies(['logged_user', 'token'])
    // console.log("logged_user: ", cookies.logged_user)

    const [loginUsername, setLoginUsername] = useState("");
    const [loginPassword, setLoginPassword] = useState("");

    const [regUsername, setRegUsername] = useState("");
    const [regEmail, setRegEmail] = useState("");
    const [regPassword, setRegPassword] = useState("");
    const [regConfPassword, setRegConfPassword] = useState("");

    let navigate = useNavigate();

    function validateRegForm() {
        const validUsername = validateUsername(regUsername);
        const validEmail = validateEmail(regEmail);
        const validPassword = validatePassword(regPassword);
        const equalPasswords = (regPassword === regConfPassword);

        return [validUsername, validEmail, validPassword, equalPasswords];
    }

    function handleRegister() {

        let allGood = true;
        const registerValidation = validateRegForm();

        if (!registerValidation[0]) {
            document.getElementById("regUsernameWarning").style.display = 'block'
            allGood = false;
        }
        else {
            document.getElementById("regUsernameWarning").style.display = 'none'
        }

        if (!registerValidation[1]) {
            document.getElementById("regEmailWarning").style.display = 'block'
            allGood = false;
        }
        else {
            document.getElementById("regEmailWarning").style.display = 'none'
        }

        if (!registerValidation[2]) {
            document.getElementById("regPasswordWarning").style.display = 'block'
            allGood = false;
        }
        else {
            document.getElementById("regPasswordWarning").style.display = 'none'
        }

        if (!registerValidation[3]) {
            document.getElementById("regConfPasswordWarning").style.display = 'block'
            allGood = false;
        }
        else {
            document.getElementById("regConfPasswordWarning").style.display = 'none'
        }

        if (allGood) {

            const newRegister = {
                // TODO: phone number in register form
                // TODO: email in post request
                "name": regUsername,
                "username": regUsername,
                "password": regPassword,
                "phone": 961234567, // TODO: this is not static
                "cart": {} // TODO: hmmmm
            }

            axios.post(process.env.REACT_APP_BACKEND_URL + registerEndpoint, newRegister, {headers:{"Access-Control-Allow-Origin": "*"}})
                .then(function (response) {
                    console.log(response);
                    if (response.data === 'User registered successfully.') {
                        setCookie('logged_user', loginUsername, { path: '/' })
                        setCookie('token', response.data.token, { path: '/' }) // , maxAge: '3600'

                        alert('Registration was successful! Proceed to login :)')
                    }
                    else {
                        alert('There was a problem with your registration. Please try again!')
                    }
                })

        }

    }

    function handleLogin() {

        document.getElementById("loginWarning").style.display = 'none'

        const newLogin = {
            "username": loginUsername,
            "password": loginPassword,
        }

        axios.post(process.env.REACT_APP_BACKEND_URL + loginEndpoint, newLogin)
            .then(function (response) {
                console.log(response.status);

                if (response.status === 200) {
                    setCookie('logged_user', loginUsername, { path: '/' })
                    setCookie('token', response.data.token, { path: '/' }) // , maxAge: '3600'
                    alert('Login was successful :)')
                    navigate('/');
                }
                else {
                    document.getElementById("loginWarning").style.display = 'block'
                }

            })

    }


    return (
        <>
            <GeneralNavbar />

            <Container>
                <h1 style={{ marginTop: '5%' }}>LOGIN</h1>
            </Container>

            <Container>
                <Row>
                    <Col className='col-xs-1' align='center'>
                        <h4 style={{ marginTop: '5%' }}>SIGN IN</h4>
                        <Form style={{ marginTop: '3%' }}>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="loginUsernameField" className="inp">
                                    <Form.Control type="text" id="loginUsernameField" placeholder="&nbsp;" onChange={(e) => { setLoginUsername(e.target.value) }} />
                                    <span className="label">Username</span>
                                    <span className="focus-bg"></span>
                                </Form.Label>
                            </Form.Group>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="loginPasswordField" className="inp">
                                    <Form.Control type="password" id="loginPasswordField" placeholder="&nbsp;" onChange={(e) => { setLoginPassword(e.target.value) }} />
                                    <span className="label">Password</span>
                                    <span className="focus-bg"></span>
                                </Form.Label>
                                <Form.Text className="text-muted" id="loginWarning" style={{ display: 'none' }}>
                                    Your login credentials are incorrect.
                                </Form.Text>
                            </Form.Group>
                            <Button variant="primary" style={{ marginTop: '3%' }} onClick={() => { handleLogin(); }}>
                                Submit
                            </Button>
                        </Form>
                    </Col>

                    <Col className='col-xs-1' align='center'>
                        <h4 style={{ marginTop: '5%' }}>SIGN UP</h4>
                        <Form style={{ marginTop: '3%' }}>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="regUsernameField" className="inp">
                                    <Form.Control type="text" id="regUsernameField" placeholder="&nbsp;" onChange={(e) => { setRegUsername(e.target.value) }} />
                                    <span className="label">Username</span>
                                    <span className="focus-bg"></span>
                                    <Form.Text className="text-muted" id="regUsernameWarning" style={{ display: 'none' }}>
                                        Your username is invalid.
                                    </Form.Text>
                                </Form.Label>
                            </Form.Group>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="regEmailField" className="inp">
                                    <Form.Control type="text" id="regEmailField" placeholder="&nbsp;" onChange={(e) => { setRegEmail(e.target.value) }} />
                                    <span className="label">E-mail</span>
                                    <span className="focus-bg"></span>
                                    <Form.Text className="text-muted" id="regEmailWarning" style={{ display: 'none' }}>
                                        Your e-mail is invalid.
                                    </Form.Text>
                                </Form.Label>
                            </Form.Group>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="regPasswordField" className="inp">
                                    <Form.Control type="password" id="regPasswordField" placeholder="&nbsp;" onChange={(e) => { setRegPassword(e.target.value) }} />
                                    <span className="label">Password</span>
                                    <span className="focus-bg"></span>
                                    <Form.Text className="text-muted" id="regPasswordWarning" style={{ display: 'none' }}>
                                        Your password is invalid.
                                    </Form.Text>
                                </Form.Label>
                            </Form.Group>
                            <Form.Group style={{ width: '20vw' }}>
                                <Form.Label htmlFor="regConfPasswordField" className="inp">
                                    <Form.Control type="password" id="regConfPasswordField" placeholder="&nbsp;" onChange={(e) => { setRegConfPassword(e.target.value) }} />
                                    <span className="label">Confirm Password</span>
                                    <span className="focus-bg"></span>
                                    <Form.Text className="text-muted" id="regConfPasswordWarning" style={{ display: 'none' }}>
                                        Your confirmation password doesn't match your initial password.
                                    </Form.Text>
                                </Form.Label>
                            </Form.Group>
                            <Button variant="primary" style={{ marginTop: '3%' }} onClick={() => { handleRegister(); }}>
                                Submit
                            </Button>
                        </Form>
                    </Col>
                </Row>
            </Container>

        </>);
}

export default Login