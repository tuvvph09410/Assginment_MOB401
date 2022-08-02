package com.example.assginment_mob403.Fragment.Chi;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.Activity.MainActivityThuChi;
import com.example.assginment_mob403.InterfaceAPI.KhoanChiAPI;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.KhoanChi_Response.ServerResponseUpdateKhoanChi;
import com.example.assginment_mob403.URLServer.PathURLServer;
import com.example.assginment_mob403.Utilities.Utilities;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.regex.Matcher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_detail_khoanchi extends Fragment {
    TextView tv_id;
    TextInputLayout edlName, edlMoney, edlNote, edlDateAdd, edlNameLoaiChi;
    TextInputEditText edName, edMoney, edNote, edDateAdd, edNameLoaiChi;
    Button btnUpdate, btnCancel;
    private int bId_khoanchi = 0, bid_user = 0, bMoney = 0, id_loaichi = 0, mDate, mMonth, mYear;
    private String bName = "", bNote = "", bDateAdd = "", bNameLoaiChi = "";
    Utilities utilities;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_khoanchi, container, false);

        initGetArgument();

        initViewById(view);

        init();

        clickListener();

        edlDateAddDatePickerClickListener();

        removeErrorTextChange();

        return view;
    }

    private void clickListener() {
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateAddKhoanChi() == true) {
                    String name = edName.getText().toString();
                    String moneyString = edMoney.getText().toString();
                    String note = edNote.getText().toString();
                    String dateAdd = edDateAdd.getText().toString();
                    try {

                        int money = Integer.parseInt(moneyString);
                        updateKhoanChiAPI(bId_khoanchi, name, money, note, dateAdd);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        edlMoney.setError(utilities.KhoanchiMoneyInvalid);
                    }
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment();
            }
        });
    }

    private void updateKhoanChiAPI(int bId_khoanchi, String name, int money, String note, String dateAdd) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KhoanChiAPI khoanChiAPI = retrofit.create(KhoanChiAPI.class);
        Call<ServerResponseUpdateKhoanChi> call = khoanChiAPI.updateKhoanchi(bId_khoanchi, name, money, note, dateAdd);
        call.enqueue(new Callback<ServerResponseUpdateKhoanChi>() {
            @Override
            public void onResponse(Call<ServerResponseUpdateKhoanChi> call, Response<ServerResponseUpdateKhoanChi> response) {
                ServerResponseUpdateKhoanChi serverResponseUpdateKhoanChi = response.body();
                Toast.makeText(getContext(), serverResponseUpdateKhoanChi.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
                loadFragment();
            }

            @Override
            public void onFailure(Call<ServerResponseUpdateKhoanChi> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
    }

    private void loadFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("dataId_user", bid_user);
        Fragment_khoanchi fragment_khoanchi = new Fragment_khoanchi();
        fragment_khoanchi.setArguments(bundle);
        ((MainActivityThuChi) getActivity()).loadFragment(fragment_khoanchi, "Khoản chi");
    }

    private void init() {
        utilities = new Utilities();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang tải ...");
        edName.setText(bName);
        tv_id.setText(String.valueOf(bId_khoanchi));
        edMoney.setText(String.valueOf(bMoney));
        edNote.setText(bNote);
        edDateAdd.setText(bDateAdd);
        edNameLoaiChi.setText(bNameLoaiChi);
    }

    private void initGetArgument() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bId_khoanchi = bundle.getInt("bId_khoanchi");
            bid_user = bundle.getInt("bId_user");
            bMoney = bundle.getInt("bMoney");
            bName = bundle.getString("bName");
            bNote = bundle.getString("bNote");
            bDateAdd = bundle.getString("bDateAdd");
            bNameLoaiChi = bundle.getString("bNameLoaiChi");
        }
    }

    private void initViewById(View view) {
        tv_id = view.findViewById(R.id.tv_id);
        edlName = view.findViewById(R.id.edl_khoanchi_name);
        edlMoney = view.findViewById(R.id.edl_khoanchi_money);
        edlNote = view.findViewById(R.id.edl_khoanchi_note);
        edlDateAdd = view.findViewById(R.id.edl_khoanchi_date_add);
        edlNameLoaiChi = view.findViewById(R.id.edl_loaichi_name);

        edName = view.findViewById(R.id.ed_khoanchi_name);
        edNameLoaiChi = view.findViewById(R.id.ed_loaichi_name);
        edMoney = view.findViewById(R.id.ed_khoanchi_money);
        edNote = view.findViewById(R.id.ed_khoanchi_note);
        edDateAdd = view.findViewById(R.id.ed_khoanchi_date_add);

        btnCancel = view.findViewById(R.id.btn_cancel);
        btnUpdate = view.findViewById(R.id.btn_edit);
    }

    private void edlDateAddDatePickerClickListener() {
        edDateAdd.setClickable(true);
        edDateAdd.setFocusable(false);
        edDateAdd.setInputType(InputType.TYPE_NULL);
        edDateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                mDate = calendar.get(Calendar.DATE);
                mMonth = calendar.get(Calendar.MONTH);
                mYear = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        edDateAdd.setText(date + "/" + month + "/" + year);
                    }
                }, mYear, mMonth, mDate);
                datePickerDialog.show();
            }
        });
    }

    private Boolean validateAddKhoanChi() {
        Boolean success = true;
        Matcher matcherName = utilities.NSCPattern.matcher(edName.getText().toString());
        Matcher matcherMoney = utilities.NSCPattern.matcher(edMoney.getText().toString());
        if (edName.getText().toString().equalsIgnoreCase("")) {
            edlName.setError(utilities.KhoanchiNameRequire);
            success = false;
        } else {
            if (edName.getText().toString().length() <= 2 || edName.getText().toString().length() >= 20) {
                edlName.setError(utilities.KhoanchiNameLength);
                success = false;
            } else {
                if (!matcherName.matches()) {
                    edlName.setError(utilities.NotSpecialCharacter);
                    success = false;
                }
            }
        }
        if (edMoney.getText().toString().equalsIgnoreCase("")) {
            edlMoney.setError(utilities.KhoanchiMoneyRequire);
            success = false;
        } else {
            if (!matcherMoney.matches()) {
                edlMoney.setError(utilities.NotSpecialCharacter);
                success = false;
            }
        }
        if (edDateAdd.getText().toString().equalsIgnoreCase("")) {
            edlDateAdd.setError(utilities.KhoanchiSelectDate);
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

    private void removeErrorTextChange() {
        utilities.removeErrorText(edlName, edName);
        utilities.removeErrorText(edlMoney, edMoney);
        utilities.removeErrorText(edlNote, edNote);
    }

}