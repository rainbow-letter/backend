SET
FOREIGN_KEY_CHECKS = 0;

INSERT INTO user(id, email, password,
                 phone_number, role, status, provider, provider_id,
                 last_logged_in, last_changed_password,
                 created_at, updated_at)
VALUES (1, 'user@mail.com', '$2a$10$MOjLsvAi7wDoJn5d3oTncuC.0eX.q8s7.jlkCMmxwrxMFBn48kJIW',
        '01012345678', 'ROLE_USER', 'ACTIVE', 'NONE', 'NONE',
        '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000',
        '2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000'),
       (2, 'admin@mail.com', '$2a$10$MOjLsvAi7wDoJn5d3oTncuC.0eX.q8s7.jlkCMmxwrxMFBn48kJIW',
        '01011223344', 'ROLE_ADMIN', 'ACTIVE', 'NONE', 'NONE',
        '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000',
        '2023-01-01 12:00:00.000000', '2023-01-01 12:00:00.000000');

SET
FOREIGN_KEY_CHECKS = 1;
