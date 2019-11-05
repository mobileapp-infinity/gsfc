package com.infinity.infoway.gsfc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.model.birthdata;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder>
{

    private List<birthdata> moviesList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title, year, genre;

        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            genre = (TextView)view.findViewById(R.id.genre);
            year = (TextView)view.findViewById(R.id.year);

        }
    }


    public MoviesAdapter(Context context, List<birthdata> moviesList)
    {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        birthdata movie = moviesList.get(position);
        holder.title.setText(movie.getemp_id());
        holder.genre.setText(movie.getemp_name());
        holder.year.setText(movie.getemp_mobile_no());
    }

    @Override
    public int getItemCount()
    {
        return moviesList.size();
    }
}
