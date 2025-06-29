package com.example.nagoyameshi.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.entity.VerificationToken;
import com.example.nagoyameshi.repository.UserRepository;
import com.example.nagoyameshi.repository.VerificationTokenRepository;

@Service
public class VerificationTokenService {

    private final UserRepository userRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository,
            UserRepository userRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.userRepository = userRepository;
    }

    // トークン情報の作成・保存
    @Transactional
    public void createVerification(User user, String token) {

        // トークン情報の雛形を作成
        VerificationToken verificationToken = new VerificationToken();

        // 雛形に値を当てはめる
        verificationToken.setUser(user);
        verificationToken.setToken(token);
        verificationToken.setExpiresAt(LocalDateTime.now().plusDays(1));

        // DBに保存
        verificationTokenRepository.save(verificationToken);
    }

    // トークンが存在していたら、認証完了してtrueを返す ソレ以外はfalseを返す
    @Transactional
    public boolean isVerifyToken(String token) {
        Optional<VerificationToken> optionalToken = verificationTokenRepository.findByToken(token);

        // 一致するトークンが登録されていなかったらfalse
        if (optionalToken.isEmpty()) {
            return false;
        }

        VerificationToken verificationToken = optionalToken.get();

        // トークンの期限が切れていたらfalse
        if (verificationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return false;
        }

        // 保存するユーザーデータを取得
        User user = verificationToken.getUser();

        // メールアドレスを認証済みにする
        user.setEmailVerified(true);

        // ユーザーデータを保存する
        userRepository.save(user);

        return true;
    }

    // トークンの文字列を元にトークン情報を検索する
    public VerificationToken findVerificationTokenByToken(String token) {

        return verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("無効な認証トークンです: " + token));
    }

}
