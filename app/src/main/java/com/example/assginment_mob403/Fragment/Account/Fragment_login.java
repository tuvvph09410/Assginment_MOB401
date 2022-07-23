package com.example.assginment_mob403.Fragment.Account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assginment_mob403.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;


public class Fragment_login extends Fragment {
    private TextInputLayout edlUserName, edlPassword;
    private TextInputEditText edUserName, edPassword;
    private AppCompatCheckBox cbSaveAccount;
    private Button btnLogin, btnSignIn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        initViewByID(view);

        initClickListener();
        return view;
    }

    private void initClickListener() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_signup());
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment);
        fragmentTransaction.commit();
    }

    private void initViewByID(View view) {
        this.edUserName = view.findViewById(R.id.ed_userNameLogin);
        this.edPassword = view.findViewById(R.id.ed_passwordLogin);
        this.edlUserName = view.findViewById(R.id.edl_userNameLogin);
        this.edlPassword = view.findViewById(R.id.edl_passwordLogin);
        this.cbSaveAccount = view.findViewById(R.id.cb_saveAccount);
        this.btnLogin = view.findViewById(R.id.btn_login);
        this.btnSignIn = view.findViewById(R.id.btn_signIn);
    }
}