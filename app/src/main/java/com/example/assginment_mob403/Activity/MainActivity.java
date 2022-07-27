package com.example.assginment_mob403.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.assginment_mob403.Fragment.User.Fragment_login;
import com.example.assginment_mob403.R;

public class MainActivity extends AppCompatActivity {
    Fragment_login fragment_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment_login = new Fragment_login();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_view_tag, fragment_login);
        fragmentTransaction.commit();
    }
}