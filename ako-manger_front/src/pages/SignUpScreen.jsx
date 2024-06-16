import React, { useState, useRef, useEffect } from "react";
import "../css/SignUpScreen.css";
import profileImage from "../pages/assets/ako-profile.png";
import HeaderComponent from "./components/HeaderComponent";
import { useNavigate, useLocation } from "react-router-dom";

const SignUpScreen = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { userId: username, password } = location.state || {};

  const [isSubmitted, setIsSubmitted] = useState(false);
  const [showUploadText, setShowUploadText] = useState(false);

  const [formData, setFormData] = useState({
    university: "", // 대학교
    studentId: username || "", // 학번 = 아이디
    name: "", // 이름
    major: "", // 전공
    college: "", // 단과대학
    minor: "", // 부전공
    date_of_birth: "", // 생년월일
    profileImage: null,
    // excelFile: null,
    username: username || "", // 아이디 = 학번
    password: password || "", // 비밀번호
  });

  const inputFileRef_2 = useRef(null);

  const handleFileInputChange = (e) => {
    setFormData({ ...formData, profileImage: e.target.files[0] });
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleStudentIdChange = (e) => {
    const value = e.target.value;
    if (/^\d*$/.test(value) || value === "") {
      setFormData({ ...formData, studentId: value });
    }
  };

  // const handleNameChange = (e) => {
  //   const value = e.target.value;
  //   if (/^[a-zA-Z가-힣\s]*$/.test(value) || value === "") {
  //     setFormData({ ...formData, name: value });
  //   }
  // };

  // const handleMajorChange = (e) => {
  //   const value = e.target.value;
  //   if (/^[a-zA-Z가-힣\s]*$/.test(value) || value === "") {
  //     setFormData({ ...formData, major: value });
  //   }
  // };

  const handleSecondMajorChange = (e) => {
    const value = e.target.value;
    if (/^[a-zA-Z가-힣\s]*$/.test(value) || value === "") {
      setFormData({ ...formData, minor: value });
    }
  };

  const handleStudentBirthChange = (e) => {
    const value = e.target.value;
    if (/^[\d-]*$/.test(value)) {
      setFormData({ ...formData, date_of_birth: value });
    }
  };

  const [errorMessage, setErrorMessage] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      !formData.university ||
      !formData.studentId ||
      !formData.name ||
      !formData.major ||
      !formData.college ||
      !formData.date_of_birth
    ) {
      alert(`모든 필수 입력란을 작성해주세요.`);
      return;
    }

    try {
      const response = await fetch("/User/add", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(formData),
      });

      if (response.ok) {
        setIsSubmitted(true);
        setTimeout(() => {
          navigate("/");
        }, 2000);
      } else {
        const errorData = await response.json();
        alert(`정보 입력 중 오류가 발생했습니다`);
      }
    } catch (error) {
      alert(`정보 입력 중 오류가 발생했습니다`);
    }
    console.log({ formData });
  };

  const nameInputRef = useRef(null);
  // const majorInputRef = useRef(null);
  const minorInputRef = useRef(null);

  useEffect(() => {
    if (nameInputRef.current) {
      nameInputRef.current.value = formData.name;
    }
  }, [formData.name]);

  // useEffect(() => {
  //   if (majorInputRef.current) {
  //     majorInputRef.current.value = formData.major;
  //   }
  // }, [formData.major]);

  useEffect(() => {
    if (minorInputRef.current) {
      minorInputRef.current.value = formData.minor;
    }
  }, [formData.minor]);

  return (
    <div className="signup-container">
      <div className="profile-container">
        <div
          className="profile-image-container"
          onMouseEnter={() => setShowUploadText(true)}
          onMouseLeave={() => setShowUploadText(false)}
          onClick={() => inputFileRef_2.current.click()}
        >
          {showUploadText && (
            <div className="upload-text-overlay">사진업로드하기</div>
          )}
          {formData.profileImage ? (
            <img
              src={URL.createObjectURL(formData.profileImage)}
              alt="Profile"
              className="profile-image"
            />
          ) : (
            <img src={profileImage} alt="Profile" className="profile-image" />
          )}
        </div>
        <div
          className="profile-text"
          onClick={() => inputFileRef_2.current.click()}
        >
          Profile
        </div>
        <input
          type="file"
          accept=".png,.jpg"
          ref={inputFileRef_2}
          style={{ display: "none" }}
          onChange={handleFileInputChange}
        />
      </div>
      <div className="required-text">*필수입력</div>
      <form className="signup-form" onSubmit={handleSubmit}>
        {errorMessage && <div className="error-message">{errorMessage}</div>}
        {isSubmitted && (
          <div className="signup-success-overlay">
            <div className="signup-success-message">
              <p>정보 입력 성공!</p>
              <p>
                <br />
                홈으로 이동...
              </p>
            </div>
          </div>
        )}

        <select
          className="select-input"
          name="university"
          value={formData.university}
          onChange={handleChange}
        >
          <option value="">소속 대학교 *</option>
          <option value="동국대학교 서울캠퍼스">동국대학교 서울캠퍼스</option>
        </select>
        <input
          className="form-input"
          type="text"
          name="studentId"
          placeholder="학번*"
          value={formData.studentId}
          onChange={handleStudentIdChange}
        />
        <input
          className="form-input"
          type="text"
          name="name"
          placeholder="이름*"
          ref={nameInputRef}
          onChange={(e) => handleChange(e)}
        />
        <select
          className="select-input"
          name="college"
          value={formData.college}
          onChange={handleChange}
        >
          <option value="">소속 단과대학 *</option>
          <option value="불교대학">불교대학</option>
          <option value="문과대학">문과대학</option>
          <option value="이과대학">이과대학</option>
          <option value="법과대학">법과대학</option>
          <option value="사회과학대학">사회과학대학</option>
          <option value="경찰사법대학">경찰사법대학</option>
          <option value="경영대학">경영대학</option>
          <option value="바이오시스템대학">바이오시스템대학</option>
          <option value="공과대학">공과대학</option>
          <option value="AI융합대학">AI융합대학</option>
          <option value="사범대학">사범대학</option>
          <option value="예술대학">예술대학</option>
        </select>
        <select
          className="select-input"
          name="major"
          value={formData.major}
          onChange={handleChange}
        >
          <option value="">전공*</option>
          <option value="불교대학">컴퓨터공학과</option>
          <option value="불교대학">멀티미디어소프트웨어공학과</option>
          <option value="불교대학">인공지능과</option>
          <option value="불교대학">데이터사이언스과</option>
          <option value="불교대학">엔터테인먼트테크놀로지과</option>
        </select>
        {/* <input
          className="form-input"
          type="text"
          name="major"
          placeholder="전공*"
          ref={majorInputRef}
          onChange={(e) => handleChange(e)}
        /> */}
        <input
          className="form-input"
          type="text"
          name="secondMajor"
          placeholder="부전공"
          ref={minorInputRef}
          onChange={(e) => handleChange(e)}
        />
        <input
          className="form-input"
          type="text"
          name="birth"
          placeholder="생년월일(YYYY-MM-DD)*"
          value={formData.date_of_birth}
          onChange={handleStudentBirthChange}
        />
        {/*
          <div
            className="help"
            onMouseEnter={handleMouseEnter}
            onMouseLeave={handleMouseLeave}
            style={{ position: "absolute", top: "0", left: "0" }}
          >
            도움말{" "}
            {messageVisible && (
              <p
                className="helptext"
                style={{
                  position: "absolute",
                  top: "0",
                  left: "100%",
                  marginLeft: "10px",
                }}
              >
                {" "}
                'mdrims - 졸업 대상자 관리 - 취득학점확인서 조회'에서 .xls 다운로드
              </p>
            )}
          </div>
        </div> */}
        <button className="submit-button" type="submit">
          입력하기
        </button>
      </form>
    </div>
  );
};

export default SignUpScreen;
