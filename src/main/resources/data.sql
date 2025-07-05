INSERT IGNORE INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, name)
VALUES (2, 'ROLE_MEMBER');
INSERT IGNORE INTO roles (id, name)
VALUES (3, 'ROLE_PREMIUM');
INSERT IGNORE INTO users (
        id,
        name,
        furigana,
        password,
        postal_code,
        address,
        phone_number,
        email,
        email_verified,
        birthday,
        occupation,
        created_at,
        updated_at,
        deleted_at
    )
VALUES (
        1,
        'ユーザー1',
        'ゆーざー1',
        '$2b$12$WAz2woYem9Cylxx6gWXZTudodvoIaK6597ouXkhfRqjlATpJ1cXxq',
        '123-4561',
        '名古屋市テスト町1丁目',
        '090-0000-0001',
        'user1@example.com',
        true,
        '1991-01-01',
        '職業1',
        '2025-06-27 10:44:03',
        '2025-06-27 10:44:03',
        NULL
    );
INSERT IGNORE INTO users (
        id,
        name,
        furigana,
        password,
        postal_code,
        address,
        phone_number,
        email,
        email_verified,
        birthday,
        occupation,
        created_at,
        updated_at,
        deleted_at
    )
VALUES (
        2,
        'ユーザー2',
        'ゆーざー2',
        '$2b$12$loWR7z3H1t50Jdga4Cf5DOBIgOJ75XbjrN/jNjWSZbRd1Aiz0t6KS',
        '123-4562',
        '名古屋市テスト町2丁目',
        '090-0000-0002',
        'user2@example.com',
        true,
        '1992-01-01',
        '職業2',
        '2025-06-27 10:44:03',
        '2025-06-27 10:44:03',
        NULL
    );
INSERT IGNORE INTO users (
        id,
        name,
        furigana,
        password,
        postal_code,
        address,
        phone_number,
        email,
        email_verified,
        birthday,
        occupation,
        created_at,
        updated_at,
        deleted_at
    )
VALUES (
        3,
        'ユーザー3',
        'ゆーざー3',
        '$2b$12$o5cJnkhCUuud1kfd14990OwTK6Pp6eMgIMvStb4qC7VsvMlt/5h.q',
        '123-4563',
        '名古屋市テスト町3丁目',
        '090-0000-0003',
        'user3@example.com',
        true,
        '1993-01-01',
        '職業3',
        '2025-06-27 10:44:03',
        '2025-06-27 10:44:03',
        NULL
    );
INSERT IGNORE INTO users (
        id,
        name,
        furigana,
        password,
        postal_code,
        address,
        phone_number,
        email,
        email_verified,
        birthday,
        occupation,
        created_at,
        updated_at,
        deleted_at
    )
VALUES (
        4,
        'ユーザー4',
        'ゆーざー4',
        '$2b$12$Jv4lNNs5n3rOOWm079hkwOgVR1xU8XuQ90AR24vC6tC1uCbakv0ay',
        '123-4564',
        '名古屋市テスト町4丁目',
        '090-0000-0004',
        'user4@example.com',
        true,
        '1994-01-01',
        '職業4',
        '2025-06-27 10:44:03',
        '2025-06-27 10:44:03',
        NULL
    );
INSERT IGNORE INTO users (
        id,
        name,
        furigana,
        password,
        postal_code,
        address,
        phone_number,
        email,
        email_verified,
        birthday,
        occupation,
        created_at,
        updated_at,
        deleted_at
    )
VALUES (
        5,
        'ユーザー5',
        'ゆーざー5',
        '$2b$12$uM/triOsPjHQ7pzLJ5LCIOUT8L8.vbN1HlJ6eogSY//4CudT44tMO',
        '123-4565',
        '名古屋市テスト町5丁目',
        '090-0000-0005',
        'user5@example.com',
        true,
        '1995-01-01',
        '職業5',
        '2025-06-27 10:44:03',
        '2025-06-27 10:44:03',
        NULL
    );
INSERT IGNORE INTO user_roles (user_id, role_id)
VALUES (1, 1);
INSERT IGNORE INTO user_roles (user_id, role_id)
VALUES (2, 2);
INSERT IGNORE INTO user_roles (user_id, role_id)
VALUES (3, 3);
INSERT IGNORE INTO user_roles (user_id, role_id)
VALUES (4, 2);
INSERT IGNORE INTO user_roles (user_id, role_id)
VALUES (5, 2);
INSERT IGNORE INTO verification_tokens (
        id,
        user_id,
        token,
        created_at,
        expires_at
    )
VALUES (
        1,
        1,
        'token-1',
        '2025-06-27 10:44:03',
        '2025-06-28 10:44:03'
    );
INSERT IGNORE INTO verification_tokens (
        id,
        user_id,
        token,
        created_at,
        expires_at
    )
VALUES (
        2,
        2,
        'token-2',
        '2025-06-27 10:44:03',
        '2025-06-28 10:44:03'
    );
INSERT IGNORE INTO verification_tokens (
        id,
        user_id,
        token,
        created_at,
        expires_at
    )
VALUES (
        3,
        3,
        'token-3',
        '2025-06-27 10:44:03',
        '2025-06-28 10:44:03'
    );
INSERT IGNORE INTO verification_tokens (
        id,
        user_id,
        token,
        created_at,
        expires_at
    )
VALUES (
        4,
        4,
        'token-4',
        '2025-06-27 10:44:03',
        '2025-06-28 10:44:03'
    );
INSERT IGNORE INTO verification_tokens (
        id,
        user_id,
        token,
        created_at,
        expires_at
    )
VALUES (
        5,
        5,
        'token-5',
        '2025-06-27 10:44:03',
        '2025-06-28 10:44:03'
    );
INSERT IGNORE INTO categories (id, name)
VALUES (1, '居酒屋'),
    (2, '焼肉'),
    (3, '寿司'),
    (4, 'ラーメン'),
    (5, '定食'),
    (6, 'カレー'),
    (7, '喫茶店'),
    (8, '中華料理'),
    (9, 'イタリア料理'),
    (10, 'フランス料理'),
    (11, 'スペイン料理'),
    (12, '韓国料理'),
    (13, 'タイ料理'),
    (14, '海鮮料理'),
    (15, 'ステーキ'),
    (16, 'ハンバーグ'),
    (17, 'ハンバーガー'),
    (18, 'そば'),
    (19, 'うどん'),
    (20, 'お好み焼き'),
    (21, 'たこ焼き'),
    (22, '鍋料理'),
    (23, 'バー'),
    (24, 'パン'),
    (25, 'スイーツ'),
    (26, '和食'),
    (27, 'おでん'),
    (28, '焼き鳥'),
    (29, 'すき焼き'),
    (30, 'しゃぶしゃぶ'),
    (31, '天ぷら'),
    (32, '揚げ物'),
    (33, '丼物'),
    (34, '鉄板焼き');
INSERT IGNORE INTO restaurants (
        id,
        name,
        image,
        description,
        lowest_price,
        highest_price,
        postal_code,
        address,
        latitude,
        longitude,
        opening_time,
        closing_time,
        seating_capacity,
        favorite_count,
        rating,
        created_at,
        updated_at,
        deleted_at
    )
VALUES (
        1,
        '和食 鶴の庵',
        NULL,
        '旬の食材を使った本格和食',
        1000,
        3000,
        '460-0008',
        '名古屋市中区栄1-2-3',
        35.1690,
        136.9066,
        '11:00:00',
        '21:00:00',
        40,
        0,
        4.2,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        NULL
    ),
    (
        2,
        'Trattoria Sole',
        NULL,
        '本格石窯ピッツァとイタリアン',
        1200,
        3500,
        '460-0007',
        '名古屋市中区錦2-3-4',
        35.1700,
        136.9070,
        '11:30:00',
        '22:00:00',
        30,
        0,
        4.5,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        NULL
    );
-- 📅 定休日データ（Enum: MONDAY, FRIDAY, etc.）
-- 和食 鶴の庵 → 月曜・金曜
-- Trattoria Sole → 火曜・祝日
INSERT IGNORE INTO holidays (restaurant_id, day_type)
VALUES (1, 'MONDAY'),
    (1, 'FRIDAY'),
    (2, 'TUESDAY'),
    (2, 'HOLIDAY');