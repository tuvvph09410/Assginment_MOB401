package com.example.assginment_mob403.InterfaceAPI;

import com.example.assginment_mob403.ServerResponse.LoaiThu_Response.ServerResponseDeleteLoaiThu;
import com.example.assginment_mob403.ServerResponse.LoaiThu_Response.ServerResponseInsertLoaiThu;
import com.example.assginment_mob403.ServerResponse.LoaiThu_Response.ServerResponseSelectLoaiThu;
import com.example.assginment_mob403.ServerResponse.LoaiThu_Response.ServerResponseUpdateLoaiThu;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoaiThuAPI {
    @FormUrlEncoded
    @POST("insert_loaithu.php")
    Call<ServerResponseInsertLoaiThu> insertLoaiThu(
            @Field("id_user") int id_user,
            @Field("name_loaithu") String name_loaithu
    );

    @FormUrlEncoded
    @POST("get_select_data_loaithu_by_id_user.php")
    Call<ServerResponseSelectLoaiThu> getSelectLoaiThu(
            @Field("id_user") int id_user
    );

    @FormUrlEncoded
    @POST("delete_loaithu_by_id.php")
    Call<ServerResponseDeleteLoaiThu> deleteLoaiThu(
            @Field("id_loaithu") int id_loaithu
    );

    @FormUrlEncoded
    @POST("update_loaithu_by_id.php")
    Call<ServerResponseUpdateLoaiThu> updateLoaiThu(
            @Field("id_loaithu") int id_loaithu,
            @Field("name_loaithu") String name_loaithu
    );
    @FormUrlEncoded
    @POST("get_select_data_loaithu_by_id_user.php")
    Call<ServerResponseSelectLoaiThu> getSelectedLoaiThuByIdUserAndIdLoaiThu(
            @Field("id_user") int id_user,
            @Field("id_loaithu") int id_loaithu
    );
}
