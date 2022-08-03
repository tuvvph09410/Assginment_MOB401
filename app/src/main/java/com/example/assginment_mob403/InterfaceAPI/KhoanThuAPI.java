package com.example.assginment_mob403.InterfaceAPI;

import com.example.assginment_mob403.ServerResponse.KhoanThu_Response.ServerResponseDeleteKhoanThu;
import com.example.assginment_mob403.ServerResponse.KhoanThu_Response.ServerResponseInsertKhoanThu;
import com.example.assginment_mob403.ServerResponse.KhoanThu_Response.ServerResponseSelectedDataById;
import com.example.assginment_mob403.ServerResponse.KhoanThu_Response.ServerResponseUpdateKhoanThu;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface KhoanThuAPI {
    @FormUrlEncoded
    @POST("insert_khoanthu.php")
    Call<ServerResponseInsertKhoanThu> insertKhoanThu(
            @Field("id_user") int id_user,
            @Field("id_loaichi") int id_loaichi,
            @Field("name_khoanthu") String name_khoanthu,
            @Field("money_khoanthu") int money_khoanthu,
            @Field("date_add_khoanthu") String date_add_khoanthu,
            @Field("note_khoanthu") String note_khoanthu
    );

    @FormUrlEncoded
    @POST("get_select_data_khoanthu_by_id_user.php")
    Call<ServerResponseSelectedDataById> selectKhoanThu(
            @Field("id_user") int id_user
    );

    @FormUrlEncoded
    @POST("delete_khoanthu_by_id.php")
    Call<ServerResponseDeleteKhoanThu> deleteKhoanThu(
            @Field("id_khoanthu") int id_khoanthu
    );

    @FormUrlEncoded
    @POST("update_khoanthu_by_id.php")
    Call<ServerResponseUpdateKhoanThu> updateKhoanThu(
            @Field("id_khoanthu") int id_khoanthu,
            @Field("name_khoanthu") String name_khoanthu,
            @Field("money_khoanthu") int money_khoanthu,
            @Field("note_khoanthu") String note_khoanthu,
            @Field("date_add_khoanthu") String date_add_khoanthu
    );
}


