import React from "react";

const TimeTable = () => {
  // 시간표 스타일
  const tableCellStyle = {
    padding: "5px",
    textAlign: "center",
    border: "1.2px solid #DBC9BB",
    height: "14px",
    width: "200px",
  };
  const halfHourCellStyle = {
    ...tableCellStyle,
    borderTop: "none",
  };
  const mergedCellStyles = {
    ...tableCellStyle,
    backgroundColor: "#F4E7DA",
    color: "#867060",
    fontSize: "11px",
    height: "10px",
    width: "40px",
  };
  const lectureCellStyle = {
    display: "flex",
    flexDirection: "column",
    justifyContent: "flex-start",
    alignItems: "flex-start",
    padding: "5px",
    fontSize: "10px",
  };
  const lectureNameStyle = {
    fontSize: "9px",
    color: "#867060",
  };
  const lectureRoomStyle = {
    fontSize: "7px",
    color: "#867060",
  };

  // Google Maps URL 만들기 함수
  const getGoogleMapsUrl = (building) => {
    const address = encodeURIComponent(`동국대학교 ${building}`);
    return `https://www.google.com/maps/search/?api=1&query=${address}`;
  };

  const lectureData = [
    {
      num: "1",
      day: "월요일",
      time: "11:00~12:30",
      name: "기초프로그래밍",
      room: "401-6119(신공학관)",
      building: "신공학관",
      lat: "37.5580918",
      lon: "126.9982178",
    },
    {
      num: "2",
      day: "월요일",
      time: "14:00~15:30",
      name: "데이터 베이스",
      room: "401-6114(신공학관)",
      building: "신공학관",
      lat: "37.5580918",
      lon: "126.9982178",
    },
    {
      num: "3",
      day: "월요일",
      time: "15:30~17:00",
      name: "컴퓨터 그래픽스",
      room: "407-203(정보문화관 P)",
      building: "정보문화관 P",
      lat: "37.5580918",
      lon: "126.9982178",
    },
    {
      num: "4",
      day: "화요일",
      time: "12:00~13:30",
      name: "운영체제",
      room: "401-4142(신공학관)",
      building: "신공학관",
      lat: "37.5580918",
      lon: "126.9982178",
    },
    {
      num: "5",
      day: "화요일",
      time: "15:00~17:00",
      name: "공개 SW 프로젝트",
      room: "401-4137(신공학관)",
      building: "신공학관",
      lat: "37.5580918",
      lon: "126.9982178",
    },
    {
      num: "2",
      day: "수요일",
      time: "12:00~13:30",
      name: "데이터 베이스",
      room: "401-6114(신공학관)",
      building: "신공학관",
      lat: "37.5580918",
      lon: "126.9982178",
    },
    {
      num: "3",
      day: "수요일",
      time: "14:00~15:30",
      name: "컴퓨터 그래픽스",
      room: "407-203(정보문화관 P)",
      building: "정보문화관 P",
      lat: "37.5580918",
      lon: "126.9982178",
    },
    {
      num: "1",
      day: "목요일",
      time: "11:30~13:00",
      name: "기초프로그래밍",
      room: "401-6119(신공학관)",
      building: "신공학관",
      lat: "37.5580918",
      lon: "126.9982178",
    },
    {
      num: "4",
      day: "목요일",
      time: "13:30~15:00",
      name: "운영체제",
      room: "401-4142(신공학관)",
      building: "신공학관",
      lat: "37.5580918",
      lon: "126.9982178",
    },
    {
      num: "5",
      day: "목요일",
      time: "15:00~17:00",
      name: "공개 SW 프로젝트",
      room: "401-4137(신공학관)",
      building: "신공학관",
      lat: "37.5580918",
      lon: "126.9982178",
    },
  ];

  // 시간표 데이터
  const schedule = Array.from({ length: 20 }, (_, index) => ({
    time: `${Math.floor(9 + index / 2)
      .toString()
      .padStart(2, "0")}:${index % 2 === 0 ? "00" : "30"}`,
    monday: "",
    tuesday: "",
    wednesday: "",
    thursday: "",
    friday: "",
  }));

  // 강의 정보를 시간표에 입력
  for (const lecture of lectureData) {
    const dayIndex = {
      월요일: "monday",
      화요일: "tuesday",
      수요일: "wednesday",
      목요일: "thursday",
      금요일: "friday",
    }[lecture.day];
    const [startHour, startMinute] = lecture.time
      .split("~")[0]
      .split(":")
      .map(Number);
    const [endHour, endMinute] = lecture.time
      .split("~")[1]
      .split(":")
      .map(Number);

    // 강의가 시작하는 첫 번째 시간
    let startSlot =
      Math.floor((startHour - 9) * 2) + (startMinute === 30 ? 1 : 0);

    // 강의가 끝나는 마지막 시간
    let endSlot = Math.floor((endHour - 9) * 2) + (endMinute === 0 ? 0 : 1);

    for (let slot = startSlot; slot < endSlot; slot++) {
      schedule[slot][dayIndex] = (
        <div>
          <div style={lectureNameStyle}>{lecture.name}</div>
          <div style={lectureRoomStyle}>
            {lecture.room}
            <div>
              <a
                href={getGoogleMapsUrl(lecture.building)}
                target="_blank"
                rel="noopener noreferrer"
                style={{ color: '#2B43C3' }}
              >
                어떻게 가요?
              </a>
            </div>
          </div>
        </div>
      );
    }
  }

  return (
    <div style={{ position: "relative", marginLeft: "20px" }}>
      <h1 style={{ color: "#867060", textAlign: "center" }}>
        2024-yy학기 시간표
      </h1>
      <h1 style={{ color :"#757575",fontSize:"9px",textAlign: "center",marginTop:"10px", fontWeight: "lighter"}}>창을 닫으려면 아무 곳이나 누르시오</h1>
      <div
        style={{ display: "flex", justifyContent: "center", marginTop: "30px" }}
      >
        <table
          style={{
            borderCollapse: "collapse",
            width: "80%",
            height: "700px",
            backgroundColor: "#fff",
            boxShadow: "0 0 20px rgba(0, 0, 0, 0.15)",
            marginTop:"-10px",
          }}
        >
          <thead style={{ backgroundColor: "#F4E7DA", color: "#867060" }}>
            <tr>
              <th style={tableCellStyle}>시간</th>
              <th style={tableCellStyle}>월</th>
              <th style={tableCellStyle}>화</th>
              <th style={tableCellStyle}>수</th>
              <th style={tableCellStyle}>목</th>
              <th style={tableCellStyle}>금</th>
            </tr>
          </thead>
          <tbody>
            {schedule.map((row, index) => (
              <tr key={index}>
                {index % 2 === 0 ? (
                  <td rowSpan="2" style={mergedCellStyles}>
                    {row.time}
                  </td>
                ) : null}
                <td style={halfHourCellStyle}>
                  <div style={lectureCellStyle}>{row.monday}</div>
                </td>
                <td style={halfHourCellStyle}>
                  <div style={lectureCellStyle}>{row.tuesday} </div>
                </td>
                <td style={tableCellStyle}>
                  <div style={lectureCellStyle}>{row.wednesday}</div>
                </td>
                <td style={tableCellStyle}>
                  <div style={lectureCellStyle}>{row.thursday}</div>
                </td>
                <td style={tableCellStyle}>
                  <div style={lectureCellStyle}>{row.friday}</div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      {/* 모달 */}
      {/* {modalVisible && (
        <div style={{ position: 'fixed', top: 0, left: 0, width: '100%', height: '100%', backgroundColor: 'rgba(0, 0, 0, 0.5)', zIndex: '2000', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
          <div style={{ backgroundColor: '#fff', padding: '20px', borderRadius: '5px', boxShadow: '0 0 20px rgba(0, 0, 0, 0.3)', width: '300px', textAlign: 'center' }}>
            <button onClick={closeModal}>닫기</button>
          </div>
        </div>
      )} */}
    </div>
  );
};

export default TimeTable;
