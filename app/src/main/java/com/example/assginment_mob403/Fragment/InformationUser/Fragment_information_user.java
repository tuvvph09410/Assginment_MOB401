package com.example.assginment_mob403.Fragment.InformationUser;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.R;
import com.example.assginment_mob403.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


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

        removeErrorTextChange();

        return view;
    }

    private void removeErrorTextChange() {
        this.utilities.removeErrorText(edlFirstName, edFirstName);
        this.utilities.removeErrorText(edlLastName, edLastName);
        this.utilities.removeErrorText(edlEmail, edEmail);
        this.utilities.removeErrorText(edlPhone, edPhone);
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