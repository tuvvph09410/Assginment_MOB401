package com.example.assginment_mob403.Fragment.Thu;

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
import com.example.assginment_mob403.Activity.MainActivityThuChi;
import com.example.assginment_mob403.InterfaceAPI.LoaiThuAPI;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.LoaiThu_Response.ServerResponseUpdateLoaiThu;
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


public class Fragment_detail_loaithu extends Fragment {
    TextView tvID;
    TextInputLayout edlLoaiThuName;
    TextInputEditText edLoaiThuName;
    Button btnUpdate, btnExit;
    private int bid_LoaiThu, bid_user;
    private String bname_LoaiThu;
    Utilities utilities;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_loaithu, container, false);
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
                if (validateUpdateLoaiThu() == true) {
                    String name = edLoaiThuName.getText().toString();
                    updateLoaiThuAPI(bid_LoaiThu, name);
                }
            }
        });
    }

    private void loadFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("dataId_user", bid_user);
        Fragment_loaithu fragment_LoaiThu = new Fragment_loaithu();
        fragment_LoaiThu.setArguments(bundle);
        ((MainActivityThuChi) getActivity()).loadFragment(fragment_LoaiThu, "Loại Thu");
    }

    private void updateLoaiThuAPI(int bid_LoaiThu, String name) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoaiThuAPI LoaiThuAPI = retrofit.create(LoaiThuAPI.class);
        Call<ServerResponseUpdateLoaiThu> call = LoaiThuAPI.updateLoaiThu(bid_LoaiThu, name);
        call.enqueue(new Callback<ServerResponseUpdateLoaiThu>() {
            @Override
            public void onResponse(Call<ServerResponseUpdateLoaiThu> call, Response<ServerResponseUpdateLoaiThu> response) {
                ServerResponseUpdateLoaiThu serverResponseUpdateLoaiThu = response.body();
                Toast.makeText(getContext(), serverResponseUpdateLoaiThu.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
                loadFragment();
            }

            @Override
            public void onFailure(Call<ServerResponseUpdateLoaiThu> call, Throwable t) {
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
        tvID.setText(String.valueOf(bid_LoaiThu));
        edLoaiThuName.setText(bname_LoaiThu);
    }

    private void initGetArgument() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bid_LoaiThu = bundle.getInt("bid_LoaiThu");
            bid_user = bundle.getInt("bid_user");
            bname_LoaiThu = bundle.getString("bname_LoaiThu");
        }
    }

    private void initViewById(View view) {
        tvID = view.findViewById(R.id.tv_id);
        edlLoaiThuName = view.findViewById(R.id.edl_loaithu_name);
        edLoaiThuName = view.findViewById(R.id.ed_loaithu_name);
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

    private boolean validateUpdateLoaiThu() {
        Matcher matcherNameLoaiThu = utilities.NSCPattern.matcher(edLoaiThuName.getText().toString());
        Boolean success = true;
        if (edLoaiThuName.getText().toString().equalsIgnoreCase("")) {
            edlLoaiThuName.setError(utilities.LoaiThuRequire);
            success = false;
        } else {
            if (edLoaiThuName.getText().toString().length() <= 2 || edLoaiThuName.getText().toString().length() >=
                    20) {
                edlLoaiThuName.setError(utilities.LoaiThuLength);
                success = false;
            } else {
                if (!matcherNameLoaiThu.matches()) {
                    edlLoaiThuName.setError(utilities.NotSpecialCharacter);
                    success = false;
                }
            }
        }
        return success;
    }

    private void removeErrorTextChange() {
        utilities.removeErrorText(edlLoaiThuName, edLoaiThuName);
    }
}