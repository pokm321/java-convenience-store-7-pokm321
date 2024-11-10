# 우아한테크코스 프리코스 4주차: 편의점 :convenience_store:

우아한테크코스의 4주차 프리코스로, 편의점 결제 시스템을 구현한 과제입니다.
구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산하고, 영수증을 출력합니다.

<img width="256" src="https://github.com/user-attachments/assets/838185bc-e48f-4fe3-898b-77fcdea003c1">
<br>

## 기능구현

- md 읽기
    - 현재 재고를 확인하여 객체로 불러오기
    - 현재 프로모션 상태를 객체로 불러오기
    - 현재 재고 출력하기
      <br>

- 기본 결제
    - 각 상품의 재고 수량을 고려하여 결제 가능 여부 확인
    - 사용자가 입력한 상품의 가격과 수량을 곱하여 계산
    - 고객이 상품을 구매할 때마다, 결제된 수량만큼 재고에서 차감
      <br>

- 프로모션 할인
    - 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인 적용
    - 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태
    - 각 상품에 최대 하나의 1+1 또는 2+1 프로모션 적용
    - 프로모션 혜택은 프로모션 재고 내에서만 적용
    - 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감, 프로모션 재고가 부족할 경우에는 일반 재고 사용
    - 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져올지 선택
    - 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제할지, 제외할지 선택
      <br>

- 멤버십 할인
    - 멤버십 적용 선택
    - 멤버십 회원은 프로모션 미적용된 상품의 금액의 30% 할인
    - 멤버십 할인의 최대 한도는 8,000원
      <br>

- 영수증 출력
    - 구매 내역, 증정품, 구매액, 할인을 요약하여 출력
        - 구매 내역: 구매한 상품명, 수량, 가격
        - 증정 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록
        - 금액 정보
            - 총구매액: 구매한 상품의 총 수량과 총 금액
            - 행사할인: 프로모션에 의해 할인된 금액
            - 멤버십할인: 멤버십에 의해 추가로 할인된 금액
            - 내실돈: 최종 결제 금액
    - 영수증 출력 후 추가 구매 여부 선택
      <br>

- 예외 처리
    - 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException를 발생시키고, "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.
      <br>

<br>

### 실행 결과 예시

```
안녕하세요. W편의점입니다.
현재 보유하고 있는 상품입니다.

- 콜라 1,000원 10개 탄산2+1
- 콜라 1,000원 10개
- 사이다 1,000원 8개 탄산2+1
- 사이다 1,000원 7개
- 오렌지주스 1,800원 9개 MD추천상품
- 오렌지주스 1,800원 재고 없음
- 탄산수 1,200원 5개 탄산2+1
- 탄산수 1,200원 재고 없음
- 물 500원 10개
- 비타민워터 1,500원 6개
- 감자칩 1,500원 5개 반짝할인
- 감자칩 1,500원 5개
- 초코바 1,200원 5개 MD추천상품
- 초코바 1,200원 5개
- 에너지바 2,000원 5개
- 정식도시락 6,400원 8개
- 컵라면 1,700원 1개 MD추천상품
- 컵라면 1,700원 10개

구매하실 상품명과 수량을 입력해 주세요. (예: [사이다-2],[감자칩-1])
[콜라-3],[에너지바-5]

멤버십 할인을 받으시겠습니까? (Y/N)
Y 

==============W 편의점================
상품명		수량	금액
콜라		3 	3,000
에너지바 		5 	10,000
=============증	정===============
콜라		1
====================================
총구매액		8	13,000
행사할인			-1,000
멤버십할인			-3,000
내실돈			 9,000

감사합니다. 구매하고 싶은 다른 상품이 있나요? (Y/N)
N
```

<br>

## 요구사항

### 주요 프로그래밍 요구 사항

- [x] indent(인덴트, 들여쓰기) depth는 2까지만 허용
- [x] 3항 연산자 금지
- [ ] 함수(또는 메서드)가 한 가지 일만 하며, 10줄을 넘어가지 않도록
- [x] else 예약어 및 switch/case 금지
- [x] Java Enum 활용
- [x] JUnit 5와 AssertJ를 이용하여 구현한 기능에 대한 단위 테스트. 단, UI(System.out, System.in, Scanner) 로직은 제외
- [x] 입출력을 담당하는 클래스 구현
- [x] `camp.nextstep.edu.missionutils` 의 `DateTimes.now()` 및 `Console.readLine()` 활용
  <br>

### 명확하지 않은 요구사항에 대한 정리

- 중복된 상품에 대한 주문을 나눠서 넣을 수 있다.
    - 예시: `[사이다-3][콜라-1][사이다-4]` 는 `[사이다-7][콜라-1]` 과 같다.
      <br>

- 주문 개수는 0이나 음수가 될 수 없다.
    - 예시: `[사이다-0][콜라-1]` 은 오류이다.
      <br>

- 상품의 프로모션 기간이 아닌 경우, 일반재고를 먼저 사용하고, 일반재고가 부족한 경우 프로모션 재고를 사용한다. (즉, 프로모션 기간과 재고사용순서가 반대이다.)
    - 예시: 콜라의 프로모션 재고 8개, 일반재고 7개인경우, 프로모션 기간이 아닐때의 콜라 10개 주문이후 남은 재고는 프로모션 5개, 일반 재고없음 이다.
      <br>

- 프로모션 기간의 `start_date`과 `end_date`은 해당 날짜도 포함한다.
    - 예시: `end_date`이 `2024-12-31` 이라면 2024년 12월 31일 23시 59분도 프로모션 기간이다.
      <br>

- 탄산2+1 프로모션이 적용되는 경우 1개만 주문이 들어오면 따로 수량추가 여부를 묻지 않는다.
    - 예시: `[콜라-1]` 일때 2개를 추가할 것인지 묻지 않는다.
      <br>

- 영수증의 실행 결과 예시에서 디테일한 공백이나 정렬까지는 따라하지 않는다.
    - 예시1: 실행 결과 예시에서는 `\t`를 사용하여 정렬하였으나, 다른 방법으로 구현한다.
    - 예시2: 예시에서 금액들의 정렬이 왼쪽+오른쪽 혼합되어 있는데, 이 부분 역시 오른쪽 정렬로 통일한다.
      <br>
  
