package com.infinity.infoway.gsfc.adapter;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.model.group_news_detail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user01 on 5/18/2018.
 */

public class Group_News_Adapter  extends RecyclerView.Adapter<Group_News_Adapter.MyViewHolder> {

    private List<group_news_detail> moviesList = new ArrayList<>();
    private Context context;
    private PopupWindow pw;
    ImageView pop_img, cross_img;
    TextView pop_date, pop_subject, pop_content, header_sub;

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        public TextView title, year, genre;
        Button btn_view_news;

        public MyViewHolder(View view)
        {
            super(view);
            title = (TextView) view.findViewById(R.id.date_news);
            genre = (TextView) view.findViewById(R.id.name_news);
            btn_view_news = (Button) view.findViewById(R.id.btn_view_news);

        }
    }


    public Group_News_Adapter(Context context, ArrayList<group_news_detail> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.title.setText(moviesList.get(position).getCn_date());
        holder.genre.setText(moviesList.get(position).getCn_subject());
        holder.btn_view_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //  initiatePopupWindow(view);
                try
                {

                    //We need to get the instance of the LayoutInflater, use the context of this activity
                    LayoutInflater inflater = (LayoutInflater) context
                            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    //Inflate the view from a predefined XML layout
                    View layout = inflater.inflate(R.layout.popup_directivepage,
                            (ViewGroup) view.findViewById(R.id.popup_element));

                    // create a 300px width and 470px height PopupWindow
                    pw = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    pw.setOutsideTouchable(true);
                    pw.setFocusable(true);

                    // Removes default black background
                    pw.setBackgroundDrawable(new BitmapDrawable());
//
                    pw.showAtLocation(view, Gravity.CENTER, 0, 0);
                    // Closes the popup window when touch outside.
                    pop_img = (ImageView) layout.findViewById(R.id.pop_img);
                    pop_date = (TextView) layout.findViewById(R.id.pop_date);
                    pop_date.setText(moviesList.get(position).getCn_date());
                    Log.d("date", String.valueOf(pop_date));
                    pop_content = (TextView) layout.findViewById(R.id.pop_content);
                    pop_content.setText(moviesList.get(position).getCn_content());
                    pop_subject = (TextView) layout.findViewById(R.id.pop_subject);
                    pop_subject.setText(moviesList.get(position).getCn_subject());
                    header_sub = (TextView) layout.findViewById(R.id.header_sub);
                    header_sub.setText(moviesList.get(position).getCn_subject());
                    Glide.with(context).load(moviesList.get(position).getPh_1()).error(R.drawable.no_image).into(pop_img);

                    cross_img = (ImageView) layout.findViewById(R.id.cross_img);
                    cross_img.setOnClickListener(new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View view)
                        {
                            pw.dismiss();
                        }
                    });


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

            }


        });


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
