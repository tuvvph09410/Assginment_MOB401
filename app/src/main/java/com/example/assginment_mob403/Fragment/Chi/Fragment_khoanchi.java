package com.example.assginment_mob403.Fragment.Chi;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.Adapter.ItemListViewAdapterKhoanChi;
import com.example.assginment_mob403.Adapter.ItemSpinnerAdapterLoaiChi;
import com.example.assginment_mob403.InterfaceAPI.KhoanChiAPI;
import com.example.assginment_mob403.InterfaceAPI.LoaiChiAPI;
import com.example.assginment_mob403.InterfaceListener.ItemClickListenerKhoanChi;
import com.example.assginment_mob403.InterfaceListener.ItemClickListenerLoaiChi;
import com.example.assginment_mob403.Model.KhoanChi;
import com.example.assginment_mob403.Model.LoaiChi;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.KhoanChi_Response.ServerResponseDeleteKhoanChi;
import com.example.assginment_mob403.ServerResponse.KhoanChi_Response.ServerResponseInsertKhoanChi;
import com.example.assginment_mob403.ServerResponse.KhoanChi_Response.ServerResponseSelectedDataById;
import com.example.assginment_mob403.ServerResponse.LoaiChi_Response.ServerResponseSelectLoaiChi;
import com.example.assginment_mob403.URLServer.PathURLServer;
import com.example.assginment_mob403.Utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Fragment_khoanchi extends Fragment {
    ListView listView;
    SearchView searchView;
    FloatingActionButton fabAddKhoanChi;
    Button btnAdd, btnCancel;
    TextInputLayout edlName, edlMoney, edlNote, edlDateAdd;
    TextInputEditText edName, edMoney, edNote, edDateAdd;
    Spinner spinnerLoaiChi;
    private Utilities utilities;
    private int dataId_user, mDate, mMonth, mYear;
    Dialog dialog;
    TextView tvNotify;
    ItemSpinnerAdapterLoaiChi itemSpinnerAdapterLoaiChi;
    private int id_loaichi = 0;
    private static final String TAG = Fragment_loaichi.class.getSimpleName();
    ProgressDialog progressDialog;
    ItemListViewAdapterKhoanChi itemListViewAdapterKhoanChi;
    private List<KhoanChi> khoanChiList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_khoanchi, container, false);

        initGetArguments();

        initViewById(view);

        init();

        getSelecteDatKhoanChiByIdUser(dataId_user);

        fabAddClickListener();

        clickListenerSearchView();


        return view;

    }

    private void clickListenerSearchView() {
        searchView.setQueryHint("Tìm kiếm theo tên khoản chi");
    }

    private void initGetArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dataId_user = bundle.getInt("dataId_user");
        }

    }

    private void init() {
        utilities = new Utilities();
        itemSpinnerAdapterLoaiChi = new ItemSpinnerAdapterLoaiChi(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang load ...");
        itemListViewAdapterKhoanChi = new ItemListViewAdapterKhoanChi(getContext());
        itemClickListView();
    }

    private void itemClickListView() {
        itemListViewAdapterKhoanChi.setOnItemDeleteClickListener(new ItemClickListenerKhoanChi() {
            @Override
            public void onItemClick(int position) {
                deleteLoaiChiAPI(position, dataId_user);
            }
        });
    }

    private void deleteLoaiChiAPI(int id_khoanchi, int dataId_user) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KhoanChiAPI khoanChiAPI = retrofit.create(KhoanChiAPI.class);
        Call<ServerResponseDeleteKhoanChi> call = khoanChiAPI.deleteKhoanchi(id_khoanchi);
        call.enqueue(new Callback<ServerResponseDeleteKhoanChi>() {
            @Override
            public void onResponse(Call<ServerResponseDeleteKhoanChi> call, Response<ServerResponseDeleteKhoanChi> response) {
                ServerResponseDeleteKhoanChi serverResponseDeleteKhoanChi = response.body();
                Toast.makeText(getContext(), serverResponseDeleteKhoanChi.getMessage(), Toast.LENGTH_LONG).show();
                getSelecteDatKhoanChiByIdUser(dataId_user);
                hideProgress();
            }

            @Override
            public void onFailure(Call<ServerResponseDeleteKhoanChi> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
    }

    private void fabAddClickListener() {
        fabAddKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_khoanchi);
                dialog.show();

                btnAdd = dialog.findViewById(R.id.btn_add);
                btnCancel = dialog.findViewById(R.id.btn_cancel);
                edlName = dialog.findViewById(R.id.edl_name_khoanchi);
                edlMoney = dialog.findViewById(R.id.edl_money_khoanchi);
                edlNote = dialog.findViewById(R.id.edl_note_khoanchi);
                edlDateAdd = dialog.findViewById(R.id.edl_date_khoanchi);
                edName = dialog.findViewById(R.id.ed_name_khoanchi);
                edMoney = dialog.findViewById(R.id.ed_money_khoanchi);
                edNote = dialog.findViewById(R.id.ed_note_khoanchi);
                edDateAdd = dialog.findViewById(R.id.ed_date_khoanchi);
                spinnerLoaiChi = dialog.findViewById(R.id.spinner_khoanchi);

                removeErrorTextChange();
                edlDateAddDatePickerClickListener();
                initSpinnerGetAPILoaiChi(dataId_user);
                clickListenerButtonDialog();


            }
        });
    }

    private void initSpinnerGetAPILoaiChi(int dataId_user) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoaiChiAPI loaiChiAPI = retrofit.create(LoaiChiAPI.class);
        Call<ServerResponseSelectLoaiChi> call = loaiChiAPI.getSelectLoaiChi(dataId_user);
        call.enqueue(new Callback<ServerResponseSelectLoaiChi>() {
            @Override
            public void onResponse(Call<ServerResponseSelectLoaiChi> call, Response<ServerResponseSelectLoaiChi> response) {
                ServerResponseSelectLoaiChi serverResponseSelectLoaiChi = response.body();
                try {
                    List<LoaiChi> loaiChiList = new ArrayList<>(Arrays.asList(serverResponseSelectLoaiChi.getLoaichi()));

                    itemSpinnerAdapterLoaiChi.setLoaiChiList(loaiChiList);
                    spinnerLoaiChi.setAdapter(itemSpinnerAdapterLoaiChi);
                    spinnerLoaiChi.setSelection(0, true);
                    spinnerLoaiChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            LoaiChi loaiChi = loaiChiList.get(i);
                            id_loaichi = loaiChi.getId_loaichi();

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ServerResponseSelectLoaiChi> call, Throwable t) {

            }
        });
    }

    private void clickListenerButtonDialog() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateAddKhoanChi() == true) {
                    String name = edName.getText().toString();
                    String stringMoney = edMoney.getText().toString();
                    String note = edNote.getText().toString();
                    String add_date = edDateAdd.getText().toString();
                    try {
                        int money = Integer.parseInt(stringMoney);
                        insertDataKhoanChiAPI(dataId_user, id_loaichi, name, money, add_date, note);
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
                dialog.dismiss();
            }
        });
    }

    private void insertDataKhoanChiAPI(int dataId_user, int id_loaichi, String name, int money, String add_date, String note) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KhoanChiAPI khoanChiAPI = retrofit.create(KhoanChiAPI.class);
        Call<ServerResponseInsertKhoanChi> call = khoanChiAPI.insertKhoanChi(dataId_user, id_loaichi, name, money, add_date, note);
        call.enqueue(new Callback<ServerResponseInsertKhoanChi>() {
            @Override
            public void onResponse(Call<ServerResponseInsertKhoanChi> call, Response<ServerResponseInsertKhoanChi> response) {
                ServerResponseInsertKhoanChi serverResponseInsertKhoanChi = response.body();
                hideProgress();
                Toast.makeText(getContext(), serverResponseInsertKhoanChi.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
                getSelecteDatKhoanChiByIdUser(dataId_user);
            }

            @Override
            public void onFailure(Call<ServerResponseInsertKhoanChi> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
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

    private void removeErrorTextChange() {
        utilities.removeErrorText(edlName, edName);
        utilities.removeErrorText(edlMoney, edMoney);
        utilities.removeErrorText(edlNote, edNote);
    }

    private void initViewById(View view) {
        listView = view.findViewById(R.id.list_view);
        searchView = view.findViewById(R.id.search);
        fabAddKhoanChi = view.findViewById(R.id.fab_add);
        tvNotify = view.findViewById(R.id.tv_notify);
    }

    private Boolean validateAddKhoanChi() {
        Boolean success = true;
        Matcher matcherName = utilities.NSCPattern.matcher(edName.getText().toString());
        Matcher matcherMoney = utilities.NSCPattern.matcher(edMoney.getText().toString());
        if (id_loaichi == 0) {
            Toast.makeText(getContext(), utilities.KhoanchiSpinnerSelectRequire, Toast.LENGTH_LONG).show();
            success = false;
        }
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

    private void getSelecteDatKhoanChiByIdUser(int userId) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KhoanChiAPI khoanChiAPI = retrofit.create(KhoanChiAPI.class);
        Call<ServerResponseSelectedDataById> call = khoanChiAPI.selectKhoanchi(userId);
        call.enqueue(new Callback<ServerResponseSelectedDataById>() {
            @Override
            public void onResponse(Call<ServerResponseSelectedDataById> call, Response<ServerResponseSelectedDataById> response) {
                ServerResponseSelectedDataById serverResponseSelectedDataById = response.body();
                String message = serverResponseSelectedDataById.getMessage();
                try {
                    hideProgress();
                    listView.setVisibility(View.VISIBLE);
                    tvNotify.setVisibility(View.GONE);
                    khoanChiList = new ArrayList<>(Arrays.asList(serverResponseSelectedDataById.getKhoanchi()));
                    itemListViewAdapterKhoanChi.setKhoanChiList(khoanChiList);
                    listView.setAdapter(itemListViewAdapterKhoanChi);

                } catch (NullPointerException e) {
                    e.printStackTrace();
                    listView.setVisibility(View.GONE);
                    tvNotify.setVisibility(View.VISIBLE);
                    tvNotify.setText(message);
                    hideProgress();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseSelectedDataById> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
    }

}