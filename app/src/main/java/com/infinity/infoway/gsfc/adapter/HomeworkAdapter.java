package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.fragment.HomeWorkFragment;
import com.infinity.infoway.gsfc.model.homework_array;
import java.util.ArrayList;


public class HomeworkAdapter extends RecyclerView.Adapter<HomeworkAdapter.ViewHolder>
{

    private ArrayList<homework_array> mValues = new ArrayList<homework_array>();
    private final HomeWorkFragment.OnListFragmentInteractionListener mListener;
    Activity a;
    Context ctx;
    DataStorage storage;

    public HomeworkAdapter(Activity a, ArrayList<homework_array> items, HomeWorkFragment.OnListFragmentInteractionListener listener)
    {
        mValues = items;
        mListener = listener;
        this.ctx = a;
        this.a = a;
        storage = new DataStorage("Login_Detail", ctx);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.view_home_work_student, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.mItem = mValues;
        if (mValues.get(position).getHomework_desc().equals(""))
        {
            holder.mainhomeworkll.setVisibility(View.GONE);
        }
        else
            {
            holder.mainhomeworkll.setVisibility(View.VISIBLE);
            holder.tv_description_hm.setText(mValues.get(position).getHomework_desc());
            holder.tv_subject_homework.setText(mValues.get(position).getSubject_name());
        }
        holder.tvsubject_cn.setText(mValues.get(position).getSubject_name());
        holder.tv_description_cn.setText(mValues.get(position).getCont_deli_desc());
        holder.main_lect_no.setText(mValues.get(position).getLect_no());
    }


    @Override
    public int getItemCount()
    {
        return mValues.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
        {
        final View mView;
        TextView tvsubject_cn, main_lect_no,tv_description_cn, tv_subject_homework, tv_description_hm;
        ImageView imglecture;
        LinearLayout mainlectlayout, mainhomeworkll;
        ArrayList<homework_array> mItem;
        RelativeLayout rllectureheader;

        ViewHolder(View view)
        {
            super(view);
            mView = view;
            main_lect_no=(TextView)view.findViewById(R.id.main_lect_no);
            tvsubject_cn = (TextView) view.findViewById(R.id.tvsubject_cn);
            tv_description_cn = (TextView) view.findViewById(R.id.tv_description_cn);
            tv_subject_homework = (TextView) view.findViewById(R.id.tv_subject_homework);
            tv_description_hm = (TextView) view.findViewById(R.id.tv_description_hm);

            mainhomeworkll = (LinearLayout) view.findViewById(R.id.mainhomeworkll);
            mainlectlayout = (LinearLayout) view.findViewById(R.id.mainlectlayout);
            mainlectlayout = (LinearLayout) view.findViewById(R.id.mainlectlayout);
            rllectureheader = (RelativeLayout) view.findViewById(R.id.rllectureheader);
            imglecture = (ImageView) view.findViewById(R.id.imglecture);
        }


    }
}
