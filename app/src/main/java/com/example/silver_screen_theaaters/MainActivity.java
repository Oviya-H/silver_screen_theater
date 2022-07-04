package com.example.silver_screen_theaaters;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sf=getSharedPreferences("Verify", Context.MODE_PRIVATE);

        boolean verify = sf.getBoolean("Register Bool", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(verify){
                    Intent intent = new Intent(MainActivity.this, Home_page.class);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(MainActivity.this, login_page.class);
                    startActivity(intent);
                }

                finish();
            }
        }, 2*1000);

    }
}