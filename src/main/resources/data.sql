-- SQL문을 사용하여 서버를 재시작하면 데이터가 사라지는 h2 DB에 직접 값을 넣어줌
-- springBoot 2.5버전 이상에서는 data.sql이 바로 동작하지 않는다. 하여 application.properties에 특정 코드를 추가해주어야 함.
INSERT INTO article(id, title, content) VALUES (1, '가가가가', '1111');
INSERT INTO article(id, title, content) VALUES (2, '나나나나', '2222');
INSERT INTO article(id, title, content) VALUES (3, '다다다다', '3333');

-- article 더미 데이터
INSERT INTO article(id, title, content) VALUES (4, '당신의 인생 영화는?', '댓글ㄱ');
INSERT INTO article(id, title, content) VALUES (5, '당신의 소울 푸드는?', '댓글ㄱㄱ');
INSERT INTO article(id, title, content) VALUES (6, '당신의 취미는?', '댓글ㄱㄱㄱ');

-- comment 더미 데이터
-- 4번 게시글의 댓글들
INSERT INTO comment(id, article_id, nickname, body) VALUES (1, 4, 'Park', '굿 윌 헌팅');
INSERT INTO comment(id, article_id, nickname, body) VALUES (2, 4, 'Kim', '캐치 미 이프 유 캔');
INSERT INTO comment(id, article_id, nickname, body) VALUES (3, 4, 'Choi', '쇼생크 탈출');
-- 5번 게시글의 댓글들
INSERT INTO comment(id, article_id, nickname, body) VALUES (4, 5, 'Park', '치킨');
INSERT INTO comment(id, article_id, nickname, body) VALUES (5, 5, 'Kim', '샤브샤브');
INSERT INTO comment(id, article_id, nickname, body) VALUES (6, 5, 'Choi', '초밥');
-- 6번 게시글의 댓글들
INSERT INTO comment(id, article_id, nickname, body) VALUES (7, 6, 'Park', '조깅');
INSERT INTO comment(id, article_id, nickname, body) VALUES (8, 6, 'Kim', '유튜브');
INSERT INTO comment(id, article_id, nickname, body) VALUES (9, 6, 'Choi', '코딩');