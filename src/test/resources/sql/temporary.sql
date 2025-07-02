SET
FOREIGN_KEY_CHECKS = 0;

INSERT INTO temporary (id, pet_id, user_id, session_id, content, status,
                       created_at, updated_at)
VALUES (1, 1, 1, 'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', '임시 저장 편지 본문', 'DELETE',
        '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000'),
       (2, 1, 1, 'bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb', '임시 저장 편지 본문', 'SAVE',
        '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000');

SET
FOREIGN_KEY_CHECKS = 1;
