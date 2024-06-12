package com.dgu_csc.akomanager_back.controller;

import com.dgu_csc.akomanager_back.dto.ChatRequest;
import com.dgu_csc.akomanager_back.dto.ChatResponse;
import com.dgu_csc.akomanager_back.dto.MajorDto;
import com.dgu_csc.akomanager_back.jwt.JWTUtil;
import com.dgu_csc.akomanager_back.model.ChatBot;
import com.dgu_csc.akomanager_back.service.ChatBotService;
import com.dgu_csc.akomanager_back.service.MajorService;
import com.dgu_csc.akomanager_back.service.SubjectFinishedServiceImpl;
import com.dgu_csc.akomanager_back.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.time.Instant;


@RestController
@RequiredArgsConstructor
@RequestMapping( "/chat")
public class ChatbotController {
    private final String DefaultPath = System.getProperty("user.dir") + "/ako-manager_back/src/main/";

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private ChatBotService chatBotService;

    @Autowired
    private UserService userService;

    @Autowired
    private MajorService majorService;
    @Autowired
    private SubjectFinishedServiceImpl subjectFinishedServiceImpl;

    // todo POST : (/chat/ask) (POSTMAN FORMAT : Username, UserInput)
    @PostMapping("/ask")
    public ResponseEntity<ChatResponse> askChatbot(ChatRequest chatRequest, HttpServletRequest request){
        String username = jwtUtil.getUsername(jwtUtil.getToken(request));
        String userInput = chatRequest.getUserInput();
        String response;

        Instant time = Instant.now();

        ChatBot chatBot = new ChatBot();
        chatBot.setCstudentId(userService.findByStudentId(username).get());
        chatBot.setUserinput(userInput);
        chatBot.setTimelog(time);

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setStudentId(username);
        chatResponse.setUserinput(userInput);
        chatResponse.setTimelog(time);

        MajorDto majorDto = new MajorDto();
        majorDto.setMajorName(chatBot.getCstudentId().getMajor());
        String enterYear = username.substring(0, Math.min(username.length(), 4));
        majorDto.setYear(enterYear);

        try {
            String pythonScriptPath = DefaultPath + "Python/ChatbotCode.py";
            String[] command = {"python3", pythonScriptPath};

            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));

            writer.write(username + "\n");
            writer.write(userInput + "\n");
            writer.flush();

            if (userInput.equals("대화종료")) {
                response = "또 만날 기회를 기다리고 있을게요! ❤️";
            } else {
                response = reader.readLine();
            }
            chatBotService.saveLog(chatBot);

            switch (response) {
                // 0번, 1번 : 아코 매니저 자기 소개
                case "0" : {
                    chatResponse.setBotoutput("안녕하세요! 아코 매니저입니다~ 원하시는 질문이 있으시다면 물어봐 주세요!");
                    break;
                    }
                case "1" : {
                    chatResponse.setBotoutput("안녕하세요~ 저는 동국대학교 학생들을 돕기 위해 이 세상에 태어난 아코 매니저입니다!");
                    break;
                }
                // 2번 : 수강해야 하는 과목들에 대한 정보 물었을 때
                case "2" : {
                    chatResponse.setBotoutput(username + "님이 수강해야 하는 과목들에 대한 정보를 불러왔어요");
                    break;
                }
                // 3번 : 전공 졸업기준표를 요구하는 경우, 수강해야 하는 교양 필수, 전공 필수 학점 알려 주기
                case "3" : {
                    chatResponse.setBotoutput(username + "님이 다니시는 전공의" +
                            " 교양 필수 과목은 ["+ majorService.getTotalDesignatedScore(majorDto) + "]학점, 전공 필수 학점은 ["+ majorService.getTotalMajorScore(majorDto) +
                            "]학점을 포함하여 총 [" + majorService.getTotalScore(majorDto) + "]학점을 수강해야 합니다");
                    break;
                }
                // 4번 : 추천 시간표 요구
                case "4" : {
                    chatResponse.setBotoutput(username + "님이 수강해야 하는 과목들을 기준으로 추천 시간표를 짜 봤어요");
                    break;
                }
                // 5번 : 해결됐다고 했을 때
                case "5" : {
                    chatResponse.setBotoutput("해결되었다니 정말 다행이네요! 언제든 도움이 필요하시다면 아코 매니저를 불러 주세요!");
                    break;
                }
                // 6번 : 선이수 과목 요구
                case "6" : {
                    chatResponse.setBotoutput("해당 과목을 듣기 위해서 추가로 수강해야 하는 과목들을 가지고 왔어요");
                    break;
                }
                // 7번 : 졸업 조건 만족을 위해 더 들어야 하는 학점 정보
                case "7" : {
                    chatResponse.setBotoutput("졸업 기준을 만족하기 위해 "+username+"님은 [" + (majorService.getTotalScore(majorDto) - subjectFinishedServiceImpl.getUserTotalScore(chatResponse.getStudentId()))
                    + "학점을 더 이수해야 합니다\n전공은 [" + (majorService.getTotalMajorScore(majorDto) - subjectFinishedServiceImpl.getUserTotalMajorScore(chatResponse.getStudentId())) + "] 학점,\n" +
                            "교양 필수는 ["+ (majorService.getTotalDesignatedScore(majorDto) -  subjectFinishedServiceImpl.getUserTotalCommonScore(chatResponse.getStudentId())) +"]학점을 필수적으로 들어야 합니다");
                    break;
                }
                // 8번 : 들은 학점 정보만 요구하는 경우
                case "8" : {
                    chatResponse.setBotoutput(username + "님은 전공 [" + subjectFinishedServiceImpl.getUserTotalMajorScore(chatResponse.getStudentId()) + "] 학점, 교양 [" + subjectFinishedServiceImpl.getUserTotalCommonScore(chatResponse.getStudentId()) +
                            "학점을 포함하여 총 [" + subjectFinishedServiceImpl.getUserTotalScore(chatResponse.getStudentId()) + "학점을 이수했습니다");
                    break;
                }
                // 9번 : 교양 중 들은 학점 정보만 요구하는 경우
                case "9" : {
                    chatResponse.setBotoutput(username + "님은 교양 [" + subjectFinishedServiceImpl.getUserTotalCommonScore(chatResponse.getStudentId()) +
                            "학점을 이수했습니다");
                    break;
                }
                //10번 : 전공 중 들은 학점 정보만 요구하는 경우
                case "10" : {
                    chatResponse.setBotoutput(username + "님은 전공 [" + subjectFinishedServiceImpl.getUserTotalMajorScore(chatResponse.getStudentId()) + "] 학점을 이수했습니다");
                    break;
                }
                // 11번 : 전공 중 추가로 들어야 하는 학점 수를 요구하는 경우
                case "11" : {
                    chatResponse.setBotoutput("전공은 [" + (majorService.getTotalMajorScore(majorDto) - subjectFinishedServiceImpl.getUserTotalMajorScore(chatResponse.getStudentId())) + "] 학점을 더 이수해야 합니다");
                    break;
                }
                // 12번 : 미 수강한 교양 필수 학점 수를 요구하는 경우
                case "12" : {
                    chatResponse.setBotoutput("교양 필수는 [" + (majorService.getTotalDesignatedScore(majorDto) + majorService.getTotalCommonScore(majorDto) - subjectFinishedServiceImpl.getUserTotalCommonScore(chatResponse.getStudentId())) + "]학점을 필수적으로 들어야 합니다");
                    break;
                }
                // 13번 : 총 전공 학점 수를 요구
                case "13" : {
                    chatResponse.setBotoutput(username + "님의 전공은 [" + majorService.getTotalMajorScore(majorDto) + "]학점을 전공으로 채워야 합니다");
                    break;
                }
                // 14번 : 총 요구하는 최소 교양 학점 수를 요구
                case "14" : {
                    chatResponse.setBotoutput(username + "님의 전공은 [" + (majorService.getTotalDesignatedScore(majorDto) + majorService.getTotalCommonScore(majorDto)) + "]학점을 교양 필수으로 채워야 합니다");
                    break;
                }
                default : chatResponse.setBotoutput(response);
            }
            return ResponseEntity.ok(chatResponse);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body(chatResponse);
        }
    }

    @PostMapping("/ask2")
    public ResponseEntity<ChatResponse> ask2Bot(@RequestBody ChatRequest chatRequest, HttpServletRequest request) {
        String username = jwtUtil.getUsername(jwtUtil.getToken(request));
        String userInput = chatRequest.getUserInput();
        String response;

        Instant time = Instant.now();

        ChatBot chatBot = new ChatBot();
        chatBot.setCstudentId(userService.findByStudentId(username).get());
        chatBot.setUserinput(userInput);
        chatBot.setTimelog(time);

        ChatResponse chatResponse = new ChatResponse();
        chatResponse.setStudentId(username);
        chatResponse.setUserinput(userInput);
        chatResponse.setTimelog(time);

        try {
            String pythonScriptPath = DefaultPath + "Python/ChatbotCode.py";
            String[] command = {"python3", pythonScriptPath};

            ProcessBuilder pb = new ProcessBuilder(command);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));

            writer.write(username + "\n");
            writer.write(userInput + "\n");
            writer.flush();

            StringBuilder responseBuilder = new StringBuilder();

            if (userInput.equals("대화종료")) {
                response = "또 만날 기회를 기다리고 있을게요! ❤️";
            } else {
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line).append("\n");
                }
                response = responseBuilder.toString().trim();
            }
            if(response.contains("님이 다니시는 전공의 이수 체계도를 가지고 왔어요")) {
                MajorDto majorDto = new MajorDto();
                majorDto.setMajorName(chatBot.getCstudentId().getMajor());
                String enterYear = username.substring(0, Math.min(username.length(), 4));
                majorDto.setYear(enterYear);
                response += " : 졸업 기준 학점은 [ " + majorService.getTotalScore(majorDto) + " ] 입니다.";
            }


            chatBot.setBotinput(response);
            chatResponse.setBotoutput(response);
            chatBotService.saveLog(chatBot);

            return ResponseEntity.ok(chatResponse);
        } catch (IOException e) {
            response = "Error processing chat: " + e.getMessage();
            chatBot.setBotinput(response);
            chatResponse.setBotoutput(response);
            chatBotService.saveLog(chatBot);
            return ResponseEntity.status(500).body(chatResponse);
        }
    }
}
