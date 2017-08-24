package com.example.jalaliandgregoriancalendar.customview;

public class CalItem {

    int id;
    String day;
    int background;
    int status;
    boolean click;

    public CalItem(int id, String day, int background) {
        this.id = id;
        this.day = day;
        this.background = background;
    }

    public CalItem(String day, int background, int status) {
        this.day = day;
        this.background = background;
        this.status = status;
    }

    public CalItem(String day, int background) {
        this.day = day;
        this.background = background;
    }

    public CalItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }
}
