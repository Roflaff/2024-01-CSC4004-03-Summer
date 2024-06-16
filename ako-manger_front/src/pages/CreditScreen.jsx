import React, { useState, useEffect } from "react";
import axios from "axios";
import { Layout, Menu, Button, FloatButton, Modal, Upload } from "antd";
import { useNavigate } from "react-router-dom";
import {
  PieChartOutlined,
  ApartmentOutlined,
  UploadOutlined,
  MenuUnfoldOutlined,
  MenuFoldOutlined,
} from "@ant-design/icons";

import HeaderComponent from "./components/HeaderComponent";
import PieChartComponent from "./components/PieChartComponent";
import Curriculum from "./components/curriculum";
import List from "./components/List";

import "../css/CreditScreen.css";

const { Sider } = Layout;
// 업데이트
// 각 value값을 subject, subjectfinished로 바꾸기
const chartData = [
  { title: "졸업까지", subjectfinished: 55, subject: 150, color: "#F1BB79" },
  { title: "전공", subjectfinished: 30, subject: 80, color: "#867060" },
  {
    title: "공통 교양",
    subjectfinished: 20,
    subject: 50,
    color: "rgba(230, 170, 15, 0.56)",
  },
  { title: "일반 교양", subjectfinished: 5, subject: 20, color: "#B3C278" },
];

const curriculumDummyData = [
  { grade: 1, semester: 1, sbj: "계산적 사고법", prev: null, major: true },
  { grade: 1, semester: 2, sbj: "어드벤처 디자인", prev: null, major: true },
  { grade: 1, semester: 2, sbj: "이산수학", prev: null, major: false },
  {
    grade: 2,
    semester: 1,
    sbj: "자료구조와 실습",
    prev: "프로그래밍 기초",
    major: true,
  },
  { grade: 2, semester: 1, sbj: "컴퓨터 구성", prev: "이산수학", major: false },
  {
    grade: 2,
    semester: 2,
    sbj: "시스템소프트웨어와 실습",
    prev: "자료구조와 실습",
    major: true,
  },
  {
    grade: 3,
    semester: 1,
    sbj: "공개SW프로젝트",
    prev: "자료구조와 실습",
    major: true,
  },
];

const CreditScreen = () => {
  const navigate = useNavigate();

  const [collapsed, setCollapsed] = useState(false);
  const [modalVisible, setModalVisible] = useState(false);
  const [uploadVisible, setUploadVisible] = useState(false);
  const [selectedFile, setSelectedFile] = useState(null);

  // 1. 학점 조회 api 연결 예시(반드시 헤더 토큰도 같이 전송해야합니다!) // 졸업 기준 총 전공 학점
  const sendMajorInfoToServer = async () => {
    try {
      // 1. 로그인 화면에서 로그인 후 토큰만 저장 하는 것이 아닌, user의 전공, 년도를 함께 받아 와서 값 넣기
      const majorDto = { majorName: "컴퓨터공학과", year: "2020" };
      const token = localStorage.getItem("authToken");
      const response = await axios.post(
        "http://34.47.92.19:8000/Major/getTotalMajorScore",
        majorDto,
        {
          headers: {
            Authorization: token,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("총 전공 학점 응답 데이터:", response.data);
    } catch (error) {
      console.error("에러 발생:", error);
    }
  };

  useEffect(() => {
    sendMajorInfoToServer();
  }, []);

  // 졸업 기준 총 일반 교양 학점
  const sendCommonInfoToServer = async () => {
    try {
      // 1. 로그인 화면에서 로그인 후 토큰만 저장 하는 것이 아닌, user의 전공, 년도를 함께 받아 와서 값 넣기
      const majorDto = { majorName: "컴퓨터공학과", year: "2020" };
      const token = localStorage.getItem("authToken");
      const response = await axios.post(
        "http://34.47.92.19:8000/Major/getTotalCommonScore",
        majorDto,
        {
          headers: {
            Authorization: token,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("총 일반 교양 학점 응답 데이터:", response.data);
    } catch (error) {
      console.error("에러 발생:", error);
    }
  };

  useEffect(() => {
    sendCommonInfoToServer();
  }, []);

  //졸업 기준 지정 교양 학점
  const sendDesignatedInfoToServer = async () => {
    try {
      // 1. 로그인 화면에서 로그인 후 토큰만 저장 하는 것이 아닌, user의 전공, 년도를 함께 받아 와서 값 넣기
      const majorDto = { majorName: "컴퓨터공학과", year: "2020" };
      const token = localStorage.getItem("authToken");
      const response = await axios.post(
        "http://localhost:8080/Major/getTotalDesignatedScore",
        majorDto,
        {
          headers: {
            Authorization: token,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("지정 교양 학점 응답 데이터:", response.data);
    } catch (error) {
      console.error("에러 발생:", error);
    }
  };

  useEffect(() => {
    sendDesignatedInfoToServer();
  }, []);

  // 졸업 기준 총 학점
  const sendInfoToServer = async () => {
    try {
      // 1. 로그인 화면에서 로그인 후 토큰만 저장 하는 것이 아닌, user의 전공, 년도를 함께 받아 와서 값 넣기
      const majorDto = { majorName: "컴퓨터공학과", year: "2020" };
      const token = localStorage.getItem("authToken");
      const response = await axios.post(
        "http://localhost:8080/Major/getTotalScore",
        majorDto,
        {
          headers: {
            Authorization: token,
            "Content-Type": "application/json",
          },
        }
      );
      console.log("총 학점 응답 데이터:", response.data);
    } catch (error) {
      console.error("에러 발생:", error);
    }
  };

  useEffect(() => {
    sendInfoToServer();
  }, []);

  const handleMenuClick = (e) => {
    const sectionId = `part-${e.key}`;
    const section = document.getElementById(sectionId);
    if (section) {
      section.scrollIntoView({ behavior: "smooth" });
    }
  };

  const openModal = () => setModalVisible(true);
  const closeModal = () => setModalVisible(false);

  const handleFileChange = (info) => {
    if (info.fileList.length > 0) {
      setSelectedFile(info.fileList[0].originFileObj);
    } else {
      setSelectedFile(null);
    }
  };
  const handleUploadCancel = () => setUploadVisible(false);

  // 2. 엑셀 파일 전송 코드
  //아래와 같이 헤더까지 같이 보내서 file 양식의 문제인데, 확인 방법은
  // 개발자 도구 -> 네트워크-> 왼쪽하단의 이름을 보면 지금까지 전송 내용 자세히 볼 수 있습니다!
  //그 중 페이로드 확인하시면 될 것 같아요!
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const formData = new FormData();
      formData.append("file", selectedFile);
      const token = localStorage.getItem("authToken");

      const response = await fetch("http://localhost:8080/excel/uploadF", {
        method: "POST",
        headers: {
          Authorization: token,
        },
        body: formData,
      });

      if (response.status === 200) {
        alert("파일 업로드 성공");
      } else {
        alert("서버 오류가 발생했습니다");
      }
    } catch (error) {
      alert("네트워크 오류가 발생했습니다");
    }
  };

  return (
    <div>
      <FloatButton
        description="✏️ 다음 학기 시간표 추천해 줄게요"
        shape="square"
        className="custom-float-button"
        onClick={openModal}
        style={{
          right: 50,
          fontSize: 100,
          width: 250,
          height: 40,
          marginRight: 10,
        }}
      />

      <HeaderComponent />
      <div className="CreditScreen-container">
        <Sider trigger={null} collapsible collapsed={collapsed} theme="light">
          <Menu
            mode="inline"
            defaultSelectedKeys={["1"]}
            theme="light"
            className="custom-menu"
            onClick={handleMenuClick}
          >
            <Menu.Item key="1" icon={<PieChartOutlined />}>
              이수 학점 조회
            </Menu.Item>
            <Menu.Item key="2" icon={<ApartmentOutlined />}>
              교과목 이수 체계도
            </Menu.Item>
            <Menu.Item
              key="3"
              icon={<UploadOutlined />}
              onClick={() => setUploadVisible(true)}
            >
              수강과목 첨부
            </Menu.Item>
          </Menu>
          <Modal
            title="수강과목 업로드"
            visible={uploadVisible}
            onOk={handleSubmit}
            onCancel={handleUploadCancel}
            footer={[
              <Button
                key="back"
                style={{ borderColor: "#F9F0E7", color: "#757575" }}
                onClick={handleUploadCancel}
              >
                취소
              </Button>,
              <Button
                key="submit"
                type="primary"
                style={{ backgroundColor: "#F9F0E7", color: "#757575" }}
                onClick={handleSubmit}
              >
                확인
              </Button>,
            ]}
          >
            <Upload
              beforeUpload={() => false}
              type="file"
              accept=".xls,.xlsx"
              onChange={handleFileChange}
            >
              {" "}
              {/* 자동 업로드 금지 */}
              <Button icon={<UploadOutlined />}>파일 선택</Button>
            </Upload>
            <div style={{ color: "#757575" }}>
              <br /> 'mdrims - 성적 - 전체성적관리 - 전체 성적 조회'에서 .xlsx
              다운로드
            </div>
          </Modal>
        </Sider>
        <Button
          type="text"
          icon={collapsed ? <MenuUnfoldOutlined /> : <MenuFoldOutlined />}
          onClick={() => setCollapsed(!collapsed)}
          style={{ fontSize: "16px", width: 64, height: 64 }}
        />
        <div className="item-container">
          <h2
            id="part-1"
            className="curriculum-title"
            style={{ marginBottom: "50px", textAlign: "center" }}
          >
            이수 학점 조회
          </h2>
          <div className="chart-wrap">
            {chartData.map((dataItem, index) => (
              <div key={index} className="chart-section">
                <p className="chart-title">{dataItem.title}</p>
                <div className="chart-content">
                  <PieChartComponent data={dataItem} />
                  <p className="chart-credits">총 {dataItem.subject} 학점</p>
                </div>
              </div>
            ))}
          </div>
          <div id="part-2" className="curriculum-wrap">
            <h2
              className="curriculum-title"
              style={{
                marginBottom: "50px",
                textAlign: "center",
                marginTop: "25px",
              }}
            >
              (전공명) 필수 과목 이수체계도
            </h2>
            <div className="curriculum-section">
              <Curriculum data={curriculumDummyData}></Curriculum>
            </div>
          </div>
        </div>
      </div>
      {modalVisible && (
        <div className="modal-overlay" onClick={closeModal}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <List />
          </div>
        </div>
      )}
    </div>
  );
};

export default CreditScreen;
