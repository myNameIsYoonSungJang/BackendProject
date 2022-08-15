-- SQL문을 사용하여 서버를 재시작하면 데이터가 사라지는 h2 DB에 직접 값을 넣어줌
-- springBoot 2.5버전 이상에서는 data.sql이 바로 동작하지 않는다. 하여 application.properties에 특정 코드를 추가해주어야 함.
INSERT INTO article(id, title, content) VALUES (1, '가가가가', '1111');
INSERT INTO article(id, title, content) VALUES (2, '나나나나', '2222');
INSERT INTO article(id, title, content) VALUES (3, '다다다다', '3333');