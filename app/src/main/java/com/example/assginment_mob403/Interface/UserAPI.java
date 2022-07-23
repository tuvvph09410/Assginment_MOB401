package com.example.assginment_mob403.Interface;

import com.example.assginment_mob403.ServerResponse.ServerResponseSignUp;

import java.util.Date;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface UserAPI {
    @FormUrlEncoded
    @POST("insert_user.php")
    Call<ServerResponseSignUp> insertUser(
            @Field("first_name") String first_name,
            @Field("last_name") String last_name,
            @Field("password") String password,
            @Field("email") String email,
            @Field("phone") int phone,
            @Field("registration_date") String registration_date
    );
}
