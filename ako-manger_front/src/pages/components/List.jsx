import React, { useState, useEffect } from 'react';

const List = () => {
  const courses = ['기초 프로그래밍', '컴퓨터구성', '심화프로그래밍'];
//   const [courses, setCourses] = useState([]);
//
//   useEffect(() => {
//     // 서버에서 courses 목록을 가져오는 비동기 함수 호출
//     const fetchCourses = async () => {
//       try {
//         const response = await fetch('http://서버주소/courses');
//         if (response.ok) {
//           const data = await response.json();
//           setCourses(data.courses); // 서버로부터 받아온 courses 목록을 상태에 설정
//         } else {
//           console.error('서버 응답 실패');
//         }
//       } catch (error) {
//         console.error('서버 요청 중 오류:', error);
//       }
//     };
//
//     fetchCourses(); // 비동기 함수 호출
//   }, []); // 컴포넌트가 처음 렌더링될 때 한 번만 호출

  return (
    <div>
      <h2 style={{ color: '#757575'}}>추천 강의 목록</h2>
      <br />
      <ul style={{ fontSize:'14px', lineHeight: '1.8'}}>
        {courses.map((course, index) => (
          <div key={index} style={{ textAlign: 'left' }}>- {course}</div>
        ))}
      </ul>
    </div>
  );
};

export default List;
