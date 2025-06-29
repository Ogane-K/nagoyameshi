package com.example.nagoyameshi.event;

import java.util.UUID;

import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.service.VerificationTokenService;

@Component
public class SignupEventListener {

    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender javaMailsender;

    public SignupEventListener(VerificationTokenService verificationTokenService, JavaMailSender javaMailSender) {
        this.verificationTokenService = verificationTokenService;
        this.javaMailsender = javaMailSender;
    }

    // メール認証イベントの実行内容
    @EventListener
    private void onSigunupEvent(SignupEvent signupEvent) {

        // トークン情報保存用の必要なデータを用意
        User user = signupEvent.getUser();
        String token = UUID.randomUUID().toString();

        // トークン情報を保存する
        verificationTokenService.createVerification(user, token);

        // 送信するメールの内容の作成
        String senderAddress = "springboot.samuraitravel@example.com";
        String recipientAddress = user.getEmail();
        String subject = "メール認証";
        String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token;
        String message = "以下のリンクをクリックして会員登録を完了してください。" + "\n" + confirmationUrl;

        // メールの作成
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom(senderAddress);
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        // メールの送信
        try {
            javaMailsender.send(mailMessage);
        } catch (Exception e) {
            System.err.println("メール送信エラー" + e.getMessage());
        }
    }

}
