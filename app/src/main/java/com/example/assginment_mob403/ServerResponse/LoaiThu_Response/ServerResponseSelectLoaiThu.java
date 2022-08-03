package com.example.assginment_mob403.ServerResponse.LoaiThu_Response;

import com.example.assginment_mob403.Model.LoaiThu;

public class ServerResponseSelectLoaiThu {
    private LoaiThu[] loaithu;
    private String message;
    private int success;

    public LoaiThu[] getLoaiThu() {
        return loaithu;
    }

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }
}
