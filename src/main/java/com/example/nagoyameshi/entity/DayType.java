package com.example.nagoyameshi.entity;

public enum DayType {

    MONDAY("月曜日"),
    TUESDAY("火曜日"),
    WEDNESDAY("水曜日"),
    THURSDAY("木曜日"),
    FRIDAY("金曜日"),
    SATURDAY("土曜日"),
    SUNDAY("日曜日"),
    HOLIDAY("祝日"),
    IRREGULAR("不定休");

    private final String label;

    DayType(String label){
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

}
