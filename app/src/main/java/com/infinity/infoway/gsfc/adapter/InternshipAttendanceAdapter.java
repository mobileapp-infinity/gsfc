package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.infinity.infoway.gsfc.CommonCls.CustomTextView;
import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.ELGroupDisplayPojo;
import com.infinity.infoway.gsfc.model.Holiday_next;
import com.infinity.infoway.gsfc.model.PunchInPunchOutpojo;

import java.util.ArrayList;
import java.util.List;

public class InternshipAttendanceAdapter extends BaseAdapter
{

    private Context ctx;
    ViewHolder holder = null;
    private LayoutInflater inflater;
    View view;
    PunchInPunchOutpojo punchInPunchOutpojo;
    DataStorage storage;
    Activity a;


    public InternshipAttendanceAdapter(Context ctx,PunchInPunchOutpojo punchInPunchOutpojo)
    {

        this.ctx = ctx;
        inflater = LayoutInflater.from(this.ctx);
        this.punchInPunchOutpojo = punchInPunchOutpojo;
        storage = new DataStorage("Login_Detail", ctx);

    }

    @Override
    public int getCount()
    {
        // return customBenifitList.size();
        return punchInPunchOutpojo.getData().size();
    }

    @Override
    public Object getItem(int position)
    {
        return position;
        //  return customBenifitList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }


    private class ViewHolder
    {
CustomTextView tv_intnship,tv_company,tv_desc,tv_start_date,tv_end_date,tv_shift,tv_total_marks,tv_pass_marks,tv_your_marks,tv_doc;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        view = convertView;
        //  LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
        {
            view = inflater.inflate(R.layout.internship_att_adaptr, null);
            holder = new ViewHolder();

            holder.tv_intnship = (CustomTextView) view.findViewById(R.id.tv_intnship);
            holder.tv_company = (CustomTextView) view.findViewById(R.id.tv_company);
            holder.tv_desc = (CustomTextView) view.findViewById(R.id.tv_desc);
            holder.tv_start_date = (CustomTextView) view.findViewById(R.id.tv_start_date);
            holder.tv_end_date = (CustomTextView) view.findViewById(R.id.tv_end_date);
            holder.tv_shift = (CustomTextView) view.findViewById(R.id.tv_shift);
            holder.tv_total_marks = (CustomTextView) view.findViewById(R.id.tv_total_marks);
            holder.tv_pass_marks = (CustomTextView) view.findViewById(R.id.tv_pass_marks);
            holder.tv_your_marks = (CustomTextView) view.findViewById(R.id.tv_your_marks);
            holder.tv_doc = (CustomTextView) view.findViewById(R.id.tv_doc);



            view.setTag(holder);
        }
        else
            {
            holder = (ViewHolder) view.getTag();
        }

//        holder.tv_tv1.setText(elGroupDisplayPojo.getTable().get(position).getEl_grp_name() + "(" + elGroupDisplayPojo.getTable().get(position).getEl_grp_type_id() + ")"+"");

        holder.tv_intnship.setText(" "+punchInPunchOutpojo.getData().get(position).getInts_name() + "");
        holder.tv_company.setText(" "+punchInPunchOutpojo.getData().get(position).getCm_name() + "");
        holder.tv_desc.setText(" "+punchInPunchOutpojo.getData().get(position).getInts_description() + "");
        holder.tv_start_date.setText(" "+punchInPunchOutpojo.getData().get(position).getInts_from_date() + "");
        holder.tv_end_date.setText(" "+punchInPunchOutpojo.getData().get(position).getInts_to_date() + "");
        holder.tv_shift.setText(" "+punchInPunchOutpojo.getData().get(position).getShift_time() + "");
        holder.tv_total_marks.setText(" "+punchInPunchOutpojo.getData().get(position).getInts_total_marks() + "");
        holder.tv_pass_marks.setText(" "+punchInPunchOutpojo.getData().get(position).getInts_passing_marks() + "");
        holder.tv_your_marks.setText(" "+punchInPunchOutpojo.getData().get(position).getIsm_mark() + "");
        holder.tv_doc.setText(" "+punchInPunchOutpojo.getData().get(position).getIsm_mark() + "");


        return view;
    }




}
