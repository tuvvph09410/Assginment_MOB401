package com.example.assginment_mob403.InterfaceAPI;

import com.example.assginment_mob403.ServerResponse.User_Response.ServerResponseChangePassword;
import com.example.assginment_mob403.ServerResponse.User_Response.ServerResponseGetUserById;
import com.example.assginment_mob403.ServerResponse.User_Response.ServerResponseSelectAccount;
import com.example.assginment_mob403.ServerResponse.User_Response.ServerResponseSignin;
import com.example.assginment_mob403.ServerResponse.User_Response.ServerResponseUpdateUser;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserAPI {
    @FormUrlEncoded
    @POST("insert_user.php")
    Call<ServerResponseSignin> insertUser(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("phone") int phone,
            @Field("registration_date") String registration_date
    );

    @FormUrlEncoded
    @POST("check_login_and_select_data_by_id.php")
    Call<ServerResponseSelectAccount> getSelectUser_checkLogin(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("change_password_by_id_user.php")
    Call<ServerResponseChangePassword> changePasswordById(
            @Field("id_user") int id_user,
            @Field("new_password") String new_password
    );

    @FormUrlEncoded
    @POST("update_user_by_id.php")
    Call<ServerResponseUpdateUser> updateUser(
            @Field("id_user") int id_user,
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("phone") int phone
    );
    @FormUrlEncoded
    @POST("get_select_user_by_id.php")
    Call<ServerResponseGetUserById> getUserById(
            @Field("id_user") int id_user
    );
}
