package com.example.assginment_mob403.Model;

public class KhoanThu {
    private int id_khoanthu;
    private int id_user;
    private int id_loaithu;
    private String name_khoanthu;
    private int money_khoanthu;
    private String date_add_khoanthu;
    private String note_khoanthu;

    public KhoanThu(int id_user, int id_loaithu, String name_khoanthu, int money_khoanthu, String date_add_khoanthu, String note_khoanthu) {
        this.id_user = id_user;
        this.id_loaithu = id_loaithu;
        this.name_khoanthu = name_khoanthu;
        this.money_khoanthu = money_khoanthu;
        this.date_add_khoanthu = date_add_khoanthu;
        this.note_khoanthu = note_khoanthu;
    }

    public void setId_khoanthu(int id_khoanthu) {
        this.id_khoanthu = id_khoanthu;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_loaithu(int id_loaithu) {
        this.id_loaithu = id_loaithu;
    }

    public void setName_khoanthu(String name_khoanthu) {
        this.name_khoanthu = name_khoanthu;
    }

    public void setMoney_khoanthu(int money_khoanthu) {
        this.money_khoanthu = money_khoanthu;
    }

    public void setDate_add_khoanthu(String date_add_khoanthu) {
        this.date_add_khoanthu = date_add_khoanthu;
    }

    public void setNote_khoanthu(String note_khoanthu) {
        this.note_khoanthu = note_khoanthu;
    }

    public int getId_khoanthu() {
        return id_khoanthu;
    }

    public int getId_user() {
        return id_user;
    }

    public int getId_loaithu() {
        return id_loaithu;
    }

    public String getName_khoanthu() {
        return name_khoanthu;
    }

    public int getMoney_khoanthu() {
        return money_khoanthu;
    }

    public String getDate_add_khoanthu() {
        return date_add_khoanthu;
    }

    public String getNote_khoanthu() {
        return note_khoanthu;
    }
}
