package com.example.assginment_mob403.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.assginment_mob403.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashScreen extends AppCompatActivity {
    ImageView ivSplashScreen;
    private String urlImage = "https://posapp.vn/wp-content/uploads/2021/06/doanh-thu-la-gi-6.jpg";
    ProgressDialog progressDialog;
    private final static int SPLASH_SCREEN = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        initViewByID();
        initProgressDialog();
        initAsycnTankLoadImage();
    }

    private void initAsycnTankLoadImage() {
        new AsycnTankLoadImage().execute(urlImage);
    }

    private class AsycnTankLoadImage extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.setMessage("Đang tải ...");
            progressDialog.setCancelable(false);
            showDialog();
        }

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
            hideDialog();
            if (bitmap != null) {
                ivSplashScreen.setImageBitmap(bitmap);
                postDelayed();
            } else {
                ivSplashScreen.setImageResource(R.drawable.ic_baseline_report_gmailerrorred_24);
            }
        }
    }

    private void postDelayed() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);

    }

    private void initViewByID() {
        ivSplashScreen = findViewById(R.id.image_splashscreen);
    }

    private void showDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}