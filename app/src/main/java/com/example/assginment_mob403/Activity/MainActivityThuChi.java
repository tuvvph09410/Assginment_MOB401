package com.example.assginment_mob403.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assginment_mob403.Fragment.Home.Fragment_home;
import com.example.assginment_mob403.R;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivityThuChi extends AppCompatActivity {
    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView tvNameHeaderNav;
    private static final String urlImage = "https://imgs.vietnamnet.vn/Images/2017/02/24/20/20170224204534-quan-ly-tai-chinh.jpg";
    View header;
    private int dataId_user = 0;
    private String dataFirst_name = "";
    private String dataLast_name = "";
    private String dataEmail = "";
    private int dataPhone = 0;
    private String dataPassword = "";
    private String dataRegistration_date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thu_chi);

        initViewByID();

        getIntentExtra();

        init();

        toolbarClickListener();

        initNavigationView();

        initNavHeader();
    }

    private void initNavHeader() {
        header = navigationView.getHeaderView(0);
        initAsycnTankLoadImage();
        tvNameHeaderNav = header.findViewById(R.id.tv_name_navHeader);
        tvNameHeaderNav.setText(dataFirst_name + " " + dataLast_name);
    }

    private void initNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        loadFragment(new Fragment_home(), "Trang chủ");
                        break;

                }
                return true;
            }
        });
    }

    private void toolbarClickListener() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void init() {
        loadFragment(new Fragment_home(), "Trang chủ");

    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            dataId_user = bundle.getInt("dataId_user");
            dataFirst_name = bundle.getString("dataFirst_name");
            dataLast_name = bundle.getString("dataLast_name");
            dataEmail = bundle.getString("dataEmail");
            dataPassword = bundle.getString("dataPassword");
            dataPhone = bundle.getInt("dataPhone");
            dataRegistration_date = bundle.getString("dataRegistration_date");
        }

    }

    private void initViewByID() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

    }

    private void loadFragment(Fragment fragment, String name) {
        toolbar.setTitle(name);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container_main_activity_thuchi, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void initAsycnTankLoadImage() {
        new AsycnTankLoadImage().execute(urlImage);
    }

    private class AsycnTankLoadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... strings) {
            try {
                return BitmapFactory.decodeStream((InputStream) new URL(strings[0]).getContent());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if (bitmap != null) {
                Drawable drawable = new BitmapDrawable(getResources(), bitmap);
                header.setBackground(drawable);

            }
        }
    }
}