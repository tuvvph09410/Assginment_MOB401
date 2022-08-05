package com.example.assginment_mob403.Fragment.Thu;

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

import com.example.assginment_mob403.Adapter.ItemListViewAdapterKhoanThu;
import com.example.assginment_mob403.Adapter.ItemSpinnerAdapterLoaiThu;
import com.example.assginment_mob403.InterfaceAPI.KhoanThuAPI;
import com.example.assginment_mob403.InterfaceAPI.LoaiThuAPI;
import com.example.assginment_mob403.InterfaceListener.ItemClickListenerKhoanThu;
import com.example.assginment_mob403.Model.KhoanThu;
import com.example.assginment_mob403.Model.LoaiThu;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.KhoanThu_Response.ServerResponseDeleteKhoanThu;
import com.example.assginment_mob403.ServerResponse.KhoanThu_Response.ServerResponseInsertKhoanThu;
import com.example.assginment_mob403.ServerResponse.KhoanThu_Response.ServerResponseSelectedDataById;
import com.example.assginment_mob403.ServerResponse.LoaiThu_Response.ServerResponseSelectLoaiThu;
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


public class Fragment_khoanthu extends Fragment {
    ListView listView;
    SearchView searchView;
    FloatingActionButton fabAddKhoanThu;
    Button btnAdd, btnCancel;
    TextInputLayout edlName, edlMoney, edlNote, edlDateAdd;
    TextInputEditText edName, edMoney, edNote, edDateAdd;
    Spinner spinnerLoaiThu;
    private Utilities utilities;
    private int dataId_user, mDate, mMonth, mYear;
    Dialog dialog;
    TextView tvNotify;
    ItemSpinnerAdapterLoaiThu itemSpinnerAdapterLoaiThu;
    private int id_LoaiThu = 0;
    private static final String TAG = Fragment_loaithu.class.getSimpleName();
    ProgressDialog progressDialog;
    ItemListViewAdapterKhoanThu itemListViewAdapterKhoanThu;
    private List<KhoanThu> KhoanThuList, khoanThuListSearh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_khoanthu, container, false);

        initGetArguments();

        initViewById(view);

        init();

        getSelecteDatKhoanThuByIdUser(dataId_user);

        fabAddClickListener();

        onChangeTextSearchView();

        return view;
    }

    private void onChangeTextSearchView() {
        searchView.setQueryHint("Tìm kiếm theo tên khoản thu");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                khoanThuListSearh = new ArrayList<>();
                for (int i = 0; i < KhoanThuList.size(); i++) {
                    if (KhoanThuList.get(i).getName_khoanthu().startsWith(searchView.getQuery().toString())) {
                        khoanThuListSearh.add(KhoanThuList.get(i));
                    }
                    itemListViewAdapterKhoanThu.setKhoanThuList(khoanThuListSearh);
                }
                return false;
            }
        });
    }

    private void initGetArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dataId_user = bundle.getInt("dataId_user");
        }

    }

    private void init() {
        utilities = new Utilities();
        itemSpinnerAdapterLoaiThu = new ItemSpinnerAdapterLoaiThu(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang load ...");
        itemListViewAdapterKhoanThu = new ItemListViewAdapterKhoanThu(getContext());
        itemClickListView();
    }

    private void itemClickListView() {
        itemListViewAdapterKhoanThu.setOnItemDeleteClickListener(new ItemClickListenerKhoanThu() {
            @Override
            public void onItemClick(int position) {
                deleteLoaiThuAPI(position, dataId_user);
            }
        });
    }

    private void deleteLoaiThuAPI(int id_KhoanThu, int dataId_user) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KhoanThuAPI KhoanThuAPI = retrofit.create(KhoanThuAPI.class);
        Call<ServerResponseDeleteKhoanThu> call = KhoanThuAPI.deleteKhoanThu(id_KhoanThu);
        call.enqueue(new Callback<ServerResponseDeleteKhoanThu>() {
            @Override
            public void onResponse(Call<ServerResponseDeleteKhoanThu> call, Response<ServerResponseDeleteKhoanThu> response) {
                ServerResponseDeleteKhoanThu serverResponseDeleteKhoanThu = response.body();
                Toast.makeText(getContext(), serverResponseDeleteKhoanThu.getMessage(), Toast.LENGTH_LONG).show();
                getSelecteDatKhoanThuByIdUser(dataId_user);
                hideProgress();
            }

            @Override
            public void onFailure(Call<ServerResponseDeleteKhoanThu> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
    }

    private void fabAddClickListener() {
        fabAddKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_khoanthu);
                dialog.show();

                btnAdd = dialog.findViewById(R.id.btn_add);
                btnCancel = dialog.findViewById(R.id.btn_cancel);
                edlName = dialog.findViewById(R.id.edl_name_khoanthu);
                edlMoney = dialog.findViewById(R.id.edl_money_khoanthu);
                edlNote = dialog.findViewById(R.id.edl_note_khoanthu);
                edlDateAdd = dialog.findViewById(R.id.edl_date_khoanthu);
                edName = dialog.findViewById(R.id.ed_name_khoanthu);
                edMoney = dialog.findViewById(R.id.ed_money_khoanthu);
                edNote = dialog.findViewById(R.id.ed_note_khoanthu);
                edDateAdd = dialog.findViewById(R.id.ed_date_khoanthu);
                spinnerLoaiThu = dialog.findViewById(R.id.spinner_khoanthu);

                removeErrorTextChange();
                edlDateAddDatePickerClickListener();
                initSpinnerGetAPILoaiThu(dataId_user);
                clickListenerButtonDialog();


            }
        });
    }

    private void initSpinnerGetAPILoaiThu(int dataId_user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoaiThuAPI LoaiThuAPI = retrofit.create(LoaiThuAPI.class);
        Call<ServerResponseSelectLoaiThu> call = LoaiThuAPI.getSelectLoaiThu(dataId_user);
        call.enqueue(new Callback<ServerResponseSelectLoaiThu>() {
            @Override
            public void onResponse(Call<ServerResponseSelectLoaiThu> call, Response<ServerResponseSelectLoaiThu> response) {
                ServerResponseSelectLoaiThu serverResponseSelectLoaiThu = response.body();
                try {
                    List<LoaiThu> LoaiThuList = new ArrayList<>(Arrays.asList(serverResponseSelectLoaiThu.getLoaiThu()));
                    itemSpinnerAdapterLoaiThu.setLoaiThuList(LoaiThuList);
                    spinnerLoaiThu.setAdapter(itemSpinnerAdapterLoaiThu);
                    spinnerLoaiThu.setSelection(0, true);
                    spinnerLoaiThu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            LoaiThu LoaiThu = LoaiThuList.get(i);
                            id_LoaiThu = LoaiThu.getId_loaithu();

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
            public void onFailure(Call<ServerResponseSelectLoaiThu> call, Throwable t) {

            }
        });
    }

    private void clickListenerButtonDialog() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateAddKhoanThu() == true) {
                    String name = edName.getText().toString();
                    String stringMoney = edMoney.getText().toString();
                    String note = edNote.getText().toString();
                    String add_date = edDateAdd.getText().toString();
                    try {
                        int money = Integer.parseInt(stringMoney);
                        insertDataKhoanThuAPI(dataId_user, id_LoaiThu, name, money, add_date, note);
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
                dialog.dismiss();
            }
        });
    }

    private void insertDataKhoanThuAPI(int dataId_user, int id_LoaiThu, String name, int money, String add_date, String note) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KhoanThuAPI KhoanThuAPI = retrofit.create(KhoanThuAPI.class);
        Call<ServerResponseInsertKhoanThu> call = KhoanThuAPI.insertKhoanThu(dataId_user, id_LoaiThu, name, money, add_date, note);
        call.enqueue(new Callback<ServerResponseInsertKhoanThu>() {
            @Override
            public void onResponse(Call<ServerResponseInsertKhoanThu> call, Response<ServerResponseInsertKhoanThu> response) {
                ServerResponseInsertKhoanThu serverResponseInsertKhoanThu = response.body();
                hideProgress();
                Toast.makeText(getContext(), serverResponseInsertKhoanThu.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
                getSelecteDatKhoanThuByIdUser(dataId_user);
            }

            @Override
            public void onFailure(Call<ServerResponseInsertKhoanThu> call, Throwable t) {
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
        fabAddKhoanThu = view.findViewById(R.id.fab_add);
        tvNotify = view.findViewById(R.id.tv_notify);
    }

    private Boolean validateAddKhoanThu() {
        Boolean success = true;
        Matcher matcherName = utilities.NSCPattern.matcher(edName.getText().toString());
        Matcher matcherMoney = utilities.NSCPattern.matcher(edMoney.getText().toString());
        if (id_LoaiThu == 0) {
            Toast.makeText(getContext(), utilities.KhoanThuSpinnerSelectRequire, Toast.LENGTH_LONG).show();
            success = false;
        }
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

    private void getSelecteDatKhoanThuByIdUser(int userId) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        KhoanThuAPI KhoanThuAPI = retrofit.create(KhoanThuAPI.class);
        Call<ServerResponseSelectedDataById> call = KhoanThuAPI.selectKhoanThu(userId);
        call.enqueue(new Callback<ServerResponseSelectedDataById>() {
            @Override
            public void onResponse(Call<ServerResponseSelectedDataById> call, Response<ServerResponseSelectedDataById> response) {
                ServerResponseSelectedDataById serverResponseSelectedDataById = response.body();
                String message = serverResponseSelectedDataById.getMessage();
                try {
                    hideProgress();
                    listView.setVisibility(View.VISIBLE);
                    tvNotify.setVisibility(View.GONE);
                    KhoanThuList = new ArrayList<>(Arrays.asList(serverResponseSelectedDataById.getKhoanThu()));
                    itemListViewAdapterKhoanThu.setKhoanThuList(KhoanThuList);
                    listView.setAdapter(itemListViewAdapterKhoanThu);

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