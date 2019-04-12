package com.infinity.infoway.agriculture.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.model.Holiday;
import com.infinity.infoway.agriculture.model.birthdata;
import com.infinity.infoway.agriculture.model.collage_list;

import java.util.List;

/**
 * Created by user01 on 1/27/2018.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {

    private List<Holiday> moviesList;
    Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.name_event);
            genre = (TextView) view.findViewById(R.id.date_event);

        }
    }

    public EventAdapter(Context context, List<Holiday> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }


    @Override
    public EventAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_list, parent, false);

        return new EventAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EventAdapter.MyViewHolder holder, int position) {
        Holiday movie = moviesList.get(position);
        holder.title.setText(movie.getGenre());
        holder.genre.setText(movie.getTitle());

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
