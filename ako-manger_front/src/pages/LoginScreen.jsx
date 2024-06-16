import React, { useState } from "react";
import "../css/LoginScreen.css";
import Ako_sit from "../pages/assets/ako-sit.png";
import { useNavigate } from "react-router-dom";
import HeaderComponent from "./components/HeaderComponent";

const LoginScreen = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const formData = new FormData();
      formData.append("username", username || "");
      formData.append("password", password || "");

      const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        body: formData,
      });

      if (response.ok) {
        const authHeader = response.headers.get("Authorization");
        if (authHeader) {
          // 토큰 저장
          localStorage.setItem("authToken", authHeader);
          console.log("로그인 성공!");
          // 사용자 이름도 저장
        localStorage.setItem("username", username);
          // 필요한 경우 다른 페이지로 이동
          navigate("/");
        } else {
          console.error("Authorization 헤더가 없습니다.");
        }
      } else {
        console.error("로그인 실패");
        alert("아이디 또는 비밀번호가 올바르지 않습니다.");
      }
    } catch (error) {
      console.error("로그인 요청 중 오류 발생:", error);
      alert("로그인 중 오류가 발생했습니다. 다시 시도해주세요.");
    }
  };

  const gotoSign = () => {
    navigate("/signup_first");
  };

  return (
    <div className="login-container">
      <HeaderComponent />
      <div className="login-wrap">
        <h1 className="login-title">Hello, A-Ko!</h1>
        <img className="home-image" src={Ako_sit} alt="LoginScreen" />
        <form className="login-form" onSubmit={handleSubmit}>
          <input
            className="login-input"
            type="text"
            placeholder="아이디"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <input
            className="login-input"
            type="password"
            placeholder="비밀번호"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button className="login-button" type="submit">
            로그인
          </button>
          <div className="login-options">
            <span className="login-option" onClick={gotoSign}>
              회원가입
            </span>
            <span className="login-option">아이디찾기</span>
            <span className="login-option">비밀번호 찾기</span>
          </div>
          <button className="login-button-kakao" type="submit">
            카카오 로그인
          </button>
        </form>
      </div>
    </div>
  );
};

export default LoginScreen;
