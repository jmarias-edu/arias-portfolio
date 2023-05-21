import React from 'react';
import {BrowserRouter, Route, Routes} from 'react-router-dom';
import Signup from './pages/signup';
import Login from './pages/login';
import Feed from './pages/feed';
import Home from './pages/home';

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route exact={true} path="/" element={<Home />} />
          <Route exact={true} path="/sign-up" element={<Signup />} />
          <Route exact={true} path="/log-in" element={<Login />} />
          <Route exact={true} path="/feed" element={<Feed />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
