import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import defaultAkoImage from "../../Images/기본형아코얼굴_(designed by 박세리,원혜림).png";
import helpicon from "../../Images/help_outline.png";
import settingsicon from "../../Images/settings.png";
import appsicon from "../../Images/apps.png";
import { BsSendPlus } from "react-icons/bs";

const HeaderComponent = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [hover, setHover] = useState(false); // hover 상태를 추적하는 상태 변수

  const gotoChat = () => {
    navigate("/chatting");
  };

  const gotoHome = () => {
    navigate("/");
  };

  const styles = {
    header: {
      display: "flex",
      justifyContent: "space-between",
      alignItems: "center",
      padding: "10px 20px",
      height: "60px",
      width: "100%",
      backgroundColor: "rgba(244, 224, 200, 0.3)",
    },
    logo: {
      paddingTop: "25px",
      display: "flex",
      alignItems: "center",
      height: "100%",
    },
    logoText: {
      fontFamily: "SeoulHangang CM",
      fontSize: "20px",
      color: "#67574b",
      marginLeft: "10px",
      lineHeight: "25px",
    },
    setting: {
      display: "flex",
      alignItems: "center",
    },
    settingIcon: {
      marginRight: "10px",
    },
    userProfile: {
      width: "40px",
      height: "40px",
      borderRadius: "50%",
      backgroundColor: "#f4d48d",
      textAlign: "center",
      display: "flex",
      justifyContent: "center",
      alignItems: "center",
      fontSize: "14px",
      marginLeft: "10px",
    },
    AKo: {
      height: "40px",
      marginLeft: "10px",
    },
    startChatButton: {
      backgroundColor: "#A08D7D",
      color: "#fff",
      border: "none",
      borderRadius: "20px",
      padding: "10px 20px",
      cursor: "pointer",
      transition: "opacity 0.3s",
    },
    startChatButtonHover: {
      opacity: 0.8,
    },
  };

  return (
    <div className="header" style={styles.header}>
      <div className="logo" style={{ ...styles.logo, cursor: "pointer" }}>
        <img
          className="AKo"
          src={defaultAkoImage}
          style={styles.AKo}
          alt="A-Ko"
          onClick={gotoHome}
        />
        <span
          style={{ ...styles.logoText, cursor: "pointer" }}
          onClick={gotoHome}
        >
          A-Ko mentor
        </span>
      </div>
      {location.pathname !== "/chatting" && (
        <button
          style={
            hover
              ? { ...styles.startChatButton, ...styles.startChatButtonHover }
              : styles.startChatButton
          }
          onClick={gotoChat}
          onMouseEnter={() => setHover(true)}
          onMouseLeave={() => setHover(false)}
        >
          start chat <BsSendPlus />
        </button>
      )}
      <div className="setting" style={styles.setting}>
        <img src={helpicon} alt="helpicon" style={styles.settingIcon} />
        <img src={settingsicon} alt="settingsicon" style={styles.settingIcon} />
        <img src={appsicon} alt="appsicon" style={styles.settingIcon} />
        <div className="userprofile" style={styles.userProfile}>
          USER
        </div>
      </div>
    </div>
  );
};

export default HeaderComponent;
