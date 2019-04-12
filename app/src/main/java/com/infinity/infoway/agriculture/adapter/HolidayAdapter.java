package com.infinity.infoway.agriculture.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.model.Holiday;
import com.infinity.infoway.agriculture.model.Holiday_next;

import java.util.List;

/**
 * Created by user01 on 1/10/2018.
 */

public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.MyViewHolder>
{

    private List<Holiday_next> moviesList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title, year, genre;
        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.date_event);
            genre = (TextView) view.findViewById(R.id.name_event);

        }
    }


    public HolidayAdapter(Context context, List<Holiday_next> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public HolidayAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list, parent, false);

        return new HolidayAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HolidayAdapter.MyViewHolder holder, int position) {
        Holiday_next movie = moviesList.get(position);
        holder.title.setText(movie.getHoliday_Name());
        holder.genre.setText(movie.getFrom_Date());
//        holder.year.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
