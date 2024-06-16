import React, { useState } from "react";
import Ako_sit from "../pages/assets/ako-sit.png";
import "../css/SignUp_first.css";
import { useNavigate } from "react-router-dom";
import HeaderComponent from "../pages/components/HeaderComponent";

const SignUp_first = () => {
  const navigate = useNavigate();

  const [userId, setUserId] = useState("");
  const [password, setPassword] = useState("");
  const [confirmPassword, setConfirmPassword] = useState("");
  const [isSignUpSuccess, setIsSignUpSuccess] = useState(false);
  const [error, setError] = useState("");

  const handleSubmit = (e) => {
    e.preventDefault();
    if (password !== confirmPassword) {
      setError("비밀번호가 일치하지 않습니다.");
      return;
    }
    setError("");
    setIsSignUpSuccess(true);
    navigate("/signup", { state: { userId, password } });
  };

  return (
    <div className="signup-container">
      <HeaderComponent />
      <div className="signup-wrap">
        <h1>안녕 A-Ko Mentor는 처음이지?</h1>
        <img className="home-image" src={Ako_sit} alt="LoginScreen" />
        {isSignUpSuccess && (
          <div className="signup-success-overlay">
            <div className="signup-success-message">
              <p>회원가입 성공!</p>
              <p>추가 정보를 입력하러 가시겠습니까?</p>
              <button
                className="add-info-button"
                onClick={() => navigate("/login")}
              >
                No
              </button>
              <button
                className="add-info-button"
                onClick={() => navigate("/signup")}
              >
                Yes!
              </button>
            </div>
          </div>
        )}
        {!isSignUpSuccess && (
          <form onSubmit={handleSubmit} className="signup-form">
            <input
              type="text"
              placeholder="학번"
              value={userId}
              onChange={(e) => setUserId(e.target.value)}
              className="input-field"
            />
            <input
              type="password"
              placeholder="비밀번호"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="input-field"
            />
            <input
              type="password"
              placeholder="비밀번호 확인"
              value={confirmPassword}
              onChange={(e) => setConfirmPassword(e.target.value)}
              className="input-field"
            />
            {error && <p className="error-message">{error}</p>}
            <button type="submit" className="signup-button">
              회원가입
            </button>
          </form>
        )}
      </div>
    </div>
  );
};

export default SignUp_first;
