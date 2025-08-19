SET FOREIGN_KEY_CHECKS = 0;

INSERT INTO pet(id, user_id, name, species, owner, personalities,death_anniversary,image, created_at, updated_at)
VALUES (1, 1, '콩이', '고양이', '형아', '활발한,잘삐짐', '2023-01-01',
        'objectKey', '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000'),
       (2, 1, '미키', '강아지', '엄마', '', '2023-01-01',
        null, '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000');

SET FOREIGN_KEY_CHECKS = 1;
