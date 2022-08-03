package com.example.assginment_mob403.Model;

public class LoaiThu {
    private int id_loaithu;
    private int id_user;
    private String name_loaithu;

    public LoaiThu(int id_user, String name_loaithu) {
        this.id_user = id_user;
        this.name_loaithu = name_loaithu;
    }

    public int getId_loaithu() {
        return id_loaithu;
    }

    public int getId_user() {
        return id_user;
    }

    public String getName_loaithu() {
        return name_loaithu;
    }

    public void setId_loaithu(int id_loaithu) {
        this.id_loaithu = id_loaithu;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setName_loaithu(String name_loaithu) {
        this.name_loaithu = name_loaithu;
    }
}
