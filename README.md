Mobile Programming Personal HomeWork
===============
20181683 임중혁
--------------

Screenshots
-------------
<div>
  <img width="200" src="https://user-images.githubusercontent.com/36337335/66752686-f9036880-eecc-11e9-81fe-b1d529859cac.jpg">
  <img width="200" src="https://user-images.githubusercontent.com/36337335/66752820-526b9780-eecd-11e9-890f-e326491449f6.jpg">
  <img width="200" src="https://user-images.githubusercontent.com/36337335/66752852-69aa8500-eecd-11e9-8687-14260ba72e84.jpg></div>
 


## PrefManager.class
1. Shared Preference 생성 및 저장, 불러오기
2. String to JsonArray 기능 제공
3. User Data를 json 형식으로 관리
$ 형식 => {"id":"idData", "pass":"password", "name":"userName", "phone":"phoneNumber", "Address":"Adress"}


## RegisterActivity.class
1. 회원가입 기능 수행
2. Id 정규식 제어 (only Alphabet + Number), 중복 체크
3. Password 정규식 제어 (only Alphabet + Number + Special Character)
4. Togle Button을 이용한 Register Agreement 기능


## LoginActivity.class
1. Id, Password 유효성 검사


## UserData.class
1. MainActivity의 ListView의 데이터 셋

## UerAdapter.class
1. MainActivity의 ListView의 item View 구성


## MainActivity.class
1. 회원가입된 모든 유저의 ID와 Password를 ListView의 아이템으로 출력
2. ListView의 아이템을 클릭하여 유저 삭제 가능
3. 버튼클릭으로 모든 유저 삭제 가능
