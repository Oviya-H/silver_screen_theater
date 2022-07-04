package com.example.silver_screen_theaaters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class home_list_adapter extends RecyclerView.Adapter<home_list_adapter.MyViewHolder> {

    DBlink_movie DB;
    Context context;
    List<Movie> mData;
    movieItemlistener itemlistener;

    public home_list_adapter(Context context, List<Movie> mData, movieItemlistener itemlistener) {
        this.context = context;
        this.mData = mData;
        this.itemlistener = itemlistener;
    }

    @Override
    public MyViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.home_list_display, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder( home_list_adapter.MyViewHolder holder, int position) {
        holder.title.setText(mData.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        Button delete;
        public MyViewHolder( View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.list_tv);
            delete = itemView.findViewById(R.id.list_btn);

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    DB = new DBlink_movie(context.getApplicationContext());

                    Boolean ret = DB.delete_movie(title.getText().toString());

                    if(ret){
                        Toast.makeText(context.getApplicationContext(), "Movie Deleted", Toast.LENGTH_SHORT).show();
                        Intent refresh = new Intent(context.getApplicationContext(), Home_page.class);
                        context.startActivity(refresh);

                    }else {
                        Toast.makeText(context.getApplicationContext(), "Error Try again!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    itemlistener.onMovieClick(mData.get(getAdapterPosition()));

                }
            });



        }
    }
}
