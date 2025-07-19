package com.example.nagoyameshi.entity;

public enum DayType {
    SUNDAY("日曜日", 0),
    MONDAY("月曜日", 1),
    TUESDAY("火曜日", 2),
    WEDNESDAY("水曜日", 3),
    THURSDAY("木曜日", 4),
    FRIDAY("金曜日", 5),
    SATURDAY("土曜日", 6),
    HOLIDAY("祝日", -1),
    IRREGULAR("不定休", -1);

    private final String label;
    private final int jsDayOfWeek;

    DayType(String label, int jsDayOfWeek) {
        this.label = label;
        this.jsDayOfWeek = jsDayOfWeek;
    }

    public String getLabel() {
        return label;
    }

    public int getJsDayOfWeek() {
        return jsDayOfWeek;
    }
}