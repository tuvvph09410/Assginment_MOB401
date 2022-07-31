package com.example.assginment_mob403.Fragment.Chi;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.Activity.MainActivityThuChi;
import com.example.assginment_mob403.InterfaceAPI.LoaiChiAPI;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.LoaiChi_Response.ServerResponseUpdateLoaichi;
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

public class Fragment_detail_loaichi extends Fragment {
    TextView tvID;
    TextInputLayout edlLoaiChiName;
    TextInputEditText edLoaiChiName;
    Button btnUpdate, btnExit;
    private int bid_loaichi, bid_user;
    private String bname_loaichi;
    Utilities utilities;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail_loaichi, container, false);

        initGetArgument();

        initViewById(view);

        init();

        clickListener();

        removeErrorTextChange();

        return view;
    }

    private void clickListener() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment();
            }
        });
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUpdateLoaichi() == true) {
                    String name = edLoaiChiName.getText().toString();
                    updateLoaichiAPI(bid_loaichi, name);
                }
            }
        });
    }

    private void loadFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("dataId_user", bid_user);
        Fragment_loaichi fragment_loaichi = new Fragment_loaichi();
        fragment_loaichi.setArguments(bundle);
        ((MainActivityThuChi) getActivity()).loadFragment(fragment_loaichi, "Loại chi");
    }

    private void updateLoaichiAPI(int bid_loaichi, String name) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoaiChiAPI loaiChiAPI = retrofit.create(LoaiChiAPI.class);
        Call<ServerResponseUpdateLoaichi> call = loaiChiAPI.updateLoaiChi(bid_loaichi, name);
        call.enqueue(new Callback<ServerResponseUpdateLoaichi>() {
            @Override
            public void onResponse(Call<ServerResponseUpdateLoaichi> call, Response<ServerResponseUpdateLoaichi> response) {
                ServerResponseUpdateLoaichi serverResponseUpdateLoaichi = response.body();
                Toast.makeText(getContext(), serverResponseUpdateLoaichi.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
                loadFragment();
            }

            @Override
            public void onFailure(Call<ServerResponseUpdateLoaichi> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
    }

    private void init() {
        utilities = new Utilities();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang tải ... ");
        tvID.setText(String.valueOf(bid_loaichi));
        edLoaiChiName.setText(bname_loaichi);
    }

    private void initGetArgument() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bid_loaichi = bundle.getInt("bid_loaichi");
            bid_user = bundle.getInt("bid_user");
            bname_loaichi = bundle.getString("bname_loaichi");
        }
    }

    private void initViewById(View view) {
        tvID = view.findViewById(R.id.tv_id);
        edlLoaiChiName = view.findViewById(R.id.edl_loaichi_name);
        edLoaiChiName = view.findViewById(R.id.ed_loaichi_name);
        btnExit = view.findViewById(R.id.btn_cancel);
        btnUpdate = view.findViewById(R.id.btn_edit);
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

    private boolean validateUpdateLoaichi() {
        Matcher matcherNameLoaiChi = utilities.NSCPattern.matcher(edLoaiChiName.getText().toString());
        Boolean success = true;
        if (edLoaiChiName.getText().toString().equalsIgnoreCase("")) {
            edlLoaiChiName.setError(utilities.LoaiChiRequire);
            success = false;
        } else {
            if (edLoaiChiName.getText().toString().length() <= 2 || edLoaiChiName.getText().toString().length() >=
                    20) {
                edlLoaiChiName.setError(utilities.LoaiChiLength);
                success = false;
            } else {
                if (!matcherNameLoaiChi.matches()) {
                    edlLoaiChiName.setError(utilities.NotSpecialCharacter);
                    success = false;
                }
            }
        }
        return success;
    }

    private void removeErrorTextChange() {
        utilities.removeErrorText(edlLoaiChiName, edLoaiChiName);
    }

}