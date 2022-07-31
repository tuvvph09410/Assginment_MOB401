package com.example.assginment_mob403.InterfaceAPI;

import com.example.assginment_mob403.ServerResponse.LoaiChi_Response.ServerResponseInsertLoaiChi;
import com.example.assginment_mob403.ServerResponse.LoaiChi_Response.ServerResponseSelectLoaiChi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoaiChiAPI {
    @FormUrlEncoded
    @POST("insert_loaichi.php")
    Call<ServerResponseInsertLoaiChi> insertLoaiChi(
            @Field("id_user") int id_user,
            @Field("name_loaichi") String name_loaichi
    );

    @FormUrlEncoded
    @POST("get_select_data_loaichi_by_id_user.php")
    Call<ServerResponseSelectLoaiChi> getSelectLoaiChi(
            @Field("id_user") int id_user
    );
}
