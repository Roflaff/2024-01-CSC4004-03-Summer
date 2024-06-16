import './App.css';
import ChattingScreen from './pages/ChattingScreen';
import CreditScreen from './pages/CreditScreen';
import LoginScreen from './pages/LoginScreen';
import SignUpScreen from './pages/SignUpScreen';
import Home from './pages/home';
import TimeTable from './pages/components/TimeTable';
import { Routes, Route, BrowserRouter } from 'react-router-dom';
import HeaderComponent from './pages/components/HeaderComponent';
import SignUp_first from './pages/SignUp_first';

import axios from 'axios';
 console.log('User added:')

const addUser = async (userData) => {
  try {
    const response = await axios.post('http://localhost:8080/user/add', userData);
    console.log('User added:', response.data);
  } catch (error) {
    console.error('Error adding user:', error);
  }
};

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="login" element={<LoginScreen />} />
        <Route path="signup" element={<SignUpScreen />} />
         <Route path="signup_first" element={<SignUp_first />} />
        <Route path="chatting" element={<ChattingScreen />} />
        {/* <Route path="header" element={<HeaderComponent />} /> 확인용 - 나중에 지우기 */}
        <Route path="credit" element={<CreditScreen />} />
        <Route path="timetable" element={<TimeTable />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
