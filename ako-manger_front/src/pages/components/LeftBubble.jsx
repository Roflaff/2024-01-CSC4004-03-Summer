import React from "react";
import ListenAko from "../../Images/듣는아코_(designed by 박세리).png";
import leftBubbleTriangle from "../../Images/left.png";

const LeftBubble = ({ data }) => {
  return (
    <div style={styles.chatWrapper}>
      <img
        className="ListenAko"
        src={ListenAko}
        alt="ListenAko"
        style={styles.ListenAko}
      />
      <div style={styles.userInfo}>
        <p style={styles.userName}>mentor</p>
      </div>
      <div style={styles.bubbleContainer}>
        <img
          src={leftBubbleTriangle}
          style={styles.bubbleTriangle}
          alt="Bubble Triangle"
        />
        <div style={styles.bubbleWrapper}>
          <p style={styles.chattingContents}>
            반가위, 안녕? 잘 지냈어? 답변 등
          </p>
        </div>
      </div>
    </div>
  );
};

const styles = {
  chatWrapper: {
    display: "flex",
    marginBottom: 16,
  },
  userInfo: {
    marginLeft: 8,
    marginRight: 4,
  },
  userName: {
    fontSize: 13,
    fontWeight: "400",
    color: "#000",
  },
  bubbleContainer: {
    display: "flex",
    marginTop: 4,
  },
  bubbleTriangle: {
    width: 8,
    height: 8,
    marginTop: 6,
  },
  bubbleWrapper: {
    backgroundColor: "#F8E7BA",
    borderRadius: 8,
    padding: 8,
    maxWidth: 224,
  },
  chattingContents: {
    fontSize: 15,
    fontWeight: "500",
    color: "#414141",
    lineHeight: 1.4,
  },
  ListenAko: {
    height: 80,
  },
};

export default LeftBubble;
