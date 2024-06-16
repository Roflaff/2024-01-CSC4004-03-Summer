import React, { useState } from "react";
import { PieChart } from "react-minimal-pie-chart";
import List from "./List";

// 총 학점에 비례해서 차트의 크기를 다르게 설정하는 함수

const calculateChartSize = (subject) => {
  const minSize = 70;
  const maxSize = 400;

  const size = Math.max(minSize, Math.min(maxSize, subject));

  return size * 2.2 + "px";
};

const PieChartComponent = ({ data }) => {
  const chartSize = calculateChartSize(data.subject); // Calculate chart size based on subject

  const chartStyles = {
    background: "rgba(228, 195, 162, 0.4)",
    borderRadius: "50%",
    border: "none",
    display: "block",
    margin: "auto 0",
    width: chartSize,
    height: chartSize,
  };
  // 모달 상태 관리
  const [modalVisible, setModalVisible] = useState(false);
  // 모달 열기 함수
  const openModal = () => {
    setModalVisible(true);
  };
    // 모달 닫기 함수
    const closeModal = (e) => {
      e.stopPropagation(); // 이벤트 버블링 차단
      setModalVisible(false);
    };

  return (
    <div onClick={openModal}>
    <PieChart
      data={[data]}
      reveal={(data.subjectfinished  / data.subject) * 100}
      lineWidth={35}
      rounded
      animate
      label={(labelRenderProps) => labelRenderProps.dataEntry.subjectfinished  + "학점"}
      style={chartStyles}
    />
    {modalVisible && (
        <div className="modal-overlay" onClick={closeModal} style={{zIndex:999}}>
          <div className="modal-content" onClick={(e) => e.stopPropagation()}>
            <List />
          </div>
        </div>
      )}
    </div>
  );
};

export default PieChartComponent;