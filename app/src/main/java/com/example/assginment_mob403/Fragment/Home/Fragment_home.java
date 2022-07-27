package com.example.assginment_mob403.Fragment.Home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assginment_mob403.Fragment.Chi.Fragment_khoanchi;
import com.example.assginment_mob403.Fragment.InformationUser.Fragment_information_user;
import com.example.assginment_mob403.Fragment.Thu.Fragment_khoanthu;
import com.example.assginment_mob403.Fragment.TietKiem.Fragment_tietkiem;
import com.example.assginment_mob403.R;
import com.github.mikephil.charting.charts.BarChart;

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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initGetArguments();

        initViewById(view);

        initClickListener();
        return view;
    }

    private void initGetArguments() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            dataId_user = bundle.getInt("dataId_user");
            dataFirst_name = bundle.getString("dataFirst_name");
            dataLast_name = bundle.getString("dataLast_name");
            dataEmail = bundle.getString("dataEmail");
            dataPhone = bundle.getInt("dataPhone");
            dataRegistration_date = bundle.getString("dataRegistration_date");
        }
    }

    private void initClickListener() {
        clKhoanThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_khoanthu());
            }
        });
        clKhoanChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_khoanchi());
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
                loadFragment(fragment_information_user);
            }
        });
        clTietKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadFragment(new Fragment_tietkiem());
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

    private void loadFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main_activity_thuchi, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}