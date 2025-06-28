package com.example.nagoyameshi.Dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class UserDto {

    private String name;

    private String furigana;

    private String postalCode;

    private String address;

    private String phoneNumber;

    private LocalDate birthday;

    private String occupation;

    private String email;

    private String password;

}
