# Exchange-wallet

Spring Boot를 이용한 트레이딩 입출금 서버 제작

## Developer

- 👩‍💻 [HashCitrine](https://github.com/HashCitrine) (`webflux, oauth, MSA` 구현 및 개발 흐름 기획)
- 👩‍💻 [Soo-ss](https://github.com/Soo-ss) (`api, oauth, wallet` 구현)

## 사용 기술

- Spring Boot
- PostgreSQL
- Kafka
- Lombok

※ Kafka는 Docker container 이용.

## 개발한 서버 목록

- exchange-api
- exchange-oauth
- exchange-eureka
- exchange-gateway
- exchange-webflux
- exchange-wallet

😀 `exchange-api, exchange-oauth, exchange-wallet, exchange-webflux` 4개의 서버를 관리하기 위해, `exchange-eureka, exchange-gateway`를 통하여 MSA를 구현했습니다.

## 입출금 및 주문 요청

---

### ❓ Kafka 요청 서비스 흐름

`exchange-api => exchange-oauth => exchange-wallet`

1. exchange-oauth에 토큰 발급을 요청하여 토큰을 발급받습니다.
2. 입출금을 하기 위해, 요청 id와 토큰을 kafka에 전송합니다.
3. exchange-oauth는 토큰을 검증 후, 검증 된 것만 exchange-wallet에 요청 id만 kafka에 전송합니다.
4. exchange-wallet은 요청 id를 kafka를 통해 받아서 입출금을 수행합니다.
5. 주문(order)요청도 동일한 흐름입니다.

---

### ⭕ 요청 성공

✔ `submitDw` 토픽에서 가져옵니다.

✔ `WalletService`의 `depositAndWithdraw`에서 처리합니다.

✔ 요청이 성공하면 `success deposit or witdraw` 를 리턴합니다.

---

### ❌ 요청 실패

✔ 요청 실패시, 로그로 남깁니다.

✔ 각종 오류로 인해 정상적으로 DB에 저장되지 않았거나 kafka로 전송되지 못한 경우, '요청실패' 상태로 처리합니다. (id 중복체크 실시하여 insert/update 판단)
