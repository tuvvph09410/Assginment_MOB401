package com.example.assginment_mob403.Model;

public class LoaiChi {
    private int id_loaichi;
    private int id_user;
    private String name_loaichi;

    public LoaiChi(int id_user, String name_loaichi) {
        this.id_user = id_user;
        this.name_loaichi = name_loaichi;
    }


    public int getId_user() {
        return id_user;
    }

    public String getName_loaichi() {
        return name_loaichi;
    }

    public int getId_loaichi() {
        return id_loaichi;
    }

    public void setId_loaichi(int id_loaichi) {
        this.id_loaichi = id_loaichi;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setName_loaichi(String name_loaichi) {
        this.name_loaichi = name_loaichi;
    }
}
