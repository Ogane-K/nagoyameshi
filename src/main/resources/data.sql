INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_ADMIN');

INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_MEMBER');

INSERT IGNORE INTO roles (id, name) VALUES (3, 'ROLE_PREMIUM');

INSERT IGNORE INTO users (
    id, name, furigana, password, postal_code, address,
    phone_number, email, email_verified, birthday, occupation,
    created_at, updated_at, deleted_at
) VALUES (
    1, 'ユーザー1', 'ゆーざー1', '$2b$12$WAz2woYem9Cylxx6gWXZTudodvoIaK6597ouXkhfRqjlATpJ1cXxq', '123-4561',
    '名古屋市テスト町1丁目', '090-0000-0001', 'user1@example.com', true,
    '1991-01-01', '職業1', '2025-06-27 10:44:03', '2025-06-27 10:44:03', NULL
);

INSERT IGNORE INTO users (
    id, name, furigana, password, postal_code, address,
    phone_number, email, email_verified, birthday, occupation,
    created_at, updated_at, deleted_at
) VALUES (
    2, 'ユーザー2', 'ゆーざー2', '$2b$12$loWR7z3H1t50Jdga4Cf5DOBIgOJ75XbjrN/jNjWSZbRd1Aiz0t6KS', '123-4562',
    '名古屋市テスト町2丁目', '090-0000-0002', 'user2@example.com', true,
    '1992-01-01', '職業2', '2025-06-27 10:44:03', '2025-06-27 10:44:03', NULL
);

INSERT IGNORE INTO users (
    id, name, furigana, password, postal_code, address,
    phone_number, email, email_verified, birthday, occupation,
    created_at, updated_at, deleted_at
) VALUES (
    3, 'ユーザー3', 'ゆーざー3', '$2b$12$o5cJnkhCUuud1kfd14990OwTK6Pp6eMgIMvStb4qC7VsvMlt/5h.q', '123-4563',
    '名古屋市テスト町3丁目', '090-0000-0003', 'user3@example.com', true,
    '1993-01-01', '職業3', '2025-06-27 10:44:03', '2025-06-27 10:44:03', NULL
);

INSERT IGNORE INTO users (
    id, name, furigana, password, postal_code, address,
    phone_number, email, email_verified, birthday, occupation,
    created_at, updated_at, deleted_at
) VALUES (
    4, 'ユーザー4', 'ゆーざー4', '$2b$12$Jv4lNNs5n3rOOWm079hkwOgVR1xU8XuQ90AR24vC6tC1uCbakv0ay', '123-4564',
    '名古屋市テスト町4丁目', '090-0000-0004', 'user4@example.com', true,
    '1994-01-01', '職業4', '2025-06-27 10:44:03', '2025-06-27 10:44:03', NULL
);

INSERT IGNORE INTO users (
    id, name, furigana, password, postal_code, address,
    phone_number, email, email_verified, birthday, occupation,
    created_at, updated_at, deleted_at
) VALUES (
    5, 'ユーザー5', 'ゆーざー5', '$2b$12$uM/triOsPjHQ7pzLJ5LCIOUT8L8.vbN1HlJ6eogSY//4CudT44tMO', '123-4565',
    '名古屋市テスト町5丁目', '090-0000-0005', 'user5@example.com', true,
    '1995-01-01', '職業5', '2025-06-27 10:44:03', '2025-06-27 10:44:03', NULL
);

INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (1, 1);

INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (2, 2);

INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (3, 3);

INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (4, 2);

INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (5, 2);

INSERT IGNORE INTO verification_tokens (
    id, user_id, token, created_at, expires_at
) VALUES (
    1, 1, 'token-1', '2025-06-27 10:44:03', '2025-06-28 10:44:03'
);

INSERT IGNORE INTO verification_tokens (
    id, user_id, token, created_at, expires_at
) VALUES (
    2, 2, 'token-2', '2025-06-27 10:44:03', '2025-06-28 10:44:03'
);

INSERT IGNORE INTO verification_tokens (
    id, user_id, token, created_at, expires_at
) VALUES (
    3, 3, 'token-3', '2025-06-27 10:44:03', '2025-06-28 10:44:03'
);

INSERT IGNORE INTO verification_tokens (
    id, user_id, token, created_at, expires_at
) VALUES (
    4, 4, 'token-4', '2025-06-27 10:44:03', '2025-06-28 10:44:03'
);

INSERT IGNORE INTO verification_tokens (
    id, user_id, token, created_at, expires_at
) VALUES (
    5, 5, 'token-5', '2025-06-27 10:44:03', '2025-06-28 10:44:03'
);