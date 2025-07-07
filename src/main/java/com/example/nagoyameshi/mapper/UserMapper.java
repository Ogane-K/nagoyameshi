package com.example.nagoyameshi.mapper;

import com.example.nagoyameshi.entity.User;
import com.example.nagoyameshi.form.UserEditForm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class UserMapper {

    private static final DateTimeFormatter BIRTHDAY_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

    // フォームからエンティティへ詰め替え

    public static User mapToEntity(UserEditForm form, User user) {
        user.setName(form.getName());
        user.setFurigana(form.getFurigana());
        user.setPostalCode(form.getPostalCode());
        user.setAddress(form.getAddress());
        user.setPhoneNumber(form.getPhoneNumber());
        user.setOccupation(form.getOccupation());
        user.setEmail(form.getEmail());

        // 誕生日（String → LocalDate）
        String birthdayStr = form.getBirthday();
        if (birthdayStr != null && !birthdayStr.isBlank()) {
            try {
                user.setBirthday(LocalDate.parse(birthdayStr, BIRTHDAY_FORMATTER));
            } catch (DateTimeParseException e) {
                user.setBirthday(null);
            }
        } else {
            user.setBirthday(null);
        }

        return user;
    }

    // エンティティからフォームへの詰替え
    public static UserEditForm mapToForm(User user) {
        UserEditForm form = new UserEditForm();
        form.setName(user.getName());
        form.setFurigana(user.getFurigana());
        form.setPostalCode(user.getPostalCode());
        form.setAddress(user.getAddress());
        form.setPhoneNumber(user.getPhoneNumber());
        form.setOccupation(user.getOccupation());
        form.setEmail(user.getEmail());

        // 誕生日（LocalDate → String）
        if (user.getBirthday() != null) {
            form.setBirthday(user.getBirthday().format(BIRTHDAY_FORMATTER));
        } else {
            form.setBirthday("");
        }

        return form;
    }
}
