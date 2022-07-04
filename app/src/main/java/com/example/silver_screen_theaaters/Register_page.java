package com.example.silver_screen_theaaters;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.print.PageRange;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.Map;
import java.util.regex.Pattern;

public class Register_page extends AppCompatActivity {

    Button reg_bt, reg_phoneVerify;
    EditText reg_name, reg_phone, reg_mail, reg_password, reg_re_password, theater_name, address1, address2;
    AutoCompleteTextView reg_city;
    DBlink DB;
    TextView phone_error, mail_error, pass_error;


    String[] cities = {"Ariyalur", "Chennai", "Coimbatore", "Cuddalore", "Dharmapuri", "Dindigul", "Erode", "Kanchipuram",
            "Kanyakumari", "Karur", "Madurai", "Nagapattinam", "Nilgiris", "Namakkal", "Perambalur", "Pudukkottai",
            "Ramanathapuram", "Salem", "Sivaganga", "Tirupur", "Tiruchirappalli", "Theni", "Tirunelveli", "Thanjavur",
            "Thoothukudi", "Tiruvallur", "Tiruvarur", "Tiruvannamalai", "Vellore", "Viluppuram", "Virudhunagar"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        reg_bt = findViewById(R.id.register_bt);
        reg_phoneVerify = findViewById(R.id.register_phoneVerify);
        reg_name = findViewById(R.id.register_name);
        reg_phone = findViewById(R.id.register_phone);
        reg_mail = findViewById(R.id.register_mail);
        reg_city = findViewById(R.id.register_city);
        reg_password = findViewById(R.id.register_password);
        reg_re_password = findViewById(R.id.re_register_password);
        theater_name = findViewById(R.id.register_theater_name);
        phone_error = findViewById(R.id.reg_phone_error);
        mail_error = findViewById(R.id.reg_mail_error);
        pass_error = findViewById(R.id.reg_pass_error);
        address1 = findViewById(R.id.register_address_1);
        address2 = findViewById(R.id.register_address_2);
        String theater_user = getString(R.string.theater);

        DB = new DBlink(this);
        ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Please Wait ...");
        dialog.setCancelable(true);

        reg_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 10){
                    phone_error.setTextColor(Color.GREEN);
                    phone_error.setText("Perfect !!");
                }else{
                    phone_error.setTextColor(Color.RED);
                    phone_error.setText("Inappropriate");
                }
            }
        });

        reg_mail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (isValid(editable.toString())){
                    mail_error.setTextColor(Color.GREEN);
                    mail_error.setText("Perfect !!");
                }
                else{
                    mail_error.setTextColor(Color.RED);
                    mail_error.setText("Inappropriate");
                }
            }
        });

        reg_re_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equals(reg_password.getText().toString())){
                    pass_error.setTextColor(Color.GREEN);
                    pass_error.setText("Perfect !!");
                }else{
                    pass_error.setTextColor(Color.RED);
                    pass_error.setText("Password does not match");
                }
            }
        });

        pass_error.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_selectable_list_item, cities);
        reg_city.setAdapter(adapter);
        reg_city.setThreshold(1);


        reg_city.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                reg_city.showDropDown();
                return false;
            }
        });

        reg_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                String username = reg_name.getText().toString();
                String phone = reg_phone.getText().toString();
                String email = reg_mail.getText().toString();
                String city = reg_city.getText().toString();
                String password = reg_password.getText().toString();
                String repass = reg_re_password.getText().toString();
                String theater = theater_name.getText().toString();
                String address_line_1 = address1.getText().toString();
                String address_line_2 = address2.getText().toString();

                if (address_line_1.trim().equals("") ||address_line_2.trim().equals("") ||theater.trim().equals("") || username.trim().equals("") || phone.trim().equals("") || email.trim().equals("") || city.trim().equals("") || password.trim().equals("") || repass.trim().equals(""))
                    Toast.makeText(getApplicationContext(),"fill all values",Toast.LENGTH_SHORT).show();

                else{
                    SharedPreferences sf = getSharedPreferences("TheaterName", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = sf.edit();
                    if (password.equals(repass)){
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                        Map<String, String> params = new HashMap<String, String>();
                        params.put("name", username);
                        params.put("phone", phone);
                        params.put("email", email);
                        params.put("addressLine1",address_line_1);
                        params.put("addressLine2",address_line_2);
                        params.put("city", city);
                        params.put("password", password);


                        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.POST, theater_user, new JSONObject(params), new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {
                                        try {
                                            String ID = response.getString("id");
                                            edit.putString("name", theater);
                                            edit.putString("theater_id", ID);
                                            edit.apply();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        dialog.dismiss();
                                        Toast.makeText(getApplicationContext(),"Registered Succesfully!",Toast.LENGTH_SHORT).show();
                                        Intent home_intent = new Intent(getApplicationContext(),login_page.class);
                                        startActivity(home_intent);

                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                        requestQueue.add(jsonObjectRequest);

                    }else{
                        Toast.makeText(getApplicationContext(),"Password Not match",Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });



        reg_phoneVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneverify = new Intent(getApplicationContext(), Phone_verifiation.class);
                startActivity(phoneverify);
            }
        });

    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}