SET
    FOREIGN_KEY_CHECKS = 0;

INSERT INTO ai_config(id, useabtest, select_prompt, created_at, updated_at)
VALUES (1, false, 'A', '2024-01-01 12:00:00.000000', '2024-01-01 12:00:00.000000');

INSERT INTO ai_prompt(id, config_id, type, provider, model, `system`, user, parameters)
VALUES (1, 1, 'A', 'OPENAI', 'gpt-4o', 'system', '사용자 %s님의 반려동물 %s', 'PET_OWNER,PET_NAME'),
       (2, 1, 'B', 'OPENAI', 'gpt-4o', 'system', '사용자 %s님의 반려동물 %s', 'PET_OWNER,PET_NAME');

INSERT INTO ai_prompt_option(frequency_penalty, max_tokens, presence_penalty, temperature, topp,
                             created_at, id, prompt_id, updated_at, stop)
VALUES (0.0, 0, 0.0, 0.0, 0.0, '2024-01-01 12:00:00.000000', 1, 1, '2024-01-01 12:00:00.000000',
        'p.s,ps'),
       (0.0, 0, 0.0, 0.0, 0.0, '2024-01-01 12:00:00.000000', 2, 2, '2024-01-01 12:00:00.000000',
        'p.s,ps');

SET
    FOREIGN_KEY_CHECKS = 1;
