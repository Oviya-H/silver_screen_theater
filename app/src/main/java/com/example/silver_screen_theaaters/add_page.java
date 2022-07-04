package com.example.silver_screen_theaaters;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class add_page extends AppCompatActivity {

    Button type_cost, timeslot, submit, theater_add;
    EditText type, cost, tickets, movie;
    TextView time;
    ListView type_cost_list, time_slot_list;
    DBlink_movie DB;
    String seatType_id, seatAvailable_id;

    public String TYPE = "", COST = "", TIME = "", TICKETS = "", MOVIE = "";
    int tHour, tMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_page);

        type_cost = findViewById(R.id.home_l1_btn);
        timeslot = findViewById(R.id.home_l2_btn);
        submit = findViewById(R.id.home_submit);

        type = findViewById(R.id.home_type);
        cost = findViewById(R.id.home_type_cost);
        time = findViewById(R.id.home_timeslot);
        tickets = findViewById(R.id.home_ticket);
        movie = findViewById(R.id.movie_name);
        theater_add = findViewById(R.id.home_l1_btn2);

        type_cost_list = findViewById(R.id.home_list_type);
        time_slot_list = findViewById(R.id.home_list_timeslot);
        String seat_url = getString(R.string.seattype);
        String show_url = getString(R.string.shows);
        String seat_available_url = getString(R.string.seatavailable);


        DB = new DBlink_movie(this);
        SharedPreferences sf=getSharedPreferences("TheaterName", Context.MODE_PRIVATE);
        String TheaterID = sf.getString("theater_id", "");

        List<String> TypeCostList = new ArrayList<>();
        List<String> TimeSlotList = new ArrayList<>();
        ProgressDialog dialog = new ProgressDialog(this);

        dialog.setMessage("Please Wait ...");
        dialog.setCancelable(true);


        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, TypeCostList);
        type_cost_list.setAdapter(adapter);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, TimeSlotList);
        time_slot_list.setAdapter(adapter2);

        theater_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                Map<String, String> params = new HashMap<String, String>();
                params.put("seatType", seatType_id);
                params.put("total", tickets.getText().toString());

                final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                        (Request.Method.POST, seat_available_url, new JSONObject(params), new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    seatAvailable_id = response.getString("id");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                                dialog.dismiss();
                                Toast.makeText(getApplicationContext(), "Insertion Failed  " + error.toString(), Toast.LENGTH_SHORT).show();

                            }
                        });

                requestQueue.add(jsonObjectRequest);
            }
        });


        type_cost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String type_text = type.getText().toString();
                String cost_text = cost.getText().toString();

                if (type_text.trim().equals("") || cost_text.trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Fill all values", Toast.LENGTH_SHORT).show();
                }else{
                    TYPE += type_text + ", ";
                    COST += cost_text + ", ";

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("name", type_text);
                    params.put("rate", cost_text);
                    params.put("theatre", TheaterID);

                    final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.POST, seat_url, new JSONObject(params), new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        seatType_id = response.getString("id");
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    type.setText("");
                                    cost.setText("");
                                    TypeCostList.add(type_text+", "+cost_text);
                                    adapter.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();

                                }
                            });

                    requestQueue.add(jsonObjectRequest);
                }
            }
        });

        timeslot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
                String time_text = time.getText().toString();

                if (time_text.trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Select Time", Toast.LENGTH_SHORT).show();

                }else{

                    TIME += time_text + ", ";

                    RequestQueue requestQueue2 = Volley.newRequestQueue(getApplicationContext());

                    Map<String, String> params2 = new HashMap<String, String>();
                    params2.put("movieName", movie.getText().toString());
                    params2.put("timing", time_text);
                    params2.put("screenNo", "2");
                    params2.put("seatsAvailable", seatAvailable_id);


                    final JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                            (Request.Method.POST, show_url, new JSONObject(params2), new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {

                                    time.setText("");
                                    TimeSlotList.add(time_text);
                                    adapter2.notifyDataSetChanged();
                                    dialog.dismiss();
                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    error.printStackTrace();
                                    dialog.dismiss();
                                    Toast.makeText(getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();

                                }
                            });

                    requestQueue2.add(jsonObjectRequest2);


                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TICKETS  = tickets.getText().toString();
                MOVIE = movie.getText().toString();
                if (TICKETS.trim().equals("")|| TYPE.trim().equals("") || COST.trim().equals("") || TIME.trim().equals("")){
                    Toast.makeText(getApplicationContext(), "Enter all the values", Toast.LENGTH_SHORT).show();
                }else{
                    Boolean insert = DB.insert_value(MOVIE, TYPE, COST, TIME, TICKETS);

                    if (insert){
                        Intent intent = new Intent(getApplicationContext(), Home_page.class);
                        startActivity(intent);
                    }else {
                        Toast.makeText(getApplicationContext(), "Insertion failed!! Try Again", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                tHour = i;
                tMinute = i1;
                time.setText(String.format(Locale.getDefault(),"%02d:%02d",tHour,tMinute));

            }
        };


        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener,tHour,tMinute, true);

        timePickerDialog.setTitle("Selete Time");
        timePickerDialog.show();
    }
}