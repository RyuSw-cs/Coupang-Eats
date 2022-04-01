# Coupang-Eats

# 쿠팡이츠

**2022.03.19 1일차 개발일지**
  - SplashActivity 구현
  - StartActivity ViewPager2, CircleIndicator 연결
  - 위치 권한 요청 기능 구현

**2022.03.20 2일차 개발일지**
  - 권한 미허용 시 나오는 NonPermissionActivity 구현
  - 로그인 정보가 없다면 나오는 LoginBottomSheet 구현
  - LoginActivity 구현
  - JoinActivity 구현

**2022.03.21 3일차 개발일지**
  - 가게 리스트가 나오는 HomeFragment 구현
  - 가게 리스트를 표현하는 ItemHomeStore 구현
  - Naver API 연동
  - 좌표값을 통해 도로명 주소를 반환하는 Naver ReverseGeo API 구현
  - 주소를 설정하는 LocationActivity 구현
  - 검색 시 나오는 Fragment 구현
  - Naver Map이 나오는 LocationMapActivity 

**2022.03.22 4일차 개발일지**
  - 앱 첫 실행 구분
  - Kakao Local API를 사용하여 좌표값으로 주소 설정
  - Kakao Local API를 사용하여 키워드를 통한 위치 검색 결과 반환
  - LocationActivity 수정
  - 피드백 반영
    - 버튼 그림자 수정
    - 글자 크기 수정
    - MainActivity 오류 수정 
  
**2022.03.23 5일차 개발일지**
  - 가게 상세보기 액티비티 개발 시작
  - 라이징테스트 서버 연결
  - 위치 검색 후 데이터를 리스트에 연결
  - 선택된 위치의 상세설정을 위한 LocationAddActivity 구현
  - 위치 선택 Activity 구현

**2022.03.24 6일차 개발일지**
  - 현재 위치에서 Naver Map을 보여주는 MapActivity 구현
  - 로그인 기능 연동
  - 회원가입 기능 연동
  - SharedPreferences에 JWT를 저장하여 자동 로그인 구현
  - 홈 화면 API 연동
  - 주소 API 연동
  - 홈 화면 배너 연동 및 애니메이션 추가
  - 홈 화면 카테고리 크기 수정
  - 가게 상세보기 Activity 구현, 서버 연동 

**2022.03.25 7일차 개발일지**
  - UI 수정
  - 권한 설정 오류 수정
  - 회원가입 로직 수정
  - 로그아웃 추가
  - 홈 화면 정렬 레이아웃 추가
  - 홈 화면, 지도 서버 통신 로직 수정

**2022.03.26 8일차 개발일지**
  - UI 수정
  - Firebase 연동 및 테스트

**2022.03.27 9일차 개발일지**
  - UI 수정
  - 지도 추가에서 맵 누를 시 오류 수정
  - 가게 상세 정보 연동
  - 가게 상세 정보 리스트 추가
  - 가게 상세 사진 리뷰 추가

**2022.03.28 10일차 개발일지**
  - UI 수정
  - 음식 상세보기 레이아웃 추가
  - 카트 추가 API 연동
  - 하단 카트 레이아웃 

**2022.03.29 11일차 개발일지**
  - UI 수정
  - 주문하기 레이아웃 추가
  - 주문 API 연동
  - 카트 삭제, 수정 및 새로운 카트 추가 API 연동

**2022.03.30 12일차 개발일지**
  - UI 수정
  - 주문 화면에서 포장을 누르면 나오는 지도 추가
  - 주문 후 배달 중인 레이아웃 추가
  - 주문하기 배달, 포장 프래그먼트 추가

**2022.03.31 13일차 개발일지**
  - UI 수정
  - 주문내역 API 연동
  - 주문 후 상태 API 연동
  - 리뷰 레이아웃 구현
  - 주문 내역 레이아웃 구현
  - 즐겨찾기 레이아웃 추가

**2022.04.01 14일차 개발일지**
  - 즐겨찾기 API 연동
  - 즐겨찾기에 따른 툴바 아이콘 변경
  - 재주문 API 연동
  - 주문 내역 리스트 구현
  - 자동로그인 API 연동
  - 리뷰 작성 레이아웃 구현
  - 영수증 구현
  - 마이페이지 레이아웃 구현
  - 위치 수정 및 즐겨찾기 오류 




## 1차 스크럼 회의(2022-03-21)

### 👨‍💻현재 진행 상황

```cpp
현재 진행된 개발 현황을 알려주세요.
```

- **Client**
    - API 연결 전 작업 진행
    - 레이아웃 구축
    <img width="200" src="https://user-images.githubusercontent.com/72602912/159365265-1599e0fd-9449-4146-89e3-624001150741.png">
    <img width="200" src="https://user-images.githubusercontent.com/72602912/159365273-f6248736-6f0a-4de5-ba2c-854c437ab0e4.png">
    <img width="200" src="https://user-images.githubusercontent.com/72602912/159365288-45eac8ad-811b-48b7-8937-d6aca5eef324.png">
    <img width="200" src="https://user-images.githubusercontent.com/72602912/159365299-1cb13e23-c20b-4d4b-84bc-dd499952ecb1.png">
    <img width="200" src="https://user-images.githubusercontent.com/72602912/159365308-c56439d5-a12c-4bc1-86a2-353a1706ca8a.png">
    <img width="200" src="https://user-images.githubusercontent.com/72602912/159365369-d3f69a06-c490-4f7c-85cf-87d597a7d7de.png">
    <img width="200" src="https://user-images.githubusercontent.com/72602912/159365377-d39d442b-dda4-4fb4-ae55-55e0bc509f3a.png">

- **Server**
    - EC2 인스턴스 구축
    - RDS 데이터베이스 구축
    - ERD 설계 완료
    - RESTful API 리스트업
    - 회원가입 API 구현
    - 로그인 API 구현
    - 홈 화면 조회 API 구현 (거리 관련 제외) 
    - 가게 상세화면 조회 API 구현



### 👨‍💻개발 시 궁금했던 점

```cpp
개발을 하며 궁금한 것이 있나요?
```

- **Client**
    - 아직 서버 연결 전임, 연결할 때 많이 생길 것 같음

- **Server**
  - 사장님 입장에서의 API도 만들어야 할까? -> 사장님 앱이 따로 있으므로, 더미 데이터만 집어 넣는 쪽으로 하기로 함.
  - 회원가입 시, 사용자의 위치 정보를 쿼리 스트링으로 보내도 되는가? -> 회원 가입 시에는 필요 없을 것 같음.
  - 로그인 시 위치정보를 Response 해주어야 하는가? -> 로그인 시에 위치 정보가, 위치 권한을 동의한 위치로 설정되므로 안 해주어도 될 것 같다.
  - 가게 카테고리가 일대일이 아닌 일대 다이기 때문에 테이블을 따로 해주어야 할 것 같다.
  - 가게 매장 정보, 원산지 정보 등을 저장 하는 것은 DB에서 항목별로 나누어 관리해야 하는가? -> 피드백 시간에 여쭤 보기로 함.
  - 가격에 원 단위를 붙이는 것은 서버에서 해주어야 하는가? -> int형태로 넘겨주면 클라이언트에서 작업하기로 함
  - 매장과 사용자와의 거리 계산 -> 매장의 위도, 경도 정보를 넘겨주면 클라이언트에서 작업해주기로 함.



## 2차 스크럼 회의(2022-03-27)

### 👨‍💻현재 진행 상황

```cpp
현재 진행된 개발 현황을 알려주세요.
```

- **Client**
    - 레이아웃 구축 후 바로 연동
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508173-e76d4f31-31e1-454d-99a7-6e0ced14b606.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508258-526b469f-7f8d-4f34-8237-a65d69e24477.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508280-32acae09-7776-4d96-82b6-e7be05a6a67c.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508303-bac0fbb3-8844-4aea-9cc4-21c50c036083.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508327-4815b553-fbdb-4951-9abd-928ab28b2f80.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508348-4c33d48a-bc1b-48e9-a8b1-081ac1aba692.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508360-5c2f56e1-ebec-4b1d-81a8-57d2232353d6.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508387-37016446-1383-47b3-89e7-65fa42e24807.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508552-ac1e3fb7-ef23-4dbf-896a-c69025b879a5.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508539-482c501a-b13f-465d-8cb7-1b11452fc0d8.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508406-c3258cd9-91ff-4515-ac0d-0ca293292f75.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508425-2540746c-b8f6-49b0-89f2-6fea3b3f9378.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508470-63b6c596-2fcd-4829-a12d-d5e71ff27403.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508514-1240f9c2-6500-4e2f-b923-bc25b78d93d3.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508612-9a548a11-1a51-4307-bd78-ad0f2eb4d79c.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508570-b50298fa-ad59-4055-b42e-2e64cc857cac.png">
    <img width="180" src="https://user-images.githubusercontent.com/72602912/160508599-bfc0f0ae-cbe1-4997-a07a-9c1d840ebac4.png">


- **Server**
  - 배달 카트 관련 API 구현
  - 주문 관련 API 구현
  - 즐겨 찾기 API 구현
  - 할인 쿠폰 관련 API 구현 
  - 세부적인 사항은 클라이언트분과 상의하면서 조금씩 수정해 나가야 함.


