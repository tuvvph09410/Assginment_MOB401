package com.example.assginment_mob403.Fragment.Chi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Fragment_detail_loaichi extends Fragment {
    TextView tvID;
    TextInputLayout edlLoaiChiName;
    TextInputEditText edLoaiChiName;
    Button btnUpdate, btnExit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_loaichi, container, false);

        initGetArgument();

        initViewById(view);

        return view;
    }

    private void initGetArgument() {

    }

    private void initViewById(View view) {
        tvID = view.findViewById(R.id.tv_id);
        edlLoaiChiName = view.findViewById(R.id.edl_loaichi_name);
        edLoaiChiName = view.findViewById(R.id.ed_loaichi_name);
        btnExit = view.findViewById(R.id.btn_cancel);
        btnUpdate = view.findViewById(R.id.btn_edit);
    }
}