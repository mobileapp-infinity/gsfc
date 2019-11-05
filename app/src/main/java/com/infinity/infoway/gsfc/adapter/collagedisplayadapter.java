package com.infinity.infoway.gsfc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.model.collage_list;

import java.util.List;

/**
 * Created by user01 on 1/17/2018.
 */

public class collagedisplayadapter extends RecyclerView.Adapter<collagedisplayadapter.MyViewHolder> {

    private List<collage_list> moviesList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title, year, genre;

        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView)view.findViewById(R.id.collage_name);
//            genre = (TextView) view.findViewById(R.id.day_next_holiday);
//            year = (TextView) view.findViewById(R.id.to_date_next_holiday);
        }
    }


    public collagedisplayadapter(Context context, List<collage_list> moviesList)
    {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_group, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        collage_list movie = moviesList.get(position);
        holder.title.setText(movie.getCollege_name());
//        holder.genre.setText(movie.getHoliday_Name());
//        holder.year.setText(movie.getTo_Date());
    }

    @Override
        public int getItemCount() {
        return moviesList.size();
    }
}
