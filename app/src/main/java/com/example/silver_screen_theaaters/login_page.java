package com.example.silver_screen_theaaters;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class login_page extends AppCompatActivity {

    Button l_google, l_facebook, login, l_forgotpassword, l_signup;
    EditText l_phone, l_password;
    DBlink DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        l_google = findViewById(R.id.regsiter_google);
        l_facebook = findViewById(R.id.regsiter_facebook);
        login = findViewById(R.id.login_bt);
        l_forgotpassword = findViewById(R.id.login_forgetPassword);
        l_signup = findViewById(R.id.login_newAccount);
        l_phone = findViewById(R.id.login_mail);
        l_password = findViewById(R.id.register_mail);
        DB = new DBlink(this);
        String theater_user = getString(R.string.login_route);

        SharedPreferences sf=getSharedPreferences("Verify", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit=sf.edit();
        ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Please Wait ...");
        dialog.setCancelable(true);

        l_forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot = new Intent(getApplicationContext(), Forgot_password.class);
                startActivity(forgot);
            }
        });

        l_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register_page.class);
                startActivity(intent);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                String mobile = l_phone.getText().toString().trim();
                String pass = l_password.getText().toString().trim();
                String paramater = "";
                int index = mobile.indexOf('@');

                if (index == -1){
                    paramater = "phone";
                }else{
                    paramater = "email";
                }

                if (mobile.equals("") || pass.equals(""))
                    Toast.makeText(getApplicationContext(),"Enter values",Toast.LENGTH_SHORT).show();
                else{

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    Map<String, String> params = new HashMap<String, String>();

                    params.put(paramater, mobile);
                    params.put("password", pass);

                    final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, theater_user, new JSONObject(params), new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    dialog.dismiss();

                                    Toast.makeText(getApplicationContext(), "Logged In!!", Toast.LENGTH_SHORT).show();
                                    edit.putBoolean("Register Bool", true);
                                    edit.apply();
                                    Intent intent = new Intent(getApplicationContext(), Home_page.class);
                                    startActivity(intent);


                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    dialog.dismiss();
                                    error.printStackTrace();
                                    Toast.makeText(getApplicationContext(),"Invalid Username or Password", Toast.LENGTH_SHORT).show();

                                }
                            });

                    requestQueue.add(jsonObjectRequest);

                }

            }
        });

    }
}