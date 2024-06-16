import { ConfigProvider, Button, Popover } from "antd";
import React from "react";

const keywordData = [
  { keyword: "Keyword 1", popularity: 100 },
  { keyword: "Keyword 2", popularity: 80 },
  { keyword: "Keyword 3", popularity: 60 },
  { keyword: "Keyword 4", popularity: 40 },
];

const KeywordComponent = () => {
  // Sort keywordData based on popularity
  const sortedKeywords = keywordData.sort(
    (a, b) => b.popularity - a.popularity
  );

  return (
    <ConfigProvider
      theme={{
        token: {
          colorPrimary: "#A08D7D",
          borderRadius: 10,
          paddingInline: "auto",
        },
      }}
    >
      {sortedKeywords.slice(0, 4).map((keyword, index) => (
        <Popover
          key={index}
          content={
            <div>
              <p>{` ${keyword.keyword}에 대한 챗봇 답변 `}</p>
            </div>
          }
          title={`${index + 1}위`}
          trigger="click"
          color="rgba(160, 141, 125, 0.5)"
          arrow={{ pointAtCenter: true }}
        >
          <Button>{`#${keyword.keyword}`}</Button>
        </Popover>
      ))}
    </ConfigProvider>
  );
};

export default KeywordComponent;
