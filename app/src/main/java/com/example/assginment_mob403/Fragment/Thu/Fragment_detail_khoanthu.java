package com.example.assginment_mob403.Fragment.Thu;

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
import com.example.assginment_mob403.InterfaceAPI.KhoanThuAPI;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.KhoanThu_Response.ServerResponseUpdateKhoanThu;
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


public class Fragment_detail_khoanthu extends Fragment {
    TextView tv_id;
    TextInputLayout edlName, edlMoney, edlNote, edlDateAdd, edlNameLoaithu;
    TextInputEditText edName, edMoney, edNote, edDateAdd, edNameLoaithu;
    Button btnUpdate, btnCancel;
    private int bId_KhoanThu = 0, bid_user = 0, bMoney = 0, id_Loaithu = 0, mDate, mMonth, mYear;
    private String bName = "", bNote = "", bDateAdd = "", bNameLoaithu = "";
    Utilities utilities;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_khoanthu, container, false);

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
                if (validateAddKhoanThu() == true) {
                    String name = edName.getText().toString();
                    String moneyString = edMoney.getText().toString();
                    String note = edNote.getText().toString();
                    String dateAdd = edDateAdd.getText().toString();
                    try {

                        int money = Integer.parseInt(moneyString);
                        updateKhoanThuAPI(bId_KhoanThu, name, money, note, dateAdd);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        edlMoney.setError(utilities.KhoanThuMoneyInvalid);
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

    private void updateKhoanThuAPI(int bId_KhoanThu, String name, int money, String note, String dateAdd) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KhoanThuAPI KhoanThuAPI = retrofit.create(KhoanThuAPI.class);
        Call<ServerResponseUpdateKhoanThu> call = KhoanThuAPI.updateKhoanThu(bId_KhoanThu, name, money, note, dateAdd);
        call.enqueue(new Callback<ServerResponseUpdateKhoanThu>() {
            @Override
            public void onResponse(Call<ServerResponseUpdateKhoanThu> call, Response<ServerResponseUpdateKhoanThu> response) {
                ServerResponseUpdateKhoanThu serverResponseUpdateKhoanThu = response.body();
                Toast.makeText(getContext(), serverResponseUpdateKhoanThu.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
                loadFragment();
            }

            @Override
            public void onFailure(Call<ServerResponseUpdateKhoanThu> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
    }

    private void loadFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt("dataId_user", bid_user);
        Fragment_khoanthu fragment_KhoanThu = new Fragment_khoanthu();
        fragment_KhoanThu.setArguments(bundle);
        ((MainActivityThuChi) getActivity()).loadFragment(fragment_KhoanThu, "Khoản chi");
    }

    private void init() {
        utilities = new Utilities();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang tải ...");
        edName.setText(bName);
        tv_id.setText(String.valueOf(bId_KhoanThu));
        edMoney.setText(String.valueOf(bMoney));
        edNote.setText(bNote);
        edDateAdd.setText(bDateAdd);
        edNameLoaithu.setText(bNameLoaithu);
    }

    private void initGetArgument() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            bId_KhoanThu = bundle.getInt("bId_KhoanThu");
            bid_user = bundle.getInt("bId_user");
            bMoney = bundle.getInt("bMoney");
            bName = bundle.getString("bName");
            bNote = bundle.getString("bNote");
            bDateAdd = bundle.getString("bDateAdd");
            bNameLoaithu = bundle.getString("bNameLoaiThu");
        }
    }

    private void initViewById(View view) {
        tv_id = view.findViewById(R.id.tv_id);
        edlName = view.findViewById(R.id.edl_khoanthu_name);
        edlMoney = view.findViewById(R.id.edl_khoanthu_money);
        edlNote = view.findViewById(R.id.edl_khoanthu_note);
        edlDateAdd = view.findViewById(R.id.edl_khoanthu_date_add);
        edlNameLoaithu = view.findViewById(R.id.edl_loaithu_name);

        edName = view.findViewById(R.id.ed_khoanthu_name);
        edNameLoaithu = view.findViewById(R.id.ed_loaithu_name);
        edMoney = view.findViewById(R.id.ed_khoanthu_money);
        edNote = view.findViewById(R.id.ed_khoanthu_note);
        edDateAdd = view.findViewById(R.id.ed_khoanthu_date_add);

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

    private Boolean validateAddKhoanThu() {
        Boolean success = true;
        Matcher matcherName = utilities.NSCPattern.matcher(edName.getText().toString());
        Matcher matcherMoney = utilities.NSCPattern.matcher(edMoney.getText().toString());
        if (edName.getText().toString().equalsIgnoreCase("")) {
            edlName.setError(utilities.KhoanThuNameRequire);
            success = false;
        } else {
            if (edName.getText().toString().length() <= 2 || edName.getText().toString().length() >= 20) {
                edlName.setError(utilities.KhoanThuNameLength);
                success = false;
            } else {
                if (!matcherName.matches()) {
                    edlName.setError(utilities.NotSpecialCharacter);
                    success = false;
                }
            }
        }
        if (edMoney.getText().toString().equalsIgnoreCase("")) {
            edlMoney.setError(utilities.KhoanThuMoneyRequire);
            success = false;
        } else {
            if (!matcherMoney.matches()) {
                edlMoney.setError(utilities.NotSpecialCharacter);
                success = false;
            }
        }
        if (edDateAdd.getText().toString().equalsIgnoreCase("")) {
            edlDateAdd.setError(utilities.KhoanThuSelectDate);
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