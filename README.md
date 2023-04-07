# README

Created: 2023년 4월 5일 오후 1:57

# I. 서비스 소개

## 1. Overview

[한 줄 소개 (사실 두 줄)]

경매를 통해 중고 전자제품을 쟁취하세요! 합리적인 가격으로 구매할 수 있고, 어떤 걱정도 없이 간편하게 판매할 수 있습니다. 

## 2. 기획 배경

[한 줄 소개 (사실 세 줄)]

기존 중고 거래 플랫폼은 과도하게 가격 흥정을 하는 구매자, 물건을 먼저 이용해 보겠다는 구매자 등 여러 종류의 진상 구매자를 상대하는 불편함을 가지고 있습니다. 게다가 거래할 제품이 전자제품이라면 더욱더 많은 애로사항을 가집니다.

저희는 경매 방식을 중고 전자제품 거래에 접목시켜 중고 거래와 전자제품 거래 모두의 불편함을 해소하고자 본 서비스를 기획하게 되었습니다.

[구구절절 소개]

중고 전자제품을 사고 파는 것은 수많은 불편함을 안고 있습니다.

기존 중고거래 플랫폼을 이용하면 판매자는 무작정 가격을 깎으려는 사람, 물건을 먼저 이용해 보겠다는 사람 등 일명 ‘진상’을 상대하는 불편함을 겪습니다. 구매자는 원하는 물건을 다른 구매자에게 빼앗기지 않기 위해 매물이 올라오는 것만 시도때도 없이 기다리는 불편함을 감수합니다.

전자제품 매입점을 이용하는 것도 한계가 있습니다. 매입점마다 시세가 천차만별이라 판매자는 마음 편히 판매할 수가 없는 실정이며, 구매자는 일반적으로 매입점에서 제품을 구매할 수가 없을뿐더러 가능하다 해도 상태가 좋지 않은 물건이 대다수입니다.

저희는 이러한 불편함을 어떻게 해소할 수 있을지 고민하다가 경매를 떠올렸습니다.

경매는 가격 흥정 없이 오로지 입찰만으로 진행되는 특성을 지니고 있어 판매자는 진상을 상대하는 불편함이 사라집니다. 게다가 중고 시세를 정확히 알지 못해도 수요에 따라 가격이 자연스레 조정되기 때문에 시세 파악에 대한 부담도 덜 수 있습니다. 구매자 역시 원하는 가격에 입찰하며 합리적으로 구매하는 것은 물론이고, 물건을 뺏기지 않으려 매물이 올라오는 것을 무작정 기다리지 않아도 됩니다.

결론적으로 중고 전자제품 거래에 경매를 접목한다면 기존의 단점을 해결하고 사용자에게 편리함을 제공할 수 있을 것이라 생각해 기획하게 되었습니다.

## 3. 서비스 화면

### 메인페이지

- 소개 부분

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled.png)

- 가장 많은 포인트를 보유한 상위 5인

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%201.png)

- 역대 가장 많이 거래된 제품

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%202.png)

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%203.png)

### 마이페이지

- 입찰중 및 판매중 UI

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%204.png)

- 구매내역 및 판매내역 UI

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%205.png)

- 알림내역

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%206.png)

### 경매 목록 페이지

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%207.png)

### 경매 진행 페이지

- 제품 설명

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%208.png)

- 사용자별 입찰 기록 그래프

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%209.png)

- 제품 설명

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2010.png)

# II. 서비스 구조 & ERD

---

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2011.png)

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2012.png)

# III. 기술 스택

---

**📍 Frontend**

- React 18.2.0
- Recoil 0.7.7
- TypeScript 4.9.5
- Stomp.js 7.0.0
- Axios 1.3.4
- Stomp.js 7.0.0

**📍 DataBase**

- MySQL 8.0.32
- Redis

**📍 Infra**

- Docker version 23.0.1
- Nginx 1.18.0 (Ubuntu)
- Jenkins 2.387.1

**Backend - MSA**

**📍 Common (+ User Server)**

- Openjdk 11
- Lombok
- jjwt 0.11.2

**📍 Auction Server**

- Spring Boot 2.7.9
- Spring Data JPA
- Swagger 3.0.0

**📍 Bid Server** 

- Spring Kafka
- Spring Boot WebSocket
- Jackson
- Redisson 3.17.7

**📍 Notification Server**

- Spring Kafka
- Spring Boot WebSocket
- Jackson
- RocksDB 7.10.2

**📍 Streaming Server**

- Kafka Streams
- Spring Kafka
- Jackson
- RocksDB 7.10.2

**📍 Gateway Server**

- Spring Cloud Gateway

# IV. 서비스 컨텐츠 소개

---

## 메인 페이지

### 1. 가장 많은 포인트를 가진 상위 5인 소개

사용자 중 포인트를 가장 많이 갖는 상위 5인을 소개하는 부분입니다.

실제 서비스에서는 민감 개인정보를 공개하는 것은 절대 하지 말아야 할 행위지만, 가상의 포인트를 사용한다는 점에서 흥미를 이끌어 보고자 추가했습니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%201.png)

### 2. 현재까지 가장 많이 거래된 제품

현재까지 거래가 완료된 제품을 집계하여 보여주는 기능입니다.

사용자는 이를 통해 어떤 제품이 가장 많이 거래되었는지 확인할 수 있습니다.

- 역대 가장 많이 거래된 제품

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2013.png)

---

## 경매 목록 페이지

### 1. 진행중인 경매 목록 조회

페이지 입장 시 현재 진행중인 경매들이 나타납니다.

사용자는 현재 진행중인 경매들 및 해당 경매들의 남은 시간, 시작가, 입찰 단위, 현재가 등 다양한 정보를 확인할 수 있습니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2014.png)

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2015.png)

### 2. 카테고리 조회

좌측의 카테고리 메뉴를 클릭하여 특정 카테고리의 제품만 조회할 수 있습니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2016.png)

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2017.png)

### 3. 종료된 경매 목록 조회

좌측 메뉴 최하단의 ‘경매완료’를 클릭하여 종료된 경매들을 조회할 수 있습니다.

이 기능을 이용해 사용자는 어느 제품이 얼마에 팔렸는지 참고할 수 있습니다.

(정상적인 경매 올리고 사진사진)

### 4. 진행중인 경매 검색

상단 검색창에 키워드를 입력해 검색함으로써 원하는 조건의 경매를 조회할 수 있습니다.

키워드가 포함된 경매 제목, 제품명, 카테고리의 경매가 등장합니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2018.png)

---

## 경매 진행 페이지

### 1. 경매 정보 조회 + 찜꽁

제품 사진, 경매 제목, 제품명, 남은 경매 시간, 시작가, 입찰 단위, 현재가, 상세설명 등 해당 경매에 대한 모든 정보를 확인할 수 있습니다.

경매 제목 우측 상단에 있는 북마크 표시를 눌러 해당 경매를 관심 경매 목록에 추가할 수 있습니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2019.png)

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2020.png)

### 2. 입찰

입찰하기 버튼을 눌러 원하는 제품에 입찰할 수 있습니다.

입찰이 성공적으로 이루어지면 리더보드에 사용자의 이름, 입찰 시각, 입찰가가 올라옵니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2021.png)

### 3.  사용자별 입찰 추이

사용자가 입찰한 시각과 가격을 그래프로 시각화하여 제공합니다.

각 사용자마다 그래프가 제공되므로 입찰한 사용자의 인원에 따라 그려지는 그래프가 2개 이상일 수 있습니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2022.png)

### 4. 댓글

댓글을 작성, 수정, 삭제할 수 있습니다.

다른 댓글에 답글을 작성하는 것도 가능합니다.

사용자는 이 기능을 통해 물건의 상세한 정보를 판매자에게 요청할 수 있습니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2023.png)

---

## 마이페이지

### 1. 입찰중인 경매 조회

현재 진행중인 경매중 입찰한 이력이 있는 경매를 조회할 수 있습니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2024.png)

### 2. 구매한 내역 조회

사용자가 구매했던 제품의 내역을 확인할 수 있습니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2025.png)

### 3. 판매중인 경매 조회

사용자가 현재 판매중인 제품의 내역을 확인할 수 있습니다.

각 게시물을 누르면 해당 페이지로 이동합니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2026.png)

### 4. 판매한 내역 조회

사용자가 판매했던 제품의 내역을 확인할 수 있습니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2027.png)

### 5. 관심 경매 조회

관심 등록을 한 경매 목록을 확인하고 입장할 수 있습니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2028.png)

### 6. 알림 내역 조회

알림을 받을 수 있습니다.

알림의 종류는 구매 성공, 구매 실패, 판매 성공, 경쟁 입찰 등 네 종류입니다.

각 알림의 제품명을 누르면 해당 게시물로 이동합니다.

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2029.png)

# V. 협업 툴

---

### 1. Git Flow

- 브랜치 전략
    - 브랜치는 아래와 같이 4가지의 형태로 관리했습니다.
        1. `main` : 최종 release 될 브랜치
        2. `develop` : 각 feature가 merge 될 브랜치
        3. `feature` : 기능 구현 브랜치. (구현이 완료되면 develop 브랜치에 merge)
        4. `fix` : 오류 및 기타 사항을 수정하기 위한 브랜치
    
    - feature 및 fix 브랜치는 뒤에 `적용할 직무` 및 `브랜치의 목표`를 명시했습니다.
        
        (e.g. Backend 로그인의 경우 feature/be/login)
        
    
- 커밋 메시지 컨벤션
    - 커밋 메시지는 적용할 직무 및 커밋 유형을 말머리에 덧붙였습니다.
        
        (e.g. `[be/fix]` NullPointerException 수정)
        
    - 커밋 유형은 아래와 같이 정했습니다.
        
        
        | 커밋 유형 | 의미 |
        | --- | --- |
        | init | 프로젝트, 파일 생성 및 초기화 |
        | feat | 새로운 기능 추가 |
        | fix | 버그 수정 |
        | docs | 문서 수정 |
        | style | 코드 formatting, 세미콜론 누락, 코드 자체의 변경이 없는 경우 |
        | refactor | 코드 리팩토링 |
        | test | 테스트 코드, 리팩토링 테스트 코드 추가 |
        | chore | 패키지 매니저 수정, 그 외 기타 수정 ex) .gitignore |
        | design | CSS 등 사용자 UI 디자인 변경 |
        | comment | 필요한 주석 추가 및 변경 |
        | rename | 파일 또는 폴더 명을 수정하거나 옮기는 작업만인 경우 |
        | remove | 파일을 삭제하는 작업만 수행한 경우 |
        | !breaking change | 커다란 API 변경의 경우 |
        | !hotfix | 급하게 치명적인 버그를 고쳐야 하는 경우 |
        | build | 배포, Docker, Jenkins등 파일 수정 및 추가 |

### 2. Jira

- Jira 컨벤션
    - 기본 단위를 Epic으로 잡고 Task 및 Story로 나누어 작성하였습니다.
    - MSA 구조에 따라 여러 서버로 나누었기 때문에 Epic은 주로 서버를 기준으로 나누었습니다.

- 협업사항 및 결과
    - 월요일 아침마다 회의를 통해 금주에 해야 할 작업을 논의해 분배하였고, 금요일 오후마다 완료된 작업사항을 공유하며 차주의 작업 분배를 개선했습니다.
    - Story Point는 세분화된 관리를 위해 가급적 2, 3을 할당하고자 하였으나 큰 단일 작업의 경우 초과 할당을 하기도 했습니다.
    - 번다운 차트를 통해 개발이 유난히 더뎠던 부분이 있다면 그 이유와 개선사항을 분석하기도 했습니다.

- 번다운 차트 용두사미 되어 가는데 빼야 되나;
    
    ![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2030.png)
    
    ![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2031.png)
    
    ![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2032.png)
    
    ![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2033.png)
    
    ![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2034.png)
    
    ![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2035.png)
    
    ![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2036.png)
    

### 3. Notion

- 금주 공지사항 또는 이슈 공유
- 코드, Git, Jira 등 작업 툴 컨벤션 공유
- 팀원 간 유용한 팁 공유
- API 명세 및 툴 버전 명세

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2037.png)

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2038.png)

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2039.png)

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2040.png)

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2041.png)

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2042.png)

![Untitled](README%20a31fbca4f359417bb992010b2cb35ffd/Untitled%2043.png)

# VI. 팀원

---

| 이름 | 역할 |
| --- | --- |
| 전인덕 (PM) | Kafka |
| 김범식 | Frontend, UX/UI, UCC |
| 신민혜 | Backend |
| 오종석 | CI/CD, OAuth |
| 윤재휘 | Backend |
| 임혜진 | Backend, 기능 소개 영상 |