import React from "react";
import PhoneAko from "../../Images/핸드폰쥔아코_(designed by 박세리).png";
import rightBubbleTriangle from "../../Images/right.png"; // Use right arrow for right bubble

const RightBubble = ({ message }) => {
  return (
    <div style={styles.chatWrapper}>
      <img
        className="PhoneAko"
        src={PhoneAko}
        alt="PhoneAko"
        style={styles.PhoneAko}
      />
      <div style={styles.userInfo}>
        <p style={styles.userName}>사용자ID</p>
      </div>
      <div style={styles.bubbleContainer}>
        <div style={styles.bubbleWrapper}>
          <p style={styles.chattingContents}>{message}</p>
        </div>
        <img
          src={rightBubbleTriangle}
          style={styles.bubbleTriangle}
          alt="Bubble Triangle"
        />
      </div>
    </div>
  );
};

const styles = {
  chatWrapper: {
    display: "flex",
    marginBottom: 30,
    flexDirection: "row-reverse", // Align components to the right
  },
  userInfo: {
    marginRight: 8, // Adjust margin
    marginLeft: 4,
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
  bubbleWrapper: {
    backgroundColor: "#F5D6B2",
    borderRadius: 8,
    padding: 8,
    maxWidth: 224,
  },
  bubbleTriangle: {
    width: 8,
    height: 8,
    marginTop: 6,
    // Adjust margin between bubble and triangle
  },
  chattingContents: {
    fontSize: 15,
    fontWeight: "500",
    color: "#414141",
    lineHeight: 1.4,
  },
  PhoneAko: {
    height: 50,
    transform: "scaleX(-1)", // Flip the image horizontally
  },
};

export default RightBubble;
