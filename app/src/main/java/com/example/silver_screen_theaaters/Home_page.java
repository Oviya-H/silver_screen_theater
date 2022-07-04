package com.example.silver_screen_theaaters;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Home_page extends AppCompatActivity implements movieItemlistener{


    RecyclerView home_rv;
    Button add_btn;
    DBlink_movie DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        home_rv = findViewById(R.id.home_movie_list);
        add_btn = findViewById(R.id.add_btn);
        DB = new DBlink_movie(this);


        List<Movie> movie_list = new ArrayList<>();

         Cursor cursor = DB.get_movies();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String Movie_name = cursor.getString(0);
                movie_list.add(new Movie(Movie_name));
               cursor.moveToNext();
            }
         }
        home_list_adapter adapter1 = new home_list_adapter(this, movie_list, this);
        home_rv.setAdapter(adapter1);

        home_rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), add_page.class);
                startActivity(intent);
            }
        });



    }

    @Override
    public void onMovieClick(Movie movie) {
        Intent intent = new Intent(this, Movie_details.class);
        intent.putExtra("Title", movie.getTitle());
        startActivity(intent);
    }
}