package com.example.assginment_mob403.Fragment.Chi;

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

import com.example.assginment_mob403.Adapter.ItemListViewAdapterLoaiChi;
import com.example.assginment_mob403.InterfaceAPI.LoaiChiAPI;
import com.example.assginment_mob403.InterfaceListener.ItemClickListenerLoaiChi;
import com.example.assginment_mob403.Model.LoaiChi;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.LoaiChi_Response.ServerResponseDeleteLoaiChi;
import com.example.assginment_mob403.ServerResponse.LoaiChi_Response.ServerResponseInsertLoaiChi;
import com.example.assginment_mob403.ServerResponse.LoaiChi_Response.ServerResponseSelectLoaiChi;
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


public class Fragment_loaichi extends Fragment {
    SearchView searchView;
    ListView listView;
    TextView tvNotify;
    FloatingActionButton floatAddLoaiChi;
    private int data_idUser;
    Dialog dialog;
    private Utilities utilities;
    TextInputLayout edlName;
    TextInputEditText edName;
    Button btnAdd, btnCancel;
    ProgressDialog progressDialog;
    ItemListViewAdapterLoaiChi itemListViewAdapterLoaiChi;
    List<LoaiChi> loaiChiList, loaiChiListSearch;
    private static final String TAG = Fragment_loaichi.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loaichi, container, false);

        initGetArguments();

        initViewByID(view);

        init();

        getSelectDataLoaiChiByIdUserAPI(data_idUser);

        onChangeTextSearchView();

        fabAddClickListener();

        return view;
    }

    private void init() {
        utilities = new Utilities();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Đang tải ...");
        itemListViewAdapterLoaiChi = new ItemListViewAdapterLoaiChi(getContext());
        itemClickListView();
    }

    private void itemClickListView() {
        itemListViewAdapterLoaiChi.setOnItemDeleteClickListener(new ItemClickListenerLoaiChi() {
            @Override
            public void onItemClick(int position) {
                deleteDataById(position, data_idUser);
            }
        });
    }

    private void fabAddClickListener() {
        floatAddLoaiChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_add_loaichi);
                dialog.show();

                edlName = dialog.findViewById(R.id.edl_name_loaichi);
                edName = dialog.findViewById(R.id.ed_name_loaichi);
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
                if (validateLoaiChi() == true) {
                    String name_loaichi = edName.getText().toString();
                    insertDataLoaiChiAPI(data_idUser, name_loaichi);
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


    private void insertDataLoaiChiAPI(int data_idUser, String name_loaichi) {
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoaiChiAPI loaiChiAPI = retrofit.create(LoaiChiAPI.class);
        Call<ServerResponseInsertLoaiChi> call = loaiChiAPI.insertLoaiChi(data_idUser, name_loaichi);
        call.enqueue(new Callback<ServerResponseInsertLoaiChi>() {
            @Override
            public void onResponse(Call<ServerResponseInsertLoaiChi> call, Response<ServerResponseInsertLoaiChi> response) {
                ServerResponseInsertLoaiChi responseInsertLoaiChi = response.body();
                Toast.makeText(getContext(), responseInsertLoaiChi.getMessage(), Toast.LENGTH_LONG).show();
                dialog.dismiss();
                hideProgress();
                getSelectDataLoaiChiByIdUserAPI(data_idUser);
            }

            @Override
            public void onFailure(Call<ServerResponseInsertLoaiChi> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                hideProgress();
            }
        });
    }

    private void getSelectDataLoaiChiByIdUserAPI(int data_idUser) {
        Log.e(TAG, String.valueOf(data_idUser));
        showProgress();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        LoaiChiAPI loaiChiAPI = retrofit.create(LoaiChiAPI.class);
        Call<ServerResponseSelectLoaiChi> call = loaiChiAPI.getSelectLoaiChi(data_idUser);
        call.enqueue(new Callback<ServerResponseSelectLoaiChi>() {
            @Override
            public void onResponse(Call<ServerResponseSelectLoaiChi> call, Response<ServerResponseSelectLoaiChi> response) {
                ServerResponseSelectLoaiChi serverResponseSelectLoaiChi = response.body();
                String message = serverResponseSelectLoaiChi.getMessage();
                try {
                    listView.setVisibility(View.VISIBLE);
                    tvNotify.setVisibility(View.GONE);
                    loaiChiList = new ArrayList<>(Arrays.asList(serverResponseSelectLoaiChi.getLoaichi()));
                    itemListViewAdapterLoaiChi.setList(loaiChiList);
                    listView.setAdapter(itemListViewAdapterLoaiChi);
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
            public void onFailure(Call<ServerResponseSelectLoaiChi> call, Throwable t) {
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
        LoaiChiAPI loaiChiAPI = retrofit.create(LoaiChiAPI.class);
        Call<ServerResponseDeleteLoaiChi> call = loaiChiAPI.deleteLoaiChi(position);
        call.enqueue(new Callback<ServerResponseDeleteLoaiChi>() {
            @Override
            public void onResponse(Call<ServerResponseDeleteLoaiChi> call, Response<ServerResponseDeleteLoaiChi> response) {
                ServerResponseDeleteLoaiChi serverResponseDeleteLoaiChi = response.body();
                Toast.makeText(getContext(), serverResponseDeleteLoaiChi.getMessage(), Toast.LENGTH_LONG).show();
                getSelectDataLoaiChiByIdUserAPI(data_idUser);
            }

            @Override
            public void onFailure(Call<ServerResponseDeleteLoaiChi> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void removeErrorTextChange() {
        utilities.removeErrorText(edlName, edName);
    }

    private void onChangeTextSearchView() {
        searchView.setQueryHint("Tìm kiếm theo tên loại chi");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               loaiChiListSearch = new ArrayList<>();
                for (int i = 0; i < loaiChiList.size(); i++) {
                    if (loaiChiList.get(i).getName_loaichi().startsWith(searchView.getQuery().toString())) {
                        loaiChiListSearch.add(loaiChiList.get(i));
                    }
                    itemListViewAdapterLoaiChi.setList(loaiChiListSearch);
                }
                return false;
            }
        });
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
        floatAddLoaiChi = view.findViewById(R.id.fab_add);
        tvNotify = view.findViewById(R.id.tv_notify);
    }

    private Boolean validateLoaiChi() {
        Matcher matcherNameLoaiChi = utilities.NSCPattern.matcher(edName.getText().toString());
        Boolean success = true;
        if (edName.getText().toString().equalsIgnoreCase("")) {
            edlName.setError(utilities.LoaiChiRequire);
            success = false;
        } else {
            if (edName.getText().toString().length() <= 2 || edName.getText().toString().length() >=
                    20) {
                edlName.setError(utilities.LoaiChiLength);
                success = false;
            } else {
                if (!matcherNameLoaiChi.matches()) {
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