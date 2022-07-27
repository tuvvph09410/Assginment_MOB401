package com.example.assginment_mob403.Fragment.InformationUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.Interface.UserAPI;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.ServerResponseUpdateUser;
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


public class Fragment_information_user extends Fragment {
    TextInputLayout edlLastName, edlFirstName, edlPhone, edlEmail, edlRegistrationDate;
    TextInputEditText edFirstName, edLastName, edPhone, edEmail, edRegistrationDate;
    TextView tvEmail;
    Button btnEdit;
    private Utilities utilities;
    private int dataId_user = 0;
    private String dataFirst_name = "";
    private String dataLast_name = "";
    private String dataEmail = "";
    private int dataPhone = 0;
    private String dataRegistration_date = "";

    private static final String TAG = Fragment_information_user.class.getSimpleName();

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_information_user, container, false);

        initGetArguments();

        initViewByID(view);

        init();

        initClickListener();

        removeErrorTextChange();

        return view;
    }

    private void initClickListener() {
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateEditInformation() == true) {
                    String firstName = edFirstName.getText().toString();
                    String lastName = edLastName.getText().toString();
                    String email = edEmail.getText().toString();
                    String stringPhone = edPhone.getText().toString();
                    try {
                        int phone = Integer.parseInt(stringPhone);
                        updateUserServerAPI(dataId_user, firstName, lastName, phone);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), utilities.PhoneType, Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private void updateUserServerAPI(int dataId_user, String firstName, String lastName, int phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<ServerResponseUpdateUser> call = userAPI.updateUser(dataId_user, firstName, lastName, phone);
        call.enqueue(new Callback<ServerResponseUpdateUser>() {
            @Override
            public void onResponse(Call<ServerResponseUpdateUser> call, Response<ServerResponseUpdateUser> response) {
                ServerResponseUpdateUser serverResponseUpdateUser = response.body();
                Toast.makeText(getContext(), serverResponseUpdateUser.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponseUpdateUser> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void removeErrorTextChange() {
        this.utilities.removeErrorText(edlFirstName, edFirstName);
        this.utilities.removeErrorText(edlLastName, edLastName);
        this.utilities.removeErrorText(edlEmail, edEmail);
        this.utilities.removeErrorText(edlPhone, edPhone);
    }

    private Boolean validateEditInformation() {
        Matcher matcherFirstName = this.utilities.NSCPattern.matcher(edFirstName.getText().toString().trim());
        Matcher matcherLastName = this.utilities.NSCPattern.matcher(edLastName.getText().toString().trim());
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

        if (edPhone.getText().toString().equalsIgnoreCase("")) {
            edlPhone.setError(utilities.PhoneRequire);
        } else {
            if (edPhone.getText().toString().equalsIgnoreCase("")) {
                edlPhone.setError(this.utilities.PhoneLength);
                success = false;
            } else {
                if (edPhone.getText().toString().trim().length() <= 9 || edPhone.getText().toString().trim().length() >= 12) {
                    edlPhone.setError(this.utilities.PhoneLength);
                    success = false;
                }
            }
        }

        return success;
    }

    private void init() {
        utilities = new Utilities();
        tvEmail.setText(dataEmail);
        edFirstName.setText(dataFirst_name);
        edLastName.setText(dataLast_name);
        edEmail.setText(dataEmail);
        edPhone.setText(("0" + dataPhone));
        edRegistrationDate.setText(dataRegistration_date);
    }

    private void initGetArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dataId_user = bundle.getInt("dataId_user");
            dataFirst_name = bundle.getString("dataFirst_name");
            dataLast_name = bundle.getString("dataLast_name");
            dataEmail = bundle.getString("dataEmail");
            dataPhone = bundle.getInt("dataPhone");
            dataRegistration_date = bundle.getString("dataRegistration_date");
        }
    }

    private void initViewByID(View view) {

        edlFirstName = view.findViewById(R.id.edl_first_name);
        edlLastName = view.findViewById(R.id.edl_last_name);
        edlPhone = view.findViewById(R.id.edl_phone);
        edlEmail = view.findViewById(R.id.edl_email);
        edlRegistrationDate = view.findViewById(R.id.edl_registration_date);

        edFirstName = view.findViewById(R.id.ed_first_name);
        edLastName = view.findViewById(R.id.ed_last_name);
        edPhone = view.findViewById(R.id.ed_phone);
        edEmail = view.findViewById(R.id.ed_email);
        edRegistrationDate = view.findViewById(R.id.ed_registration_date);

        tvEmail = view.findViewById(R.id.tv_email);
        btnEdit = view.findViewById(R.id.btn_edit);
    }
}