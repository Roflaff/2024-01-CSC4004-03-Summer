import React from "react";
import { Button, ConfigProvider, Space, Popover, Row, Col } from "antd";

const Curriculum = ({ data }) => {
  const groupedSubjects = data.reduce((acc, subject) => {
    const key = `${subject.grade}-${subject.semester}`;
    if (!acc[key]) {
      acc[key] = [];
    }
    acc[key].push(subject);
    return acc;
  }, {});

  return (
    <div>
      <ConfigProvider
        theme={{
          token: {
            // 전공 버튼 컬러
            colorPrimary: "#867060",
            borderRadius: 10,
            paddingInline: "auto",
            // 교양 버튼 컬러
            colorBgContainer: "#B3C278",
          },
        }}
      >
        {Object.entries(groupedSubjects).map(([key, subjects]) => {
          const [grade, semester] = key.split("-");
          return (
            <div
              key={key}
              style={{
                marginBottom: "50px",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                flexDirection: "column",
              }}
            >
              <h3 style={{ marginBottom: "15px" }}>
                {grade}학년 {semester}학기
              </h3>
              <Space direction="vertical">
                <Row gutter={[16, 16]}>
                  {subjects.map((subject, index) => (
                    <Col key={index} span={12}>
                      <Popover
                        content={
                          <div>
                            <p>
                              선이수 교과목 :{" "}
                              {subject.prev ? subject.prev : "없음."}
                            </p>
                          </div>
                        }
                        title={subject.major ? "전공" : "교양"}
                        trigger="click"
                        color={subject.major ? "#F2E2DE" : "#F1EADE"}
                        arrow={{ pointAtCenter: true }}
                      >
                        <Button
                          type={subject.major ? "primary" : "default"}
                          shape="round"
                          size="large"
                          style={{ marginLeft: "15px", marginRight: "15px" }}
                        >
                          {subject.sbj}
                        </Button>
                      </Popover>
                    </Col>
                  ))}
                </Row>
              </Space>
            </div>
          );
        })}
      </ConfigProvider>
    </div>
  );
};

export default Curriculum;
