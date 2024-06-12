import sys  # library to get flushed data from Java
import openai

username = sys.stdin.readline().strip()  # getting username from first flushed data

openai.api_key = ""
# paste your ChatGPT Openai api key between the double quotation mark


def calculate_match_score(rule_words, request):
    match_score = -1
    for word in rule_words:
        if word in request:
            match_score += 1
    return match_score


def return_response_index(request):
    best_match_score = -1
    best_response = "잘 이해하지 못했어요. 다른 단어를 사용해서 질문해 주세요"  # best response set by default

    # finding the best response from getting keywords of the request of user
    for idx, rule_words in enumerate(chat_rules):
        current_score = calculate_match_score(rule_words, request)
        if current_score > best_match_score:
            best_match_score = current_score
            best_response = idx

    # if none of above matches, send user's input to ChatGPT
    if best_match_score == -1:
        response = openai.chat.completions.create(
            messages=[
                {"role": "user",
                 "content": request}
            ],
            model="gpt-3.5-turbo",
        )
        best_response = response.choices[0].message.content
    return best_response


chat_rules = [['안녕', '반가워'],
              ['이름', '소개'],
              ['졸업', '남은', '들어야'],
              ['졸업', '기준', '어떻게'],
              ['추천', '시간표', '짜줄래', '짜줘'],
              ['고마워', '굿', '땡큐', '감사', '좋아'],
              ['선이수', '먼저', '전', '반드시'],
              ['부족', '더', '최소', '필수', '전체'],
              ['지금까지', '학점', '들었어', '내역', '전에', '전체'],
              ['지금까지', '학점', '들었어', '내역', '교양', '전에'],
              ['지금까지', '학점', '들었어', '내역', '전공', '전에'],
              ['전공', '더', '최소', '필수', '부족'],
              ['교양', '더', '최소', '필수', '부족'],
              ['전공', '총', '학점'],
              ['교양', '총', '학점']]


UserInput = sys.stdin.readline().strip()        # Get UserInput here(Input sent from Java)
print(return_response_index(UserInput))                 # to send index to Java, required to print
