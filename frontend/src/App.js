// React
import React from 'react';
import { BrowserRouter, Routes, Route } from "react-router-dom";
// Bootstrap
import 'bootstrap/dist/css/bootstrap.min.css';
// Pages
import Homepage from './pages/Homepage';
import Staff from './pages/Staff';
import Tasks from './pages/Tasks';
import Riders from './pages/Riders';
import Stores from './pages/Stores';
import RiderProfile from './pages/RiderProfile';
import Account from './pages/Account';
import ChooseStore from './pages/ChooseStore';
import StoreInfo from './pages/StoreInfo';
import StoreProducts from './pages/StoreProducts';
import Login from './pages/Login';

export let staff = [
  { id: 1, name: "Afonso Campos", rating: 5, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 10, workZone: 'A' },
  { id: 2, name: "Diana Siso", rating: 4, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 1, workZone: 'B' },
  { id: 3, name: "Isabel Rosário", rating: 2, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 2, workZone: 'D' },
  { id: 4, name: "Miguel Ferreira", rating: 3, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 2, workZone: 'C' },
  { id: 5, name: "Dinis Lei", rating: 5, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 9, workZone: 'B' },
  { id: 6, name: "Diogo Monteiro", rating: 4, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 8, workZone: 'D' },
  { id: 7, name: "Camila Fonseca", rating: 4, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 4, workZone: 'B' },
  { id: 8, name: "Lucius Vinicius", rating: 5, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 4, workZone: 'A' },
  { id: 9, name: "Tomé Carvalho", rating: 1, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 5, workZone: 'C' },
  { id: 10, name: "Rodrigo Lima", rating: 3, img: 'https://cdn-icons-png.flaticon.com/512/147/147144.png', time: 2, workZone: 'A' },
]

let wine_img = "https://www.creativefabrica.com/wp-content/uploads/2019/08/Bottle-of-wine-580x386.jpg"

export let products = [
  { id: 1, name: "Wine 1", types: ["red"], img: wine_img, price: 19.99, alcohol: 13, available: true},
  { id: 2, name: "Wine 2", types: ["red"], img: wine_img, price: 15.99, alcohol: 11, available: true},
  { id: 3, name: "Wine 3", types: ["dry"], img: wine_img, price: 17.99, alcohol: 12, available: true},
  { id: 4, name: "Wine 4", types: ["rose"], img: wine_img, price: 10.99, alcohol: 12.5, available: false},
  { id: 5, name: "Wine 5", types: ["sparkling", "rose"], img: wine_img, price: 9.99, alcohol: 13.5, available: true},
  { id: 6, name: "Wine 6", types: ["rose"], img: wine_img, price: 20.99, alcohol: 9, available: true},
  { id: 7, name: "Wine 7", types: ["white", "sparkling"], img: wine_img, price: 11.99, alcohol: 10.5, available: true},
  { id: 8, name: "Wine 8", types: ["red"], img: wine_img, price: 13.99, alcohol: 12.5, available: true}
]

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Homepage />}></Route>
        <Route path="/stores" element={<Stores />}></Route>
        <Route path="/account" element={<Account />}></Route>
        <Route path="/choose_store" element={<ChooseStore />}></Route>
        <Route path="/store/:id/products" element={<StoreProducts />}></Route>
        <Route path="/store/:id/info" element={<StoreInfo />}></Route>
        <Route path="/login" element={<Login />}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
