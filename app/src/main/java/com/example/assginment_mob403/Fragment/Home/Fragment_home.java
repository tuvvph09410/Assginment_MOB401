package com.example.assginment_mob403.Fragment.Home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.Activity.MainActivityThuChi;
import com.example.assginment_mob403.Fragment.Chi.Fragment_khoanchi;
import com.example.assginment_mob403.Fragment.InformationUser.Fragment_information_user;
import com.example.assginment_mob403.Fragment.Thu.Fragment_khoanthu;
import com.example.assginment_mob403.Fragment.TietKiem.Fragment_tietkiem;
import com.example.assginment_mob403.InterfaceAPI.UserAPI;
import com.example.assginment_mob403.Model.User;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.User_Response.ServerResponseGetUserById;
import com.example.assginment_mob403.URLServer.PathURLServer;
import com.github.mikephil.charting.charts.BarChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Fragment_home extends Fragment {
    ConstraintLayout clKhoanThu, clKhoanChi, clTietKiem, clTaiKhoan;
    BarChart barChart;
    Button btnReset;
    FrameLayout fl_bar_chart;
    private int dataId_user = 0;
    private String dataFirst_name = "";
    private String dataLast_name = "";
    private String dataEmail = "";
    private int dataPhone = 0;
    private String dataRegistration_date = "";
    private static final String TAG = Fragment_home.class.getSimpleName();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initGetArguments();

        getSelectedById(dataId_user);

        initViewById(view);

        initClickListener();

        return view;
    }

    private void initGetArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dataId_user = bundle.getInt("dataId_user");
        }
    }

    private void initClickListener() {
        clKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivityThuChi) getContext()).loadFragment(new Fragment_khoanthu(), "Khoản thu");
            }
        });
        clKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                Fragment_khoanchi fragment_khoanchi=new Fragment_khoanchi();
                bundle.putInt("dataId_user",dataId_user);
                fragment_khoanchi.setArguments(bundle);
                ((MainActivityThuChi) getContext()).loadFragment(fragment_khoanchi, "Khoản chi");
            }
        });
        clTaiKhoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment_information_user fragment_information_user = new Fragment_information_user();
                Bundle bundleIF = new Bundle();
                bundleIF.putInt("dataId_user", dataId_user);
                bundleIF.putString("dataFirst_name", dataFirst_name);
                bundleIF.putString("dataLast_name", dataLast_name);
                bundleIF.putString("dataEmail", dataEmail);
                bundleIF.putInt("dataPhone", dataPhone);
                bundleIF.putString("dataRegistration_date", dataRegistration_date);
                fragment_information_user.setArguments(bundleIF);
                ((MainActivityThuChi) getContext()).loadFragment(fragment_information_user, "Thông tin tài khoản");
            }
        });
        clTietKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivityThuChi) getContext()).loadFragment(new Fragment_tietkiem(), "Tiết kiệm");
            }
        });
    }

    private void initViewById(View view) {
        clKhoanThu = view.findViewById(R.id.l_khoanthu);
        clKhoanChi = view.findViewById(R.id.l_khoanchi);
        clTietKiem = view.findViewById(R.id.l_tietkiem);
        clTaiKhoan = view.findViewById(R.id.l_account);
        barChart = view.findViewById(R.id.barChart);
        btnReset = view.findViewById(R.id.btn_reset);
        fl_bar_chart = view.findViewById(R.id.l_bar_chart);
    }

//    private void loadFragment(Fragment fragment, String name) {
//        toolbar.setTitle(name);
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container_main_activity_thuchi, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//    }

    private void getSelectedById(int id_user) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(PathURLServer.getBaseURL())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<ServerResponseGetUserById> call = userAPI.getUserById(id_user);
        call.enqueue(new Callback<ServerResponseGetUserById>() {
            @Override
            public void onResponse(Call<ServerResponseGetUserById> call, Response<ServerResponseGetUserById> response) {
                ServerResponseGetUserById serverResponseGetUserById = response.body();
                List<User> userList = new ArrayList<>(Arrays.asList(serverResponseGetUserById.getUser()));
                if (userList.size() != 0) {
                    for (int i = 0; i < userList.size(); i++) {
                        User user = userList.get(i);
                        dataFirst_name = user.getFirst_name();
                        dataLast_name = user.getLast_name();
                        dataEmail = user.getEmail();
                        dataPhone = user.getPhone();
                        dataRegistration_date = user.getRegistration_date();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponseGetUserById> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }
}