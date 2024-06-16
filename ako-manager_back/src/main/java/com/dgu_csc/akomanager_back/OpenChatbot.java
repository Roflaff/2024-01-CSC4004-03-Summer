package com.dgu_csc.akomanager_back;



import java.io.*;
import java.util.Scanner;
import java.time.*;


public class OpenChatbot {
    final static String DefaultPath = System.getProperty("user.dir") + "/ako-manager_back/src/main/";
    final static String ChatbotLogFormat =  LocalDate.now() + " " + LocalTime.now() + "\t";
    public static void main(String[] args) throws IOException, InterruptedException {
        try {

            int i;
            File ChatLog = new File (DefaultPath + "resources/MyLog3.txt");
            if ((ChatLog.exists()) && (ChatLog.canRead())) {
                // Mylog3.txt가 존재하는 경우 모든 번호들을 앞으로 한 개씩 당김 -> 원래 1번이었던 애는 삭제됨  ! 3번이 가장 최신 내용입니다.
                for ( i = 2 ; i < 4 ; i++) {
                    File upper = new File(DefaultPath + "resources/MyLog" + i + ".txt");
                    File lower = new File(DefaultPath + "resources/MyLog" + (i-1) + ".txt");
                    upper.renameTo(lower);
                }

            }

            String pythonScriptPath = DefaultPath + "Python/ChatbotCode.py";

            String[] command = {"python3", pythonScriptPath};

            FileWriter ChatLogWriter = new FileWriter(ChatLog, true);  // 챗봇 로그 기록을 위하여 추가한 FileWriter


            System.out.println("아코 멘토에게 물어보고 싶은 것을 물어보세요");
            ProcessBuilder pb = new ProcessBuilder(command);

            while (true) {
                Process process = pb.start();
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(process.getOutputStream()));
                String Username = "김윤서";
                ChatLogWriter.write(ChatbotLogFormat + Username + " : ");
                writer.println(Username);
                writer.flush();


                Scanner scanner = new Scanner(System.in);
                String UserInput;

                System.out.print(Username + " : ");
                UserInput = scanner.nextLine();
                ChatLogWriter.write(UserInput + "\n");
                ChatLogWriter.flush();
                writer.println(UserInput);
                writer.flush();

                if (UserInput.equals("대화종료")) {
                    System.out.println("또 만날 기회를 기다리고 있을게요! ❤️");
                    ChatLogWriter.write(ChatbotLogFormat + "아코 멘토 : 또 만날 기회를 기다리고 있을게요! ❤️");
                    break;
                }

                String line;
                System.out.print("아코 멘토 : ");
                ChatLogWriter.write(ChatbotLogFormat + " " + "아코 멘토 : ");


                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    ChatLogWriter.write(line);              // 줄대로 읽게 할 거라 이렇게 씀
                }
                ChatLogWriter.write("\n");
                ChatLogWriter.flush();

            }
            ChatLogWriter.close();

        } catch (IOException e) {e.printStackTrace();}
    }
}
