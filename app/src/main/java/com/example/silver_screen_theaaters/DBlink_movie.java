package com.example.silver_screen_theaaters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBlink_movie extends SQLiteOpenHelper {
    public static final String DBNAME = "Movie.db";

    public DBlink_movie(Context context) {

        super(context, "Movie.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table movies(movie TEXT, seat_type TEXT ,type_cost TEXT, time_slots TEXT, tickets TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS movies");
    }

    public Boolean insert_value(String movies, String seat_type,String type_cost, String time_slots, String tickets){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("movie", movies);
        values.put("seat_type",seat_type);
        values.put("type_cost",type_cost);
        values.put("time_slots", time_slots);
        values.put("tickets", tickets);
        long result = db.insert("movies",null,values);
        return result != -1;

    }

    public  Cursor get_movies(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies ",null);
        return cursor;
    }

    public Cursor get_movie_details(String movie_name){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM movies where movie = ? ",new String[]{movie_name});
        return cursor;
    }

    public Boolean delete_movie(String movie_name){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("movies", "movie=?", new String[]{movie_name});
        return result != -1;
    }


}
