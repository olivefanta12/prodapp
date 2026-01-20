# Socket + JSON 상품 CRUD (MyClient / MyServer)

이 프로젝트는 **자바 소켓(Socket)** 으로 클라이언트와 서버가 통신하고,  
서로 주고받는 데이터를 **JSON(Gson)** 으로 직렬화/역직렬화해서  
상품 목록/상세조회/등록/삭제 기능을 수행하는 예제입니다.

---

## 1. 프로젝트 구성

### ✅ 서버 (MyServer)
- `ServerSocket(20000)` 으로 포트 20000에서 대기
- 클라이언트가 연결되면 `accept()` 후 계속 요청을 받음
- 클라이언트가 보낸 한 줄(JSON 문자열)을 읽어서 `RequestDTO` 로 변환
- `method` 값에 따라 서비스 호출 후 `ResponseDTO` 로 응답 생성
- 응답도 JSON 문자열로 변환해서 한 줄로 보내줌

### ✅ 클라이언트 (MyClient)
- `Socket("localhost", 20000)` 으로 서버에 접속
- 키보드 입력을 받아 요청을 만들고 JSON으로 서버에 전송
- 서버 응답(JSON 문자열)을 한 줄 읽어 출력

---

## 2. 통신 데이터 구조 (DTO)

### RequestDTO (요청)
클라이언트 → 서버로 보내는 요청 JSON 형태

- `method` : `"get" | "post" | "delete"`
- `queryString` : GET/DELETE에서 `id` 전달할 때 사용
- `body` : POST에서 `name, price, qty` 전달할 때 사용

예시:
json
{
  "method": "get",
  "queryString": {"id": 1},
  "body": null
}

## 3. 지원 기능 (CRUD)

서버는 아래 로직으로 동작합니다.

(1) 전체 상품 목록 조회

조건: method = "get" AND queryString == null

호출: service.상품목록()

(2) 특정 상품 상세 조회

조건: method = "get" AND queryString != null (id 존재)

호출: service.상품상세(id)

(3) 상품 삭제

조건: method = "delete" (id 필요)

호출: service.상품삭제(id)

삭제 실패/예외 발생 시 "invalid request"

(4) 상품 등록

조건: method = "post" (body 필요)

body: name, price, qty

호출: service.상품등록(name, price, qty)

4. 실행 방법
1) 서버 실행

먼저 서버를 실행해야 합니다.

실행 클래스: server.MyServer

포트: 20000

서버가 실행되면 클라이언트 연결을 기다립니다.

2) 클라이언트 실행

그 다음 클라이언트를 실행합니다.

실행 클래스: client.MyClient

서버 주소: localhost:20000

5. 사용 방법 (클라이언트 명령어)

클라이언트에서 아래 형식으로 입력하면 서버에 요청을 보냅니다.

✅ 종료
exit

✅ 전체조회
get


현재 코드 기준으로 get만 입력하면 parts.length == 1이라서 qs/body가 null로 들어가고, 서버에서 상품목록으로 처리됩니다.

✅ 상세조회 (id로 조회)
get 1

✅ 삭제 (id로 삭제)
delete 1

✅ 등록 (이름 가격 수량)
post 콜라 1500 10

## 6. 내부 동작 흐름 (한 줄 요약)

클라이언트가 입력 → RequestDTO 생성

Gson으로 JSON 변환 → 서버로 전송

서버가 JSON 수신 → RequestDTO 파싱

method에 따라 서비스 실행

ResponseDTO 생성 → JSON 변환 → 클라이언트로 응답

클라이언트가 응답 출력

## 7. 참고 / 주의사항

이 예제는 HTTP가 아니라 직접 만든 규칙(프로토콜) 로 통신합니다.

요청/응답은 반드시 한 줄(JSON + \n) 형태로 주고받습니다.

서버는 exit를 받으면 while을 종료하고 연결을 끝냅니다.

post 요청에서 body 값이 없거나 형식이 틀리면 서버에서 예외가 날 수 있습니다.

## 8. 예시 실행 화면

클라이언트:

get 숫자, delete 숫자, post 이름 가격 갯수 :
post 콜라 1500 10
server : {"status":"ok","data":null}

get 숫자, delete 숫자, post 이름 가격 갯수 :
get
server : {"status":"ok","data":[...]}