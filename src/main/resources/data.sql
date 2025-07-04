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
INSERT IGNORE INTO restaurants (
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
        regular_holiday
    )
VALUES (
        '味噌カツ一番 本店',
        '',
        '名古屋名物・味噌カツの専門店。濃厚なタレがクセになる逸品。',
        800,
        1800,
        '460-0008',
        '名古屋市中区栄1-1-1',
        35.1709,
        136.8815,
        '11:00:00',
        '22:00:00',
        50,
        120,
        4.6,
        '水曜日'
    ),
    (
        '手打ちそば 梅乃屋',
        '',
        'こだわりの手打ちそばと静かな和空間。落ち着いたランチにおすすめ。',
        1000,
        2000,
        '460-0007',
        '名古屋市中区新栄2-3-8',
        35.1687,
        136.9053,
        '11:30:00',
        '20:30:00',
        36,
        45,
        4.2,
        '火曜日'
    ),
    (
        '洋食キッチン アカツキ',
        '',
        '昭和の雰囲気漂う洋食屋。オムライスが大人気。',
        900,
        1600,
        '460-0011',
        '名古屋市中区大須3-15-9',
        35.1593,
        136.9039,
        '10:30:00',
        '21:00:00',
        40,
        78,
        4.4,
        '月曜日'
    ),
    (
        '中華ダイニング 翠園',
        '',
        '本格中華を気軽に味わえるダイニング。麻婆豆腐が絶品。',
        700,
        2500,
        '460-0012',
        '名古屋市中区千代田5-10-2',
        35.1551,
        136.9021,
        '11:00:00',
        '22:30:00',
        60,
        98,
        4.3,
        '木曜日'
    ),
    (
        'カフェ・ド・ルーヴ',
        '',
        '名古屋城近くのレトロカフェ。モーニングが充実。',
        500,
        1200,
        '460-0031',
        '名古屋市中区本丸1-1',
        35.1852,
        136.8996,
        '08:00:00',
        '18:00:00',
        28,
        150,
        4.7,
        'なし'
    ),
    (
        'グリル青山',
        '',
        '昔ながらの洋食と丁寧な接客が魅力。ハンバーグが人気。',
        850,
        1900,
        '460-0006',
        '名古屋市中区葵1-2-3',
        35.1741,
        136.9180,
        '11:00:00',
        '21:30:00',
        42,
        64,
        4.1,
        '日曜日'
    ),
    (
        'らぁめん信玄',
        '',
        '濃厚な豚骨醤油スープともちもち麺が自慢のラーメン店。',
        780,
        1200,
        '460-0003',
        '名古屋市中区錦3-4-15',
        35.1702,
        136.9062,
        '11:00:00',
        '23:00:00',
        20,
        102,
        4.5,
        '水曜日'
    ),
    (
        'イタリアンバル LUNA',
        '',
        '名古屋の夜景を楽しめるイタリアンバル。デートにも最適。',
        1500,
        4000,
        '460-0002',
        '名古屋市中区丸の内2-8-11',
        35.1814,
        136.8982,
        '17:00:00',
        '01:00:00',
        48,
        89,
        4.3,
        '月曜日'
    ),
    (
        'お好み焼き 楓',
        '',
        '関西風お好み焼きと鉄板焼きのお店。アットホームな雰囲気。',
        900,
        2200,
        '460-0004',
        '名古屋市中区東桜1-10-3',
        35.1747,
        136.9107,
        '12:00:00',
        '22:00:00',
        32,
        55,
        4.0,
        '木曜日'
    ),
    (
        'ベーカリー＆カフェ なつめ',
        '',
        '手作りパンと自家焙煎コーヒーが楽しめるベーカリーカフェ。',
        400,
        1000,
        '460-0015',
        '名古屋市中区大井町2-5',
        35.1665,
        136.8912,
        '07:30:00',
        '17:00:00',
        26,
        110,
        4.6,
        '日曜日'
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