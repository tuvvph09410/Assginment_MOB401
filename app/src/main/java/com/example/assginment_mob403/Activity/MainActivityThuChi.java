package com.example.assginment_mob403.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.assginment_mob403.Fragment.ChangePassword.Fragment_change_password;
import com.example.assginment_mob403.Fragment.Chi.Fragment_khoanchi;
import com.example.assginment_mob403.Fragment.Chi.Fragment_loaichi;
import com.example.assginment_mob403.Fragment.Home.Fragment_home;
import com.example.assginment_mob403.Fragment.InformationUser.Fragment_information_user;
import com.example.assginment_mob403.Fragment.Thu.Fragment_khoanthu;
import com.example.assginment_mob403.Fragment.Thu.Fragment_loaithu;
import com.example.assginment_mob403.Fragment.TietKiem.Fragment_tietkiem;
import com.example.assginment_mob403.InterfaceAPI.UserAPI;
import com.example.assginment_mob403.Model.User;
import com.example.assginment_mob403.R;
import com.example.assginment_mob403.ServerResponse.User_Response.ServerResponseGetUserById;
import com.example.assginment_mob403.URLServer.PathURLServer;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivityThuChi extends AppCompatActivity {
    private static final String urlImage = "https://imgs.vietnamnet.vn/Images/2017/02/24/20/20170224204534-quan-ly-tai-chinh.jpg";
    MaterialToolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    TextView tvNameHeaderNav;
    View header;
    private int dataId_user = 0;
    private String dataFirst_name = "";
    private String dataLast_name = "";
    private String dataEmail = "";
    private int dataPhone = 0;
    private String dataPassword = "";
    private String dataRegistration_date = "";

    private static final String TAG = MainActivityThuChi.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_thu_chi);

        getIntentExtra();

        getSelectedById(dataId_user);

        initViewByID();

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
                Bundle bundle = new Bundle();
                bundle.putInt("dataId_user", dataId_user);
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        init();
                        break;
                    case R.id.nav_loaithu:
                        Fragment_loaithu fragment_loaithu = new Fragment_loaithu();
                        fragment_loaithu.setArguments(bundle);
                        loadFragment(fragment_loaithu, "Loại thu");
                        break;

                    case R.id.nav_khoanthu:
                        Fragment_khoanthu fragment_khoanthu = new Fragment_khoanthu();
                        fragment_khoanthu.setArguments(bundle);
                        loadFragment(fragment_khoanthu, "Khoản thu");
                        break;
                    case R.id.nav_loaichi:
                        Fragment_loaichi fragment_loaichi = new Fragment_loaichi();
                        fragment_loaichi.setArguments(bundle);
                        loadFragment(fragment_loaichi, "Loại chi");
                        break;
                    case R.id.nav_khoanchi:
                        Fragment_khoanchi fragment_khoanchi = new Fragment_khoanchi();
                        fragment_khoanchi.setArguments(bundle);
                        loadFragment(fragment_khoanchi, "Khoản chi");
                        break;
                    case R.id.nav_changepassword:
                        Fragment_change_password fragment_change_password = new Fragment_change_password();
                        Bundle bundleChange = new Bundle();
                        bundleChange.putInt("dataId_user", dataId_user);
                        bundleChange.putString("dataEmail", dataEmail);
                        bundleChange.putString("dataPassword", dataPassword);
                        fragment_change_password.setArguments(bundleChange);
                        loadFragment(fragment_change_password, "Đổi mật khẩu");
                        break;
                    case R.id.nav_information_user:
                        getSelectedById(dataId_user);
                        Fragment_information_user fragment_information_user = new Fragment_information_user();
                        Bundle bundleIF = new Bundle();
                        bundleIF.putInt("dataId_user", dataId_user);
                        bundleIF.putString("dataFirst_name", dataFirst_name);
                        bundleIF.putString("dataLast_name", dataLast_name);
                        bundleIF.putString("dataEmail", dataEmail);
                        bundleIF.putInt("dataPhone", dataPhone);
                        bundleIF.putString("dataRegistration_date", dataRegistration_date);
                        fragment_information_user.setArguments(bundleIF);
                        loadFragment(fragment_information_user, "Thông tin tài khoản");
                        break;
                    case R.id.nav_tietkiem:
                        Fragment_tietkiem fragment_tietkiem = new Fragment_tietkiem();
                        loadFragment(fragment_tietkiem, "Tiết kiệm");
                        break;
                    case R.id.nav_logout:
                        Intent intent = new Intent(MainActivityThuChi.this, MainActivity.class);
                        startActivity(intent);
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
        Fragment_home fragment_home = new Fragment_home();
        Bundle bundleHome = new Bundle();
        bundleHome.putInt("dataId_user", dataId_user);
        fragment_home.setArguments(bundleHome);
        loadFragment(fragment_home, "Trang chủ");

    }

    private void getIntentExtra() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            dataId_user = bundle.getInt("dataId_user");

        }

    }

    private void initViewByID() {
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

    }

    public void loadFragment(Fragment fragment, String name) {
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
                        dataPassword = user.getPassword();
                        dataPhone = user.getPhone();
                        dataRegistration_date = user.getRegistration_date();
                    }
                }
            }

            @Override
            public void onFailure(Call<ServerResponseGetUserById> call, Throwable t) {
                Toast.makeText(getApplication(), t.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG, t.getMessage());
            }
        });
    }

}