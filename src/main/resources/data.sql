-- ロール　テーブル : roles

INSERT IGNORE INTO roles (id, name)
VALUES (1, 'ROLE_ADMIN');
INSERT IGNORE INTO roles (id, name)
VALUES (2, 'ROLE_FREE_MEMBER');
INSERT IGNORE INTO roles (id, name)
VALUES (3, 'ROLE_PREMIUM_MEMBER');
INSERT IGNORE INTO roles (id, name)
VALUES (4, 'ROLE_SUPER_PREMIUM_MEMBER');


-- ユーザー テーブル : users

INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (1, '山田 太郎', 'やまだ たろう', '$2b$12$OjFS.9Bhf3fER2DHBWBGaO1DkOb2.FnITRpxvQrdJmWwUjCpDrNqC', '100-0001', '東京都テスト町1-番地', '090-0000-0001', 'user01@example.com', true, '2014-12-23', '主婦', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (2, '佐藤 美咲', 'さとう みさき', '$2b$12$k9y01Xzzgw.q1cb4R8dDKOjOMyC/eJB.l.HuuHttBp4lcHhl8cSzm', '100-0002', '東京都テスト町2-番地', '090-0000-0002', 'user02@example.com', true, '2014-12-23', '主婦', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (3, '鈴木 翔', 'すずき しょう', '$2b$12$OwRukQDcX/kUI.czfvqM0uIMR8uSOj0.iCrRPKH7wSYP7WoEADFF2', '100-0003', '東京都テスト町3-番地', '090-0000-0003', 'user03@example.com', true, '2005-12-25', '会社員', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (4, '高橋 未来', 'たかはし みく', '$2b$12$nD0TIIScOZuGAdX2/.oNVeSqtQQ1bEWgmk1yOOrXtaEmGqq5u7YnC', '100-0004', '東京都テスト町4-番地', '090-0000-0004', 'user04@example.com', true, '2015-12-23', 'フリーランス', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (5, '田中 海斗', 'たなか かいと', '$2b$12$vWVzkCc339zUz9Go9DKvve5GXq/VMYrZy9Mfbu4jtYb.9Of53Ef1e', '100-0005', '東京都テスト町5-番地', '090-0000-0005', 'user05@example.com', true, '2016-12-22', '主婦', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (6, '中村 愛', 'なかむら あい', '$2b$12$tBFRxxuIdxajAW/HVa9XruODJs.LGnAFXeqGjACbQnex7MFI06D6C', '100-0006', '東京都テスト町6-番地', '090-0000-0006', 'user06@example.com', true, '2013-12-23', '無職', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (7, '小林 陸', 'こばやし りく', '$2b$12$F3wY.RZECG/WlSvOxyjyteK5HW9e7OIAEsCAfKcqYAH.7a8CHaEO2', '100-0007', '東京都テスト町7-番地', '090-0000-0007', 'user07@example.com', true, '2015-12-23', '主婦', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (8, '加藤 美優', 'かとう みゆ', '$2b$12$7doiaSmTbbnI8zdOHLrpdeqYYV/CxjVFWBls.JQlGDuYjKp9UoK.C', '100-0008', '東京都テスト町8-番地', '090-0000-0008', 'user08@example.com', true, '2008-12-24', '無職', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (9, '吉田 拓海', 'よしだ たくみ', '$2b$12$OjJoQyJ0E6ZL3QK3u1.9OOH5p4opjMqR.86mKAixchSJaNv4xViqC', '100-0009', '東京都テスト町9-番地', '090-0000-0009', 'user09@example.com', true, '2010-12-24', '会社員', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (10, '斎藤 結衣', 'さいとう ゆい', '$2b$12$0.HsTtRx0FD/A2e19P82.uAMTJho3ghuHkjewqZ9GkdP.rDCkQVSC', '100-0010', '東京都テスト町10-番地', '090-0000-0010', 'user10@example.com', true, '1998-12-27', '主婦', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (11, '渡辺 大和', 'わたなべ やまと', '$2b$12$CZOueKE8fKdhId5DkXCo4uiWNbeYms/L0EqF5qEMrLw8IGmBc4jEi', '100-0011', '東京都テスト町11-番地', '090-0000-0011', 'user11@example.com', true, '2004-12-25', '学生', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (12, '山口 葵', 'やまぐち あおい', '$2b$12$zrbGQAYzCTMnG8qfcuf75OCnhYkapHQscVCEvNI43mG/yOaTKgku.', '100-0012', '東京都テスト町12-番地', '090-0000-0012', 'user12@example.com', true, '2005-12-25', '会社員', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (13, '井上 悠人', 'いのうえ ゆうと', '$2b$12$4/ICYdxasH.xh.4H/j.x7O3W9Q0Sva5RRYTiQ/W5ZQeaGpaMdMr52', '100-0013', '東京都テスト町13-番地', '090-0000-0013', 'user13@example.com', true, '2017-12-22', '主婦', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (14, '松本 さくら', 'まつもと さくら', '$2b$12$ltZVVY0sqo0y7RzUHYq6RuScRn4zBjqITXPsIk1718hQITEj1wjXe', '100-0014', '東京都テスト町14-番地', '090-0000-0014', 'user14@example.com', true, '2015-12-23', '主婦', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (15, '清水 陽翔', 'しみず はると', '$2b$12$GLpTzg9S2z9ZwZt0gltOreHQnlLxXQGd7BlR6r3DglkrR/rdm77DG', '100-0015', '東京都テスト町15-番地', '090-0000-0015', 'user15@example.com', true, '2014-12-23', '会社員', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (16, '石川 心', 'いしかわ こころ', '$2b$12$1fhDlMZXZbLYuHeBflF11.Ev2twuoOe2v7SaSqL49XV7vww13dU/u', '100-0016', '東京都テスト町16-番地', '090-0000-0016', 'user16@example.com', true, '2005-12-25', '主婦', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (17, '森 永遠', 'もり とわ', '$2b$12$F7EY2j5SBoZwHx99LMwWqelRNuS0h3W8UKyNXJFKxn/aKYZDR/kSe', '100-0017', '東京都テスト町17-番地', '090-0000-0017', 'user17@example.com', true, '1997-12-27', '会社員', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (18, '橋本 栞', 'はしもと しおり', '$2b$12$WU.CHqMquUYt13NE.Bu62.NSMrJJTKkZ.iRgUbPausYq.hD4K5Sd.', '100-0018', '東京都テスト町18-番地', '090-0000-0018', 'user18@example.com', true, '2014-12-23', '無職', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (19, '池田 瑛太', 'いけだ えいた', '$2b$12$VEAqyZ19HsOgTYq7PJOBQuxJIWdQgM5eVNOHibo8z8KsQ4f3mkdQ.', '100-0019', '東京都テスト町19-番地', '090-0000-0019', 'user19@example.com', true, '2002-12-26', '会社員', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);
INSERT IGNORE INTO users (id, name, furigana, password, postal_code, address, phone_number, email, email_verified, birthday, occupation, created_at, updated_at, deleted_at)
VALUES (20, '阿部 花', 'あべ はな', '$2b$12$/nuEAr4sFS7vNwSSkfEVdeEmiMLjZmk8tU07nan832BJemAHThD7a', '100-0020', '東京都テスト町20-番地', '090-0000-0020', 'user20@example.com', true, '2017-12-22', 'フリーランス', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL);


-- ユーザー×ロール　テーブル : user_roles

INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (2, 1);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (3, 2);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (4, 2);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (5, 2);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (6, 2);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (7, 2);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (8, 2);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (9, 3);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (10, 3);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (11, 3);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (12, 3);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (13, 3);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (14, 3);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (15, 4);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (16, 4);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (17, 4);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (18, 4);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (19, 4);
INSERT IGNORE INTO user_roles (user_id, role_id) VALUES (20, 4);

-- メール認証トークン　テーブル : verification_tokens

INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (1, 1, 'dummy-token-1', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (2, 2, 'dummy-token-2', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (3, 3, 'dummy-token-3', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (4, 4, 'dummy-token-4', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (5, 5, 'dummy-token-5', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (6, 6, 'dummy-token-6', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (7, 7, 'dummy-token-7', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (8, 8, 'dummy-token-8', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (9, 9, 'dummy-token-9', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (10, 10, 'dummy-token-10', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (11, 11, 'dummy-token-11', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (12, 12, 'dummy-token-12', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (13, 13, 'dummy-token-13', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (14, 14, 'dummy-token-14', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (15, 15, 'dummy-token-15', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (16, 16, 'dummy-token-16', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (17, 17, 'dummy-token-17', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (18, 18, 'dummy-token-18', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (19, 19, 'dummy-token-19', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));
INSERT IGNORE INTO verification_tokens (id, user_id, token, created_at, expires_at)
VALUES (20, 20, 'dummy-token-20', CURRENT_TIMESTAMP, DATE_ADD(CURRENT_TIMESTAMP, INTERVAL 1 DAY));

-- カテゴリー　マスター　テーブル : categories

INSERT IGNORE INTO categories (id, name)
VALUES
(1, '居酒屋'),
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

-- 店舗　テーブル　: restaurants

INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    1, '和食処 あさひ', '店舗用画像4.jpg', '旬の食材を活かした優しい味わいの和食を提供します。', 727, 2571, '460-0001', '愛知県名古屋市中区仮町1-1-1',
    35.172062, 136.87656, '18:00:00', '22:30:00', 38, 0, 1.0,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    2, 'ラーメン一番星', '店舗用画像4.jpg', '濃厚なスープと自家製麺が自慢のラーメン専門店です。', 953, 2538, '460-0002', '愛知県名古屋市中区仮町2-2-2',
    35.168714, 136.875273, '18:00:00', '22:30:00', 33, 0, 1.4,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    3, 'Pizzeria Luna', '店舗用画像6.jpg', '石窯で焼くナポリピザとパスタが人気の本格イタリアン。', 1178, 2815, '460-0003', '愛知県名古屋市中区仮町3-3-3',
    35.169216, 136.882564, '17:00:00', '23:00:00', 48, 0, 4.1,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    4, '焼肉 龍苑', '店舗用画像4.jpg', '上質なお肉を炭火で楽しめる焼肉専門店です。', 691, 3193, '460-0004', '愛知県名古屋市中区仮町4-4-4',
    35.174621, 136.881662, '18:00:00', '22:30:00', 31, 0, 3.4,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    5, 'カフェ・ミモザ', '店舗用画像1.jpg', '昔ながらの落ち着いた雰囲気の喫茶店。', 919, 2308, '460-0005', '愛知県名古屋市中区仮町5-5-5',
    35.172564, 136.879056, '18:00:00', '22:30:00', 12, 0, 0.2,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    6, 'Sushi 雅', '店舗用画像1.jpg', '新鮮なネタを職人が握る本格寿司。', 1073, 2298, '460-0006', '愛知県名古屋市中区仮町6-6-6',
    35.169441, 136.879088, '18:00:00', '22:30:00', 10, 0, 4.5,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    7, 'ステーキ ハウス雷', '店舗用画像3.jpg', 'ジューシーなステーキをリーズナブルに楽しめます。', 687, 2141, '460-0007', '愛知県名古屋市中区仮町7-7-7',
    35.174015, 136.881344, '11:30:00', '22:00:00', 32, 0, 0.9,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    8, 'フレンチレーヴ', '店舗用画像1.jpg', '特別な日にぴったりの本格フレンチ。', 1233, 2849, '460-0008', '愛知県名古屋市中区仮町8-8-8',
    35.172895, 136.880892, '10:00:00', '20:00:00', 32, 0, 4.4,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    9, 'タコス デルソル', '店舗用画像2.jpg', 'タパスとワインが楽しめるスペインバル。', 1418, 3082, '460-0009', '愛知県名古屋市中区仮町9-9-9',
    35.16882, 136.879936, '10:00:00', '20:00:00', 22, 0, 1.6,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    10, 'Bar 夜空', '店舗用画像2.jpg', 'おしゃれな空間でゆったりとした時間を過ごせます。', 1120, 2121, '460-0010', '愛知県名古屋市中区仮町10-10-10',
    35.168847, 136.875724, '11:30:00', '22:00:00', 20, 0, 3.6,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    11, '中華大吉', '店舗用画像6.jpg', '本場仕込みの味が楽しめる中華料理店。', 698, 2149, '460-0011', '愛知県名古屋市中区仮町11-11-11',
    35.167723, 136.8805, '11:30:00', '22:00:00', 16, 0, 4.8,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    12, 'パン工房 麦麦', '店舗用画像5.jpg', '焼きたてのパンと香り高いコーヒーをどうぞ。', 898, 2707, '460-0012', '愛知県名古屋市中区仮町12-12-12',
    35.166222, 136.882491, '10:00:00', '20:00:00', 50, 0, 2.0,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    13, 'お好み焼き 花', '店舗用画像4.jpg', 'ふわっと焼き上げたお好み焼きが人気。', 783, 3600, '460-0013', '愛知県名古屋市中区仮町13-13-13',
    35.174665, 136.879931, '18:00:00', '22:30:00', 25, 0, 2.2,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    14, 'うどん亭 まつば', '店舗用画像3.jpg', 'コシのある手打ちうどんを提供しています。', 827, 3822, '460-0014', '愛知県名古屋市中区仮町14-14-14',
    35.166788, 136.87909, '11:00:00', '21:00:00', 27, 0, 3.3,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    15, 'カレーの王国', '店舗用画像3.jpg', 'スパイス香る本格インドカレー。', 1250, 3176, '460-0015', '愛知県名古屋市中区仮町15-15-15',
    35.1706, 136.883281, '18:00:00', '22:30:00', 25, 0, 2.5,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    16, 'たこ焼き天国', '店舗用画像6.jpg', '外カリ中トロの絶品たこ焼きをご提供。', 678, 3356, '460-0016', '愛知県名古屋市中区仮町16-16-16',
    35.171508, 136.879191, '10:00:00', '20:00:00', 23, 0, 4.3,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    17, '韓国食堂 ソウル', '店舗用画像1.jpg', 'ピリ辛がクセになる韓国の家庭料理。', 1235, 2535, '460-0017', '愛知県名古屋市中区仮町17-17-17',
    35.165203, 136.878823, '17:00:00', '23:00:00', 31, 0, 4.5,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    18, '天ぷら なぎさ', '店舗用画像1.jpg', '揚げたてサクサクの天ぷらをお楽しみください。', 1273, 4117, '460-0018', '愛知県名古屋市中区仮町18-18-18',
    35.165032, 136.882478, '11:30:00', '22:00:00', 39, 0, 4.0,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    19, 'しゃぶ膳', '店舗用画像1.jpg', '厳選素材を使ったしゃぶしゃぶ専門店。', 729, 2686, '460-0019', '愛知県名古屋市中区仮町19-19-19',
    35.171083, 136.881017, '18:00:00', '22:30:00', 18, 0, 3.6,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);
INSERT IGNORE INTO restaurants (
    id, name, image, description, lowest_price, highest_price, postal_code, address, latitude, longitude,
    opening_time, closing_time, seating_capacity, favorite_count, rating, created_at, updated_at, deleted_at
) VALUES (
    20, '丼丸屋', '店舗用画像6.jpg', 'がっつり食べたいあなたに丼物を！', 594, 1847, '460-0020', '愛知県名古屋市中区仮町20-20-20',
    35.173368, 136.877178, '11:30:00', '22:00:00', 27, 0, 2.9,
    CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL
);


-- 定休日　テーブル : holidays

INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (1, 'TUESDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (1, 'SUNDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (2, 'MONDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (2, 'THURSDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (2, 'WEDNESDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (3, 'SUNDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (4, 'THURSDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (4, 'HOLIDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (5, 'THURSDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (5, 'TUESDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (5, 'WEDNESDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (6, 'MONDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (6, 'SATURDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (7, 'THURSDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (7, 'HOLIDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (7, 'FRIDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (8, 'WEDNESDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (9, 'WEDNESDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (10, 'TUESDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (11, 'WEDNESDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (11, 'SATURDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (12, 'THURSDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (12, 'SATURDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (12, 'HOLIDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (13, 'SUNDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (13, 'FRIDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (14, 'HOLIDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (14, 'WEDNESDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (15, 'SATURDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (15, 'WEDNESDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (16, 'THURSDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (16, 'SUNDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (16, 'FRIDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (17, 'SUNDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (18, 'THURSDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (18, 'SATURDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (19, 'WEDNESDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (19, 'SUNDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (20, 'THURSDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (20, 'HOLIDAY');
INSERT IGNORE INTO holidays (restaurant_id, day_type) VALUES (20, 'SUNDAY');

--  店舗×カテゴリー テーブル : restaurants_categories

INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (1, 5);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (2, 32);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (3, 21);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (4, 6);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (5, 24);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (6, 10);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (6, 34);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (7, 24);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (7, 9);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (8, 1);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (8, 7);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (9, 13);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (9, 21);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (10, 4);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (10, 12);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (11, 10);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (11, 24);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (11, 11);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (12, 11);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (12, 22);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (12, 33);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (13, 29);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (13, 7);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (13, 33);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (14, 10);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (14, 31);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (14, 1);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (15, 12);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (15, 8);
INSERT IGNORE INTO restaurants_categories (restaurants_id, category_id) VALUES (15, 1);

-- レビュー　テーブル: reviews

INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (1, 1, 19, 5, 'とても満足したです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (2, 2, 3, 5, 'とても混んでいたです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (3, 2, 14, 3, 'とても良かったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (4, 2, 12, 5, 'とても混んでいたです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (5, 4, 9, 5, 'とても美味しかったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (6, 4, 13, 4, 'とても普通だったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (7, 4, 15, 2, 'とても混んでいたです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (8, 5, 9, 1, 'とても普通だったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (9, 6, 3, 2, 'とても満足したです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (10, 6, 2, 1, 'とても満足したです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (11, 6, 16, 5, 'とても混んでいたです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (12, 7, 3, 1, 'とても満足したです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (13, 7, 6, 5, 'とても混んでいたです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (14, 8, 11, 2, 'とても良かったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (15, 8, 3, 1, 'とても混んでいたです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (16, 8, 7, 5, 'とても普通だったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (17, 10, 10, 2, 'とても良かったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (18, 10, 8, 5, 'とても良かったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (19, 11, 5, 2, 'とても普通だったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (20, 11, 17, 4, 'とても混んでいたです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (21, 12, 10, 5, 'とても良かったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (22, 12, 16, 3, 'とても美味しかったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (23, 12, 12, 4, 'とても普通だったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (24, 13, 13, 5, 'とても満足したです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (25, 13, 1, 4, 'とても美味しかったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (26, 14, 19, 2, 'とても美味しかったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (27, 14, 20, 4, 'とても良かったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (28, 14, 13, 4, 'とても良かったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (29, 15, 14, 2, 'とても混んでいたです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (30, 15, 15, 3, 'とても満足したです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (31, 15, 6, 5, 'とても満足したです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (32, 16, 1, 1, 'とても良かったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (33, 16, 3, 1, 'とても普通だったです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (34, 17, 16, 5, 'とても満足したです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (35, 18, 19, 3, 'とても混んでいたです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (36, 18, 17, 5, 'とても混んでいたです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO reviews (id, user_id, restaurant_id, rating, comment, created_at, updated_at)
VALUES (37, 19, 4, 2, 'とても満足したです。', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- お気に入り　テーブル : favorites

INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (1, 20, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (2, 9, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (3, 11, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (4, 4, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (5, 5, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (6, 3, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (7, 8, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (8, 20, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (9, 17, 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (10, 1, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (11, 20, 4, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (12, 1, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (13, 9, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (14, 2, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (15, 12, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (16, 5, 5, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (17, 15, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (18, 9, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (19, 19, 6, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (20, 20, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (21, 3, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (22, 8, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (23, 17, 7, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (24, 14, 9, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (25, 11, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (26, 18, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (27, 2, 10, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (28, 20, 11, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (29, 13, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (30, 15, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (31, 7, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (32, 12, 12, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (33, 6, 16, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (34, 16, 16, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (35, 4, 16, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (36, 2, 16, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (37, 17, 17, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (38, 7, 17, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (39, 11, 17, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (40, 4, 18, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (41, 20, 18, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (42, 9, 18, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (43, 14, 18, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (44, 15, 18, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (45, 8, 19, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (46, 7, 19, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (47, 16, 19, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT IGNORE INTO favorites (id, restaurant_id, user_id, created_at, updated_at)
VALUES (48, 4, 20, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

-- 利用規約 テーブル : terms

INSERT IGNORE INTO terms (id, content)
VALUES (
        1,
        '<p>この利用規約（以下、「本規約」といいます。）は、NAGOYAMESHI株式会社（以下、「当社」といいます。）がこのウェブサイト上で提供するサービス（以下、「本サービス」といいます。）の利用条件を定めるものです。登録ユーザーの皆さま（以下、「ユーザー」といいます。）には、本規約に従って、本サービスをご利用いただきます。</p>

<h2>第1条（適用）</h2>
<ol><li>本規約は、ユーザーと当社との間の本サービスの利用に関わる一切の関係に適用されるものとします。</li>
<li>当社は本サービスに関し、本規約のほか、ご利用にあたってのルール等、各種の定め（以下、「個別規定」といいます。）をすることがあります。これら個別規定はその名称のいかんに関わらず、本規約の一部を構成するものとします。</li>
<li>本規約の規定が前条の個別規定の規定と矛盾する場合には、個別規定において特段の定めなき限り、個別規定の規定が優先されるものとします。</li></ol>

<h2>第2条（利用登録）</h2>
<ol><li>本サービスにおいては、登録希望者が本規約に同意の上、当社の定める方法によって利用登録を申請し、当社がこれを承認することによって、利用登録が完了するものとします。</li>
<li>当社は、利用登録の申請者に以下の事由があると判断した場合、利用登録の申請を承認しないことがあり、その理由については一切の開示義務を負わないものとします。</li>
<ol><li>利用登録の申請に際して虚偽の事項を届け出た場合</li>
<li>本規約に違反したことがある者からの申請である場合</li>
<li>その他、当社が利用登録を相当でないと判断した場合</li></ol></ol>

<h2>第3条（ユーザーIDおよびパスワードの管理）</h2>
<ol><li>ユーザーは、自己の責任において、本サービスのユーザーIDおよびパスワードを適切に管理するものとします。</li>
<li>ユーザーは、いかなる場合にも、ユーザーIDおよびパスワードを第三者に譲渡または貸与し、もしくは第三者と共用することはできません。当社は、ユーザーIDとパスワードの組み合わせが登録情報と一致してログインされた場合には、そのユーザーIDを登録しているユーザー自身による利用とみなします。</li>
<li>ユーザーID及びパスワードが第三者によって使用されたことによって生じた損害は、当社に故意又は重大な過失がある場合を除き、当社は一切の責任を負わないものとします。</li></ol>

<h2>第4条（利用料金および支払方法）</h2>
<ol><li>ユーザーは、本サービスの有料部分の対価として、当社が別途定め、本ウェブサイトに表示する利用料金を、当社が指定する方法により支払うものとします。</li>
<li>ユーザーが利用料金の支払を遅滞した場合には、ユーザーは年14．6％の割合による遅延損害金を支払うものとします。</li></ol>

<h2>第5条（禁止事項）</h2>
<p>ユーザーは、本サービスの利用にあたり、以下の行為をしてはなりません。</p>

<ol><li>法令または公序良俗に違反する行為</li>
<li>犯罪行為に関連する行為</li>
<li>本サービスの内容等、本サービスに含まれる著作権、商標権ほか知的財産権を侵害する行為</li>
<li>当社、ほかのユーザー、またはその他第三者のサーバーまたはネットワークの機能を破壊したり、妨害したりする行為</li>
<li>本サービスによって得られた情報を商業的に利用する行為</li>
<li>当社のサービスの運営を妨害するおそれのある行為</li>
<li>不正アクセスをし、またはこれを試みる行為</li>
<li>他のユーザーに関する個人情報等を収集または蓄積する行為</li>
<li>不正な目的を持って本サービスを利用する行為</li>
<li>本サービスの他のユーザーまたはその他の第三者に不利益、損害、不快感を与える行為</li>
<li>他のユーザーに成りすます行為</li>
<li>当社が許諾しない本サービス上での宣伝、広告、勧誘、または営業行為</li>
<li>面識のない異性との出会いを目的とした行為</li>
<li>当社のサービスに関連して、反社会的勢力に対して直接または間接に利益を供与する行為</li>
<li>その他、当社が不適切と判断する行為</li></ol>

<h2>第6条（本サービスの提供の停止等）</h2>
<ol><li>当社は、以下のいずれかの事由があると判断した場合、ユーザーに事前に通知することなく本サービスの全部または一部の提供を停止または中断することができるものとします。</li>
<ol><li>本サービスにかかるコンピュータシステムの保守点検または更新を行う場合</li>
<li>地震、落雷、火災、停電または天災などの不可抗力により、本サービスの提供が困難となった場合</li>
<li>コンピュータまたは通信回線等が事故により停止した場合</li>
<li>その他、当社が本サービスの提供が困難と判断した場合</li></ol>
<li>当社は、本サービスの提供の停止または中断により、ユーザーまたは第三者が被ったいかなる不利益または損害についても、一切の責任を負わないものとします。</li></ol>

<h2>第7条（利用制限および登録抹消）</h2>
<ol><li>当社は、ユーザーが以下のいずれかに該当する場合には、事前の通知なく、ユーザーに対して、本サービスの全部もしくは一部の利用を制限し、またはユーザーとしての登録を抹消することができるものとします。</li>
<ol><li>本規約のいずれかの条項に違反した場合</li>
<li>登録事項に虚偽の事実があることが判明した場合</li>
<li>料金等の支払債務の不履行があった場合</li>
<li>当社からの連絡に対し、一定期間返答がない場合</li>
<li>本サービスについて、最終の利用から一定期間利用がない場合</li>
<li>その他、当社が本サービスの利用を適当でないと判断した場合</li></ol>
<li>当社は、本条に基づき当社が行った行為によりユーザーに生じた損害について、一切の責任を負いません。</li></ol>

<h2>第8条（退会）</h2>
<p>ユーザーは、当社の定める退会手続により、本サービスから退会できるものとします。</p>

<h2>第9条（保証の否認および免責事項）</h2>
<ol><li>当社は、本サービスに事実上または法律上の瑕疵（安全性、信頼性、正確性、完全性、有効性、特定の目的への適合性、セキュリティなどに関する欠陥、エラーやバグ、権利侵害などを含みます。）がないことを明示的にも黙示的にも保証しておりません。</li>
<li>当社は、本サービスに起因してユーザーに生じたあらゆる損害について、当社の故意又は重過失による場合を除き、一切の責任を負いません。ただし、本サービスに関する当社とユーザーとの間の契約（本規約を含みます。）が消費者契約法に定める消費者契約となる場合、この免責規定は適用されません。</li>
<li>前項ただし書に定める場合であっても、当社は、当社の過失（重過失を除きます。）による債務不履行または不法行為によりユーザーに生じた損害のうち特別な事情から生じた損害（当社またはユーザーが損害発生につき予見し、または予見し得た場合を含みます。）について一切の責任を負いません。また、当社の過失（重過失を除きます。）による債務不履行または不法行為によりユーザーに生じた損害の賠償は、ユーザーから当該損害が発生した月に受領した利用料の額を上限とします。</li>
<li>当社は、本サービスに関して、ユーザーと他のユーザーまたは第三者との間において生じた取引、連絡または紛争等について一切責任を負いません。</li></ol>

<h2>第10条（サービス内容の変更等）</h2>
<p>当社は、ユーザーへの事前の告知をもって、本サービスの内容を変更、追加または廃止することがあり、ユーザーはこれを承諾するものとします。</p>

<h2>第11条（利用規約の変更）</h2>
<ol><li>当社は以下の場合には、ユーザーの個別の同意を要せず、本規約を変更することができるものとします。</li>
<ol><li>本規約の変更がユーザーの一般の利益に適合するとき。</li>
<li>本規約の変更が本サービス利用契約の目的に反せず、かつ、変更の必要性、変更後の内容の相当性その他の変更に係る事情に照らして合理的なものであるとき。</li></ol>
<li>当社はユーザーに対し、前項による本規約の変更にあたり、事前に、本規約を変更する旨及び変更後の本規約の内容並びにその効力発生時期を通知します。</li></ol>

<h2>第12条（個人情報の取扱い）</h2>
<p>当社は、本サービスの利用によって取得する個人情報については、当社「プライバシーポリシー」に従い適切に取り扱うものとします。</p>

<h2>第13条（通知または連絡）</h2>
<p>ユーザーと当社との間の通知または連絡は、当社の定める方法によって行うものとします。当社は、ユーザーから、当社が別途定める方式に従った変更届け出がない限り、現在登録されている連絡先が有効なものとみなして当該連絡先へ通知または連絡を行い、これらは、発信時にユーザーへ到達したものとみなします。</p>

<h2>第14条（権利義務の譲渡の禁止）</h2>
<p>ユーザーは、当社の書面による事前の承諾なく、利用契約上の地位または本規約に基づく権利もしくは義務を第三者に譲渡し、または担保に供することはできません。</p>

<h2>第15条（準拠法・裁判管轄）</h2>
<ol><li>本規約の解釈にあたっては、日本法を準拠法とします。</li>
<li>本サービスに関して紛争が生じた場合には、当社の本店所在地を管轄する裁判所を専属的合意管轄とします。</li></ol>'
    );


-- 課金プラン テーブル : plans

INSERT IGNORE INTO plans (
        id,
        name,
        stripe_price_id,
        price_yen,
        `plan_interval`
        )
VALUES (
        1,
        'premium',
        'price_1RjXr3BBdYnXts63snSFL2nq',
        300,
        'month'
    ),(
        2,
        'super_premium',
        'price_1RjkMQBBdYnXts639P77tBGx',
        500,
        'month'
    );

-- 会社概要 テーブル : companies

INSERT IGNORE INTO companies (
        id,
        name,
        postal_code,
        address,
        latitude,
        longitude,
        representative,
        establishment_date,
        capital,
        business,
        number_of_employees
    )
VALUES (
        1,
        'NAGOYAMESHI株式会社',
        '1010022',
        '東京都千代田区神田練塀町300番地 住友不動産秋葉原駅前ビル5F',
        35.698683,
        139.774219,
        '侍 太郎',
        '2015年3月19日',
        '110,000千円',
        '飲食店等の情報提供サービス',
        '83名'
    );