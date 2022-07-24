package com.example.assginment_mob403.Fragment.Account;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assginment_mob403.Interface.UserAPI;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.ServerResponseSelectAccount;
import com.example.assginment_mob403.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_login extends Fragment {
    private TextInputLayout edlEmail, edlPassword;
    private TextInputEditText edEmail, edPassword;
    private AppCompatCheckBox cbSaveAccount;
    private Button btnLogin, btnSignIn;
    private SharedPreferences sharedPreferences;
    private Utilities utilities;
    private ProgressDialog progressDialog;
    private String baseURL = "https://tucaomypham.000webhostapp.com/android_networking_mob403/assginment/";
    private static final String TAG = Fragment_login.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initViewByID(view);

        initClickListener();

        init();

        Remember();

        removeErrorTextOnChange();

        initProgreesDiaLog();

        return view;
    }

    private void initProgreesDiaLog() {
        this.progressDialog = new ProgressDialog(getContext());
        this.progressDialog.setMessage("Đang kiểm tra ...");
        this.progressDialog.setCancelable(false);
    }

    private void removeErrorTextOnChange() {
        this.utilities.removeErrorText(edlEmail, edEmail);
        this.utilities.removeErrorText(edlPassword, edPassword);
    }

    private void Remember() {
        String login = sharedPreferences.getString("login", "");
        if (login.equalsIgnoreCase("remember")) {
            edEmail.setText(sharedPreferences.getString("Username", ""));
            edPassword.setText(sharedPreferences.getString("Password", ""));
            cbSaveAccount.setChecked(true);
        } else {
            edEmail.setText(sharedPreferences.getString("Username", ""));
        }
    }

    private void init() {
        utilities = new Utilities();
        sharedPreferences = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
    }

    private void initClickListener() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_signin());
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateLogin() == true) {
                    String email = edEmail.getText().toString().trim();
                    String password = edPassword.getText().toString().trim();
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("Username", email);
                    editor.putString("Role", "user");
                    if (checkAPILogin(email, password) == true) {
                        if (cbSaveAccount.isChecked()) {
                            editor.putString("login", "remember");
                            editor.putString("Password", password);
                        } else {
                            editor.putString("login", "no");
                            editor.putString("Password", "");
                        }
                        editor.commit();
                    }
                }
            }
        });
    }

    private Boolean checkAPILogin(String email, String password) {
        final Boolean[] success = {true};
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<ServerResponseSelectAccount> call = userAPI.getSelectUser_checkLogin(email, password);
        call.enqueue(new Callback<ServerResponseSelectAccount>() {
            @Override
            public void onResponse(Call<ServerResponseSelectAccount> call, Response<ServerResponseSelectAccount> response) {
                success[0] = true;
                ServerResponseSelectAccount serverResponseSelectAccount = response.body();
                hideProgress();
                Toast.makeText(getContext(), serverResponseSelectAccount.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponseSelectAccount> call, Throwable t) {
                success[0] = false;
                hideProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });

        return success[0];
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment);
        fragmentTransaction.commit();
    }

    private void initViewByID(View view) {
        this.edEmail = view.findViewById(R.id.ed_userEmail);
        this.edPassword = view.findViewById(R.id.ed_passwordLogin);
        this.edlEmail = view.findViewById(R.id.edl_userEmail);
        this.edlPassword = view.findViewById(R.id.edl_passwordLogin);
        this.cbSaveAccount = view.findViewById(R.id.cb_saveAccount);
        this.btnLogin = view.findViewById(R.id.btn_login);
        this.btnSignIn = view.findViewById(R.id.btn_signIn);
    }

    private Boolean validateLogin() {
        final String EMAIL_REGEX = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
        Matcher matcherPassword = this.utilities.NSCPattern.matcher(edPassword.getText().toString().trim());
        Boolean success = true;

        if (edEmail.getText().toString().trim().equalsIgnoreCase("")) {
            this.edlEmail.setError(this.utilities.EmailRequire);
            success = false;
        }
        if (!edEmail.getText().toString().trim().matches(EMAIL_REGEX)) {
            this.edlEmail.setError(this.utilities.EmailInvalid);
            success = false;
        }
        if (!matcherPassword.matches()) {
            this.edlPassword.setError(this.utilities.NotSpecialCharacter);
            success = false;
        }
        if (edPassword.getText().toString().trim().length() <= 4 || edPassword.getText().toString().trim().length() >= 20) {
            this.edlPassword.setError(this.utilities.PasswordLength);
            success = false;
        }
        if (edPassword.getText().toString().trim().equalsIgnoreCase("")) {
            this.edlPassword.setError(this.utilities.PasswordRequire);
            success = false;
        }
        return success;
    }

    private void showProgress() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideProgress() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}