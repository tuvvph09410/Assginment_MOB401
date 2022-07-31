package com.example.assginment_mob403.ServerResponse.LoaiChi_Response;

import com.example.assginment_mob403.Model.LoaiChi;

public class ServerResponseSelectLoaiChi {
    private LoaiChi[] loaichi;
    private String message;
    private int success;

    public LoaiChi[] getLoaichi() {
        return loaichi;
    }

    public String getMessage() {
        return message;
    }

    public int getSuccess() {
        return success;
    }
}
