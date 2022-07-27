package com.example.assginment_mob403.Fragment.TietKiem;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.assginment_mob403.R;


public class Fragment_tietkiem extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tietkiem, container, false);

        initViewByID(view);

        return view;
    }

    private void initViewByID(View view) {
    }
}