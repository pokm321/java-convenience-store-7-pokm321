# 우아한테크코스 프리코스 4주차: 편의점

편의점 결제 시스템을 구현합니다. 구매자의 할인 혜택과 재고 상황을 고려하여 최종 결제 금액을 계산합니다.

<br>

## 기능구현
- md 읽기
  - 현재 재고를 확인하여 객체로 불러오기
  - 현재 프로모션 상태를 enum으로 불러오기
- 기본 결제
  - 사용자가 입력한 상품의 가격과 수량을 곱하여 계산
  - 각 상품의 재고 수량을 고려하여 결제 가능 여부 확인
  - 고객이 상품을 구매할 때마다, 결제된 수량만큼 재고에서 차감
<br>

- 프로모션 할인
  - 오늘 날짜가 프로모션 기간 내에 포함된 경우에만 할인 적용
  - 프로모션은 N개 구매 시 1개 무료 증정(Buy N Get 1 Free)의 형태
  - 1+1 또는 2+1 프로모션이 각각 지정된 상품에 적용
  - 각 상품은 최대 하나의 프로모션만 가능
  - 프로모션 혜택은 프로모션 재고 내에서만 적용
  - 프로모션 기간 중이라면 프로모션 재고를 우선적으로 차감, 프로모션 재고가 부족할 경우에는 일반 재고 사용
  - 프로모션 적용이 가능한 상품에 대해 고객이 해당 수량보다 적게 가져온 경우, 필요한 수량을 추가로 가져오면 혜택을 받을 수 있음을 안내한다.
  - 프로모션 재고가 부족하여 일부 수량을 프로모션 혜택 없이 결제해야 하는 경우, 일부 수량에 대해 정가로 결제하게 됨을 안내
  <br>

- 멤버십 할인
  - 프로모션 적용 후 남은 금액에 대해 멤버십 할인 적용
  - 멤버십 회원은 프로모션 미적용 금액의 30% 할인
  - 멤버십 할인의 최대 한도는 8,000원
  <br>

- 영수증 출력
  - 구매 내역과 산출한 금액 정보
  - 영수증은 고객의 구매 내역과 할인을 요약하여 출력
  - 영수증 항목
    - 구매 상품 내역: 구매한 상품명, 수량, 가격
    - 증정 상품 내역: 프로모션에 따라 무료로 제공된 증정 상품의 목록
    - 금액 정보
      - 총구매액: 구매한 상품의 총 수량과 총 금액
      - 프로모션할인: 프로모션에 의해 할인된 금액
      - 멤버십할인: 멤버십에 의해 추가로 할인된 금액
      - 내실돈: 최종 결제 금액
  - 영수증의 구성 요소를 보기 좋게 정렬하여 고객이 쉽게 금액과 수량을 확인할 수 있게 한다.
  - 영수증 출력 후 추가 구매 여부 선택
  <br>

- 예외 처리
  - 사용자가 잘못된 값을 입력할 경우 IllegalArgumentException를 발생시키고, "[ERROR]"로 시작하는 에러 메시지를 출력 후 그 부분부터 입력을 다시 받는다.
<br>

## 클래스
- Product
  - name
  - number
  - isPromotion