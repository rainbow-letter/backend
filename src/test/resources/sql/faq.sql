SET
FOREIGN_KEY_CHECKS = 0;

INSERT INTO faq (id, summary, detail,
                 visibility, sequence, created_at, updated_at)
VALUES (1, '무지개 편지는 무슨 서비스인가요?', '무지개 편지는 무지개 다리를 건넌 반려동물과 편지를 주고받는 서비스입니다.',
        true, 1, '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000'),
       (2, '답장이 온 건 어떻게 알 수 있나요?', '답장이 도착하면 등록하신 이메일 주소로 메일을 보내드려요.',
        true, 2, '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000');

SET
FOREIGN_KEY_CHECKS = 1;
