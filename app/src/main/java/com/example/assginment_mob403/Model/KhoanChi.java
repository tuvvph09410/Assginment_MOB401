package com.example.assginment_mob403.Model;

public class KhoanChi {
    private int id_khoanchi;
    private int id_user;
    private int id_loaichi;
    private String name_khoanchi;
    private int money_khoanchi;
    private String date_add_khoanchi;
    private String note_khoanchi;

    public KhoanChi(int id_user, int id_loaichi, String name_khoanchi, int money_khoanchi, String date_add_khoanchi, String note_khoanchi) {
        this.id_user = id_user;
        this.id_loaichi = id_loaichi;
        this.name_khoanchi = name_khoanchi;
        this.money_khoanchi = money_khoanchi;
        this.date_add_khoanchi = date_add_khoanchi;
        this.note_khoanchi = note_khoanchi;
    }

    public int getId_khoanchi() {
        return id_khoanchi;
    }

    public void setId_khoanchi(int id_khoanchi) {
        this.id_khoanchi = id_khoanchi;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_loaichi() {
        return id_loaichi;
    }

    public void setId_loaichi(int id_loaichi) {
        this.id_loaichi = id_loaichi;
    }

    public String getName_khoanchi() {
        return name_khoanchi;
    }

    public void setName_khoanchi(String name_khoanchi) {
        this.name_khoanchi = name_khoanchi;
    }

    public int getMoney_khoanchi() {
        return money_khoanchi;
    }

    public void setMoney_khoanchi(int money_khoanchi) {
        this.money_khoanchi = money_khoanchi;
    }

    public String getDate_add_khoanchi() {
        return date_add_khoanchi;
    }

    public void setDate_add_khoanchi(String date_add_khoanchi) {
        this.date_add_khoanchi = date_add_khoanchi;
    }

    public String getNote_khoanchi() {
        return note_khoanchi;
    }

    public void setNote_khoanchi(String note_khoanchi) {
        this.note_khoanchi = note_khoanchi;
    }
}
