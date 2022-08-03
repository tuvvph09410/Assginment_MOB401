package com.example.assginment_mob403.Fragment.Thu;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.Adapter.ItemListViewAdapterLoaiThu;
import com.example.assginment_mob403.InterfaceAPI.LoaiThuAPI;

import com.example.assginment_mob403.InterfaceListener.ItemClickListenerLoaiThu;
import com.example.assginment_mob403.Model.LoaiThu;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.LoaiThu_Response.ServerResponseDeleteLoaiThu;
import com.example.assginment_mob403.ServerResponse.LoaiThu_Response.ServerResponseInsertLoaiThu;
import com.example.assginment_mob403.ServerResponse.LoaiThu_Response.ServerResponseSelectLoaiThu;
import com.example.assginment_mob403.URLServer.PathURLServer;
import com.example.assginment_mob403.Utilities.Utilities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_loaithu extends Fragment {
    SearchView searchView;
    ListView listView;
    TextView tvNotify;
    FloatingActionButton floatAddLoaiThu;
    private int data_idUser;
    Dialog dialog;
    private Utilities utilities;
    TextInputLayout edlName;
    TextInputEditText edName;
    Button btnAdd, btnCancel;
    ProgressDialog progressDialog;
    ItemListViewAdapterLoaiThu itemListViewAdapterLoaiThu;
    List<LoaiThu> LoaiThuList, LoaiThuListSearch;
    private static final String TAG = Fragment_loaithu.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loaithu, container, false);

        initGetArguments();

        initViewByID(view);

        init();

        getSelectDataLoaiThuByIdUserAPI(data_idUser);

        clickListenerSearchView();

        fabAddClickListener();

        return view;
    }

    private void init() {
        utilities = new Utilities();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang tải ...");
        itemListViewAdapterLoaiThu = new ItemListViewAdapterLoaiThu(getContext());
        itemClickListView();
    }

    private void itemClickListView() {
        itemListViewAdapterLoaiThu.setOnItemDeleteClickListener(new ItemClickListenerLoaiThu() {
            @Override
            public void onItemClick(int position) {
                deleteDataById(position, data_idUser);
            }
        });
    }

    private void fabAddClickListener() {
        floatAddLoaiThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add_loaithu);
                dialog.show();

                edlName = dialog.findViewById(R.id.edl_name_loaithu);
                edName = dialog.findViewById(R.id.ed_name_loaithu);
                btnAdd = dialog.findViewById(R.id.btn_add);
                btnCancel = dialog.findViewById(R.id.btn_cancel);

                removeErrorTextChange();
                clickListenerButtonDialog();
            }
        });
    }

    private void clickListenerButtonDialog() {
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateLoaiThu() == true) {
                    String name_LoaiThu = edName.getText().toString();
                    insertDataLoaiThuAPI(data_idUser, name_LoaiThu);
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


    private void insertDataLoaiThuAPI(int data_idUser, String name_LoaiThu) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoaiThuAPI LoaiThuAPI = retrofit.create(LoaiThuAPI.class);
        Call<ServerResponseInsertLoaiThu> call = LoaiThuAPI.insertLoaiThu(data_idUser, name_LoaiThu);
        call.enqueue(new Callback<ServerResponseInsertLoaiThu>() {
            @Override
            public void onResponse(Call<ServerResponseInsertLoaiThu> call, Response<ServerResponseInsertLoaiThu> response) {
                ServerResponseInsertLoaiThu responseInsertLoaiThu = response.body();
                Toast.makeText(getContext(), responseInsertLoaiThu.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
                hideProgress();
                getSelectDataLoaiThuByIdUserAPI(data_idUser);
            }

            @Override
            public void onFailure(Call<ServerResponseInsertLoaiThu> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
    }

    private void getSelectDataLoaiThuByIdUserAPI(int data_idUser) {
        Log.e(TAG, String.valueOf(data_idUser));
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoaiThuAPI LoaiThuAPI = retrofit.create(LoaiThuAPI.class);
        Call<ServerResponseSelectLoaiThu> call = LoaiThuAPI.getSelectLoaiThu(data_idUser);
        call.enqueue(new Callback<ServerResponseSelectLoaiThu>() {
            @Override
            public void onResponse(Call<ServerResponseSelectLoaiThu> call, Response<ServerResponseSelectLoaiThu> response) {
                ServerResponseSelectLoaiThu serverResponseSelectLoaiThu = response.body();
                String message = serverResponseSelectLoaiThu.getMessage();
                try {
                    listView.setVisibility(View.VISIBLE);
                    tvNotify.setVisibility(View.GONE);
                    LoaiThuList = new ArrayList<>(Arrays.asList(serverResponseSelectLoaiThu.getLoaiThu()));
                    itemListViewAdapterLoaiThu.setList(LoaiThuList);
                    listView.setAdapter(itemListViewAdapterLoaiThu);
                    hideProgress();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    hideProgress();
                    listView.setVisibility(View.GONE);
                    tvNotify.setVisibility(View.VISIBLE);
                    tvNotify.setText(message);
                    hideProgress();
                }
            }

            @Override
            public void onFailure(Call<ServerResponseSelectLoaiThu> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
    }

    private void deleteDataById(int position, int data_idUser) {
        Log.e(TAG, String.valueOf(position));
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoaiThuAPI LoaiThuAPI = retrofit.create(LoaiThuAPI.class);
        Call<ServerResponseDeleteLoaiThu> call = LoaiThuAPI.deleteLoaiThu(position);
        call.enqueue(new Callback<ServerResponseDeleteLoaiThu>() {
            @Override
            public void onResponse(Call<ServerResponseDeleteLoaiThu> call, Response<ServerResponseDeleteLoaiThu> response) {
                ServerResponseDeleteLoaiThu serverResponseDeleteLoaiThu = response.body();
                Toast.makeText(getContext(), serverResponseDeleteLoaiThu.getMessage(), Toast.LENGTH_LONG).show();
                getSelectDataLoaiThuByIdUserAPI(data_idUser);
            }

            @Override
            public void onFailure(Call<ServerResponseDeleteLoaiThu> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void removeErrorTextChange() {
        utilities.removeErrorText(edlName, edName);
    }

    private void clickListenerSearchView() {
        searchView.setQueryHint("Tìm kiếm theo tên loại thu");
    }

    private void initGetArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            data_idUser = bundle.getInt("dataId_user");
        }
    }

    private void initViewByID(View view) {
        searchView = view.findViewById(R.id.search);
        listView = view.findViewById(R.id.list_view);
        floatAddLoaiThu = view.findViewById(R.id.fab_add);
        tvNotify = view.findViewById(R.id.tv_notify);
    }

    private Boolean validateLoaiThu() {
        Matcher matcherNameLoaiThu = utilities.NSCPattern.matcher(edName.getText().toString());
        Boolean success = true;
        if (edName.getText().toString().equalsIgnoreCase("")) {
            edlName.setError(utilities.LoaiThuRequire);
            success = false;
        } else {
            if (edName.getText().toString().length() <= 2 || edName.getText().toString().length() >=
                    20) {
                edlName.setError(utilities.LoaiThuLength);
                success = false;
            } else {
                if (!matcherNameLoaiThu.matches()) {
                    edlName.setError(utilities.NotSpecialCharacter);
                    success = false;
                }
            }
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
}