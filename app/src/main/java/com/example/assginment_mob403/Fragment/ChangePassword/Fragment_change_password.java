package com.example.assginment_mob403.Fragment.ChangePassword;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.Activity.MainActivity;
import com.example.assginment_mob403.InterfaceAPI.UserAPI;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.User_Response.ServerResponseChangePassword;
import com.example.assginment_mob403.URLServer.PathURLServer;
import com.example.assginment_mob403.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_change_password extends Fragment {
    TextInputLayout edlPasswordOld, edlPasswordNew, edlRepasswordNew;
    TextInputEditText edPasswordOld, edPasswordNew, edRepasswordNew;
    TextView tvEmail;
    Button btnChangePassword;
    Utilities utilities;
    private int dataId_user = 0;
    private String dataEmail = "";
    private String dataPassword = "";
    ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);

        initGetArguments();

        initViewByID(view);

        init();

        initClickListener();

        removeErrorTextChange();

        return view;
    }

    private void initClickListener() {
        this.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateFormPassword() == true) {
                    String new_password = edRepasswordNew.getText().toString();
                    changePasswordById(dataId_user, new_password);
                }
            }
        });
    }

    private Boolean validateFormPassword() {

        Matcher matcherPasswordOld = utilities.NSCPattern.matcher(edPasswordOld.getText().toString().trim());
        Matcher matcherPasswordNew = utilities.NSCPattern.matcher(edPasswordNew.getText().toString().trim());
        Matcher matcherRePasswordNew = utilities.NSCPattern.matcher(edRepasswordNew.getText().toString().trim());
        Boolean success = true;

        if (edPasswordOld.getText().toString().trim().equalsIgnoreCase("")) {
            edlPasswordOld.setError(utilities.PasswordRequire);
            success = false;
        } else {

            if (edPasswordOld.getText().toString().trim().length() <= 4 || edPasswordOld.getText().toString().trim().length() >= 20) {
                edlPasswordOld.setError(utilities.PasswordLength);
                success = false;
            } else {
                if (!matcherPasswordOld.matches()) {
                    edlPasswordOld.setError(utilities.NotSpecialCharacter);
                    success = false;
                } else {
                    if (!edPasswordOld.getText().toString().equalsIgnoreCase(dataPassword)) {
                        edlPasswordOld.setError(utilities.PasswordCompare);
                        success = false;
                    }
                }
            }
        }
        if (edPasswordNew.getText().toString().trim().equalsIgnoreCase("")) {
            edlPasswordNew.setError(utilities.PasswordRequire);
            success = false;
        } else {
            if (edPasswordNew.getText().toString().trim().length() <= 4 || edPasswordNew.getText().toString().trim().length() >= 20) {
                edlPasswordNew.setError(utilities.PasswordLength);
                success = false;
            } else {
                if (!matcherPasswordNew.matches()) {
                    edlPasswordNew.setError(utilities.NotSpecialCharacter);
                    success = false;
                } else {
                    if (edPasswordNew.getText().toString().equalsIgnoreCase(dataPassword)) {
                        edlPasswordNew.setError(utilities.PasswordOldNotMatchPasswordNew);
                        success = false;
                    }
                }
            }
        }
        if (edRepasswordNew.getText().toString().trim().equalsIgnoreCase("")) {
            edlRepasswordNew.setError(utilities.PasswordRequire);
            success = false;
        } else {
            if (edRepasswordNew.getText().toString().trim().length() <= 4 || edRepasswordNew.getText().toString().trim().length() >= 20) {
                edlRepasswordNew.setError(utilities.PasswordLength);
                success = false;
            } else {
                if (!matcherRePasswordNew.matches()) {
                    edRepasswordNew.setError(utilities.NotSpecialCharacter);
                    success = false;
                } else {
                    if (!edPasswordNew.getText().toString().equalsIgnoreCase(edRepasswordNew.getText().toString())) {
                        edlRepasswordNew.setError(utilities.RePasswordCompare);
                        success = false;
                    }
                }
            }
        }
        return success;
    }

    private void initGetArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dataId_user = bundle.getInt("dataId_user");
            dataEmail = bundle.getString("dataEmail");
            dataPassword = bundle.getString("dataPassword");
        }
    }

    private void removeErrorTextChange() {
        utilities.removeErrorText(edlPasswordOld, edPasswordOld);
        utilities.removeErrorText(edlPasswordNew, edPasswordNew);
        utilities.removeErrorText(edlRepasswordNew, edRepasswordNew);
    }

    private void init() {
        utilities = new Utilities();
        tvEmail.setText(dataEmail);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang tải ...");
    }

    private void initViewByID(View view) {
        edlPasswordNew = view.findViewById(R.id.edl_password_new);
        edlPasswordOld = view.findViewById(R.id.edl_password_old);
        edlRepasswordNew = view.findViewById(R.id.edl_repassword_new);
        edPasswordOld = view.findViewById(R.id.ed_password_old);
        edPasswordNew = view.findViewById(R.id.ed_password_new);
        edRepasswordNew = view.findViewById(R.id.ed_repassword_new);
        tvEmail = view.findViewById(R.id.tv_email);
        btnChangePassword = view.findViewById(R.id.btn_change_password);

    }

    private void changePasswordById(int id_user, String new_password) {
        showProgressDialog();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<ServerResponseChangePassword> call = userAPI.changePasswordById(id_user, new_password);
        call.enqueue(new Callback<ServerResponseChangePassword>() {
            @Override
            public void onResponse(Call<ServerResponseChangePassword> call, Response<ServerResponseChangePassword> response) {
                ServerResponseChangePassword serverResponseChangePassword = response.body();
                hideProgressDialog();
                Toast.makeText(getContext(), serverResponseChangePassword.getMessage(), Toast.LENGTH_LONG).show();
                initDialog();
            }

            @Override
            public void onFailure(Call<ServerResponseChangePassword> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showProgressDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void initDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Đã đổi mật khẩu thành công, Vui lòng đăng nhập lại!");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startLogin();
            }
        });
        builder.create();
        builder.show();
    }

    private void startLogin() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
    }
}