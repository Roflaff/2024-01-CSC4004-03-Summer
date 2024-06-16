// ChatLog.jsx

import React from "react";

const ChatLog = () => {
  return (
    <div style={styles.chatLogContainer}>
      <div style={styles.chatLog}>
        <div style={styles.log}>채팅 기록</div>
        {/* 예시로 몇 가지 채팅 기록을 추가했습니다. */}
      </div>
    </div>
  );
};

export default ChatLog;

const styles = {
  chatLogContainer: {
    overflowY: "auto", // 채팅 로그가 화면을 넘어갈 경우 스크롤바 표시
    maxHeight: "80vh", // 채팅 로그의 최대 높이 지정
  },
  chatLog: {
    padding: "10px",
  },
  log: {
    backgroundColor: "#FFFFFF",
    borderRadius: "8px",
    padding: "8px",
    marginBottom: "8px",
    fontSize: "14px",
  },
};
