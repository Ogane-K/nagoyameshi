package com.example.nagoyameshi.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.nagoyameshi.Dto.UserDto;
import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.event.SignupEventPublisher;
import com.example.nagoyameshi.form.SignupForm;
import com.example.nagoyameshi.service.UserService;
import com.example.nagoyameshi.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    private final VerificationTokenService verificationTokenService;

    private final UserService userService;
    private final SignupEventPublisher signupEventPublisher;

    RegistrationController(UserService userService, SignupEventPublisher signupEventPublisher,
            VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.signupEventPublisher = signupEventPublisher;
        this.verificationTokenService = verificationTokenService;
    }

    // 会員登録の入力フォーム画面
    @GetMapping("/signup")
    public String signup(@ModelAttribute("signupForm") SignupForm signupForm, Model model) {

        return "auth/signup";
    }

    // 会員登録の入力フォームの受取り
    @PostMapping("/signup")
    public String signup(@ModelAttribute("signupForm") @Validated SignupForm form,
            BindingResult bindingResult,
            HttpSession httpSession) {

        // バリデーションにエラーが有れば入力フォーム画面に戻す
        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }

        // メールアドレスがすでに登録さていた場合には、入力フォーム画面に戻す
        if (userService.isEmailRegistered(form.getEmail())) {
            bindingResult.rejectValue(
                    "email",
                    "duplicate.email",
                    "このメールアドレスはすでに登録されています");

            if (!userService.isSamePassword(form.getPassword(), form.getPasswordConfirmation())) {
                bindingResult.rejectValue(
                        "passwordConfirmation",
                        "",
                        "パスワードと確認用のパスワードが一致していません");
            }

            return "auth/signup";
        }

        // パスワードと確認入力したパスワードが一致していなかったら、入力フォーム画面に戻す
        if (!userService.isSamePassword(form.getPassword(), form.getPasswordConfirmation())) {
            bindingResult.rejectValue(
                    "passwordConfirmation",
                    "",
                    "パスワードと確認用のパスワードが一致していません");

            return "auth/signup";
        }

        // DTOにフォームクラスの中身を詰め替える
        UserDto dto = new UserDto();

        dto.setName(form.getName());
        dto.setFurigana(form.getFurigana());
        dto.setEmail(form.getEmail());
        dto.setPassword(form.getPassword());
        dto.setPostalCode(form.getPostalCode());
        dto.setAddress(form.getAddress());
        dto.setPhoneNumber(form.getPhoneNumber());

        // Occupation(職業)がnullでないなら、dtoにいれる
        if (StringUtils.hasText(form.getOccupation())) {
            dto.setOccupation(form.getOccupation());
        }

        if (StringUtils.hasText(form.getBirthday())) {
            try {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                LocalDate parsedBirthday = LocalDate.parse(form.getBirthday(), formatter);

                dto.setBirthday(parsedBirthday);
            } catch (DateTimeParseException e) {
                System.out.println("誕生日の型変換に失敗しました 変換前の値: " + form.getBirthday());
            }
        }

        httpSession.setAttribute("userDto", dto);

        return "redirect:/signup/confirm";
    }

    // 入力した会員情報の確認画面 / 会員登録
    @GetMapping("/signup/confirm")
    public String confirm(HttpSession httpSession,
            RedirectAttributes redirectAttributes,
            Model model) {

        UserDto userDto = (UserDto) httpSession.getAttribute("userDto");

        // セッション切れ、または直接アクセスされた場合は入力フォームに戻す
        if (userDto == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "セッションの有効期限が切れました。もう一度入力してください。");
            return "redirect:/signup";
        }

        model.addAttribute("userDto", userDto);
        return "auth/confirm";
    }

    // 確認画面でユーザー登録を確定
    @PostMapping("/signup/complete")
    public String complete(HttpSession httpSession,
            RedirectAttributes redirectAttributes,
            HttpServletRequest httpServletRequest) {

        UserDto userDto = (UserDto) httpSession.getAttribute("userDto");
        if (userDto == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "セッションの有効期限が切れました。もう一度入力してください。");
            return "redirect:/signup";
        }

        try {
            userService.createUser(userDto);
        } catch (Exception e) {
            System.out.println("ユーザー登録中にエラーが発生しました :" + e.getMessage());

            redirectAttributes.addFlashAttribute("errorMessage", "登録に失敗しました。もう一度お試しください。");
            return "redirect:/signup";

        }

        // 認証メール送信用のユーザーデータを取得
        User user = userService.findByUserByEmail(userDto.getEmail());

        String requestUrl = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":"
                + httpServletRequest.getServerPort();

        // 認証メール送信のイベントを発行
        try {
            signupEventPublisher.publishSignupEvent(user, requestUrl);
        } catch (Exception e) {
            System.err.println("メール送信イベントの発行に失敗しました。: " + e.getMessage());
        }

        // セッションからDTOをクリア
        httpSession.removeAttribute("userDto");

        return "redirect:/signup/mail-sent";
    }

    // メール認証用のメール送信画面
    @GetMapping("/signup/mail-sent")
    public String mailSent() {

        return "auth/mail-sent";
    }

    // 認証メール内でクリックしたURLの転送先
    @GetMapping("/verify")
    public String verify(@RequestParam(name = "token") String token) {

        if (!verificationTokenService.isVerifyToken(token)) {
            
            return "redirect:/signup/verifyError";
        }

        return "redirect:/signup/complete";
    }


    // 認証失敗画面
    @GetMapping("/signup/verifyError")
    public String verifyError(Model model) {

        model.addAttribute("errorMessage", "この認証リンクは無効か、期限切れです。");
        return "auth/verify";
    }
    


    // 完了画面の表示
    @GetMapping("/signup/complete")
    public String complete() {
        return "auth/complete";
    }

}
