package com.infinity.infoway.gsfc.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.model.Holiday_next;

import java.util.List;

/**
 * Created by user01 on 1/12/2018.
 */

public class NextHoliadyAdapter extends RecyclerView.Adapter<NextHoliadyAdapter.MyViewHolder> {

    private List<Holiday_next> moviesList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.from_date_next_holiday);
            genre = (TextView) view.findViewById(R.id.day_next_holiday);
            year = (TextView) view.findViewById(R.id.to_date_next_holiday);
        }
    }


    public NextHoliadyAdapter(Context context, List<Holiday_next> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.next_holiday_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Holiday_next movie = moviesList.get(position);
        holder.title.setText(movie.getFrom_Date());
        holder.genre.setText(movie.getHoliday_Name());
        holder.year.setText(movie.getTo_Date());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
