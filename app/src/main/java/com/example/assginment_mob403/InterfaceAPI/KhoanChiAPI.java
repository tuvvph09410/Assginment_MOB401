package com.example.assginment_mob403.InterfaceAPI;

import com.example.assginment_mob403.ServerResponse.KhoanChi_Response.ServerResponseDeleteKhoanChi;
import com.example.assginment_mob403.ServerResponse.KhoanChi_Response.ServerResponseInsertKhoanChi;
import com.example.assginment_mob403.ServerResponse.KhoanChi_Response.ServerResponseSelectedDataById;
import com.example.assginment_mob403.ServerResponse.KhoanChi_Response.ServerResponseUpdateKhoanChi;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface KhoanChiAPI {
    @FormUrlEncoded
    @POST("insert_khoanchi.php")
    Call<ServerResponseInsertKhoanChi> insertKhoanChi(
            @Field("id_user") int id_user,
            @Field("id_loaichi") int id_loaichi,
            @Field("name_khoanchi") String name_khoanchi,
            @Field("money_khoanchi") int money_khoanchi,
            @Field("date_add_khoanchi") String date_add_khoanchi,
            @Field("note_khoanchi") String note_khoanchi
    );

    @FormUrlEncoded
    @POST("get_select_data_khoanchi_by_id_user.php")
    Call<ServerResponseSelectedDataById> selectKhoanchi(
            @Field("id_user") int id_user
    );

    @FormUrlEncoded
    @POST("delete_khoanchi_by_id.php")
    Call<ServerResponseDeleteKhoanChi> deleteKhoanchi(
            @Field("id_khoanchi") int id_khoanchi
    );

    @FormUrlEncoded
    @POST("update_khoanchi_by_id.php")
    Call<ServerResponseUpdateKhoanChi> updateKhoanchi(
            @Field("id_khoanchi") int id_khoanchi,
            @Field("name_khoanchi") String name_khoanchi,
            @Field("money_khoanchi") int money_khoanchi,
            @Field("note_khoanchi") String note_khoanchi,
            @Field("date_add_khoanchi") String date_add_khoanchi
    );
}
