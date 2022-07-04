package com.example.silver_screen_theaaters;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

public class Movie_details extends AppCompatActivity {


    DBlink_movie DB;
    TextView data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        DB = new DBlink_movie(this);
        data = findViewById(R.id.details_title);


        Bundle b = getIntent().getExtras();
        String movie_name = b.getString("Title");
        Cursor cursor = DB.get_movie_details(movie_name);
        cursor.moveToFirst();
        String a = cursor.getString(0);
        String c = cursor.getString(1);
        String d = cursor.getString(2);
        String e = cursor.getString(3);
        String f = cursor.getString(4);

        String out = "Name : "+a+'\n'+"Type & Cost : "+c+d+'\n'+"Time : "+e+'\n'+"Total Ticket : "+f;

        data.setText(out);




    }
}