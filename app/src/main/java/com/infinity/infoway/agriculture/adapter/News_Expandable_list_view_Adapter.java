package com.infinity.infoway.agriculture.adapter;

import android.content.Context;
import android.graphics.Paint;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.model.group_news_detail;

import java.util.ArrayList;

/**
 * Created by user01 on 12/7/2017.
 */

public class News_Expandable_list_view_Adapter extends RecyclerView.Adapter<News_Expandable_list_view_Adapter.ViewHolder>
{
    private Context context;
    ImageView pop_img, cross_img;
    Button pop_cancel;
    LinearLayout  news_layout;

    TextView pop_date, pop_subject, pop_content, header_sub;
    private ArrayList<group_news_detail> news_values = new ArrayList<group_news_detail>();
    private PopupWindow pw;

    public News_Expandable_list_view_Adapter(Context context, ArrayList<group_news_detail> news_values)
    {
        this.context = context;
        this.news_values = news_values;

    }

    @Override
    public News_Expandable_list_view_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_view_list, parent, false);
        return new News_Expandable_list_view_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(News_Expandable_list_view_Adapter.ViewHolder holder, final int position)
    {

        if(news_values.get(position).getCn_content().equals("")&&news_values.get(position).getCn_subject().equals("")&&news_values.get(position).getCn_date().equals("")&&news_values.get(position).getPh_1().equals("")&&news_values.get(position).getPh_2().equals("")&&news_values.get(position).getCn_id().equals("0"))
        {
            Toast.makeText(context,"No records found",Toast.LENGTH_LONG).show();
            news_layout.setVisibility(View.GONE);
        }
        holder.news_text_date.setText(news_values.get(position).getCn_date());
        holder.news_header_content.setText(news_values.get(position).getCn_subject());
        holder.news_view_button.setOnClickListener(new View.OnClickListener()
        {
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
                    View layout = inflater.inflate(R.layout.popup,
                            (ViewGroup) view.findViewById(R.id.popup_element));

                    // create a 300px width and 470px height PopupWindow
                    pw = new PopupWindow(layout, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    pw.setOutsideTouchable(true);
                    pw.setFocusable(true);
                    pw.setBackgroundDrawable(new BitmapDrawable());
                    pw.showAtLocation(view, Gravity.CENTER, 0, 0);
                    pop_img = (ImageView) layout.findViewById(R.id.pop_img);
                    pop_date = (TextView) layout.findViewById(R.id.pop_date);
                    pop_date.setText(news_values.get(position).getCn_date());
                    Log.d("date", String.valueOf(pop_date));
                    pop_content = (TextView) layout.findViewById
                            (R.id.pop_content);
                    pop_content.setText(news_values.get(position).getCn_content());
                    pop_subject = (TextView) layout.findViewById(R.id.pop_subject);
                    pop_subject.setText(news_values.get(position).getCn_subject());
                    header_sub = (TextView) layout.findViewById(R.id.header_sub);
                    header_sub.setText(news_values.get(position).getCn_subject());
                    Glide.with(context).load(news_values.get(position).getPh_1()).error(R.drawable.no_image).into(pop_img);
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
    public int getItemCount()
    {
        return news_values.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView news_text_date;
        TextView news_header_content;
        Button news_view_button;

        ViewHolder(View view)
        {
            super(view);
            news_layout=(LinearLayout)view.findViewById(R.id.news_layout);
            news_text_date = (TextView) view.findViewById(R.id.news_text_date);
            news_text_date.setPaintFlags(news_text_date.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            news_header_content = (TextView) view.findViewById(R.id.news_header_content);
            news_view_button = (Button) view.findViewById(R.id.news_view_button);
        }

//        @Override
//        public String toString() {
//            return null;
//        }
    }
}
