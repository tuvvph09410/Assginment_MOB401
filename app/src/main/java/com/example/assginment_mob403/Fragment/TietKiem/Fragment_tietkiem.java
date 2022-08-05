package com.example.assginment_mob403.Fragment.TietKiem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.R;


public class Fragment_tietkiem extends Fragment {
    TextView tv_total_money;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.fragment_tietkiem, container, false);

        initViewById(view);

        return view;

    }

    private void initViewById(View view) {
        tv_total_money = view.findViewById(R.id.tv_money_tietkiem);
        listView = view.findViewById(R.id.list_view);
    }
}