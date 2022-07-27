package com.example.assginment_mob403.Fragment.User;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assginment_mob403.Interface.UserAPI;
import com.example.assginment_mob403.Model.User;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.ServerResponseSignUp;
import com.example.assginment_mob403.URLServer.PathURLServer;
import com.example.assginment_mob403.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_signin extends Fragment {
    TextInputLayout edlFirstName, edlLastName, edlEmail, edlPasswordSignIn, edlRePasswordSignIn, edlPhoneSignIn;
    TextInputEditText edFirstName, edLastName, edEmail, edPasswordSignIn, edRePasswordSignIn, edPhoneSignIn;
    Button btnSaveSignIn;
    TextView tvLogin;
    ProgressDialog progressDialog;
    Utilities utilities;
    private final static String TAG = Fragment_signin.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signin, container, false);

        initViewByID(view);

        initClickListener();

        initProgressDialog();

        removeErrorTextOnChange();

        return view;
    }

    private Boolean validateSignIn() {
        final String EMAIL_REGEX = "^[A-Za-z0-9]+[A-Za-z0-9]*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)$";
        Matcher matcherFirstName = this.utilities.NSCPattern.matcher(edFirstName.getText().toString().trim());
        Matcher matcherLastName = this.utilities.NSCPattern.matcher(edLastName.getText().toString().trim());
        Matcher matcherPassword = this.utilities.NSCPattern.matcher(edPasswordSignIn.getText().toString().trim());
        Boolean success = true;
        if (edFirstName.getText().toString().trim().equalsIgnoreCase("")) {
            this.edlFirstName.setError(this.utilities.FirstNameRequire);
            success = false;
        } else {
            if (edFirstName.getText().toString().trim().length() < 2 || edFirstName.getText().toString().trim().length() > 20) {
                this.edlFirstName.setError(this.utilities.FirstNameLength);
                success = false;
            } else {
                if (!matcherFirstName.matches()) {
                    this.edlFirstName.setError(this.utilities.NotSpecialCharacter);
                    success = false;
                }
            }
        }
        if (edLastName.getText().toString().trim().equalsIgnoreCase("")) {
            this.edlLastName.setError(this.utilities.LastNameRequire);
            success = false;
        } else {
            if (edLastName.getText().toString().trim().length() < 2 || edLastName.getText().toString().trim().length() > 20) {
                this.edlLastName.setError(this.utilities.LastNameLength);
                success = false;
            } else {
                if (!matcherLastName.matches()) {
                    this.edlLastName.setError(this.utilities.NotSpecialCharacter);
                    success = false;
                }
            }
        }

        if (edPasswordSignIn.getText().toString().trim().equalsIgnoreCase("")) {
            this.edlPasswordSignIn.setError(this.utilities.PasswordRequire);
            success = false;
        } else {
            if (edPasswordSignIn.getText().toString().trim().length() < 4 || edPasswordSignIn.getText().toString().trim().length() > 20) {
                this.edlPasswordSignIn.setError(this.utilities.PasswordLength);
                success = false;
            } else {
                if (!matcherPassword.matches()) {
                    this.edlPasswordSignIn.setError(this.utilities.NotSpecialCharacter);
                    success = false;
                }
            }
        }
        if (edRePasswordSignIn.getText().toString().trim().equalsIgnoreCase("")) {
            this.edlRePasswordSignIn.setError(this.utilities.ConfirmPasswordRequire);
            success = false;
        } else {
            if (!edPasswordSignIn.getText().toString().equalsIgnoreCase(edRePasswordSignIn.getText().toString())) {
                this.edlRePasswordSignIn.setError(this.utilities.PasswordCompare);
                success = false;
            }
        }

        if (edEmail.getText().toString().trim().equalsIgnoreCase("")) {
            this.edlEmail.setError(this.utilities.EmailRequire);
            success = false;
        } else {
            if (!edEmail.getText().toString().trim().matches(EMAIL_REGEX)) {
                this.edlEmail.setError(this.utilities.EmailInvalid);
                success = false;
            }
        }

        if (edPhoneSignIn.getText().toString().equalsIgnoreCase("")) {
            edlPhoneSignIn.setError(this.utilities.PhoneLength);
            success = false;
        } else {
            if (edPhoneSignIn.getText().toString().trim().length() <= 9 || edPhoneSignIn.getText().toString().trim().length() >= 12) {
                edlPhoneSignIn.setError(this.utilities.PhoneLength);
                success = false;
            }
        }

        return success;
    }

    private void removeErrorTextOnChange() {
        this.utilities.removeErrorText(edlFirstName, edFirstName);
        this.utilities.removeErrorText(edlLastName, edLastName);
        this.utilities.removeErrorText(edlEmail, edEmail);
        this.utilities.removeErrorText(edlPhoneSignIn, edPhoneSignIn);
        this.utilities.removeErrorText(edlPasswordSignIn, edPasswordSignIn);
        this.utilities.removeErrorText(edlRePasswordSignIn, edRePasswordSignIn);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang xử lý ... ");
        progressDialog.setCancelable(false);
    }

    private void initClickListener() {
        this.tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_login());
            }
        });
        this.btnSaveSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateSignIn() == true) {
                    try {
                        String first_Name = edFirstName.getText().toString();
                        String last_Name = edLastName.getText().toString();
                        String password = edRePasswordSignIn.getText().toString();
                        String email = edEmail.getText().toString();
                        int phone = Integer.parseInt(edPhoneSignIn.getText().toString());
                        Date dateCurrent = Calendar.getInstance().getTime();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String registration_date = simpleDateFormat.format(dateCurrent);
                        insertDataProcess(first_Name, last_Name, password, email, phone, registration_date);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        edlPhoneSignIn.setError(utilities.PhoneInvalid);
                    }
                }
            }
        });
    }

    private void insertDataProcess(String first_name, String last_name, String password, String email, int phone, String registration_date) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        User user = new User(first_name, last_name, email, phone, password, registration_date);
        Call<ServerResponseSignUp> call = userAPI.insertUser(user.getFirst_name(), user.getLast_name(), user.getPassword(), user.getEmail(), user.getPhone(), user.getRegistration_date());
        call.enqueue(new Callback<ServerResponseSignUp>() {
            @Override
            public void onResponse(Call<ServerResponseSignUp> call, Response<ServerResponseSignUp> response) {
                ServerResponseSignUp serverResponseSignUp = response.body();
                hideProgress();
                Toast.makeText(getContext(), serverResponseSignUp.getMessage(), Toast.LENGTH_LONG).show();

            }

            @Override
            public void onFailure(Call<ServerResponseSignUp> call, Throwable t) {
                hideProgress();
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment);
        fragmentTransaction.commit();
    }

    private void initViewByID(View view) {
        this.edlFirstName = view.findViewById(R.id.edl_fisrtName);
        this.edlLastName = view.findViewById(R.id.edl_lastName);
        this.edlPasswordSignIn = view.findViewById(R.id.edl_passwordSignIn);
        this.edlRePasswordSignIn = view.findViewById(R.id.edl_rePasswordSignIn);
        this.edlEmail = view.findViewById(R.id.edl_email);
        this.edFirstName = view.findViewById(R.id.ed_fisrtName);
        this.edLastName = view.findViewById(R.id.ed_lastName);
        this.edEmail = view.findViewById(R.id.ed_email);
        this.edPasswordSignIn = view.findViewById(R.id.ed_passwordSignIn);
        this.edRePasswordSignIn = view.findViewById(R.id.ed_rePasswordSignIn);
        this.edlPhoneSignIn = view.findViewById(R.id.edl_phone);
        this.edPhoneSignIn = view.findViewById(R.id.ed_phone);
        this.btnSaveSignIn = view.findViewById(R.id.btn_saveSignIn);
        this.tvLogin = view.findViewById(R.id.tv_login);
        utilities = new Utilities();
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