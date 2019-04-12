package com.infinity.infoway.agriculture.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.infinity.infoway.agriculture.R;
import com.infinity.infoway.agriculture.activity.FillAttendanceActivityfaculty;
import com.infinity.infoway.agriculture.app.DataStorage;
import com.infinity.infoway.agriculture.model.BatchPojo;
import com.infinity.infoway.agriculture.model.FacultyPojo;
import com.infinity.infoway.agriculture.model.TeachingMethodPojo;

public class BatchAdapterCheckBox  extends RecyclerView.Adapter<BatchAdapterCheckBox.ViewHolder> {

    Activity a;
    Context ctx;
    DataStorage storage;
    BatchPojo batchPojo;

    public BatchAdapterCheckBox(Context ctx, BatchPojo batchPojo) {

        this.ctx = ctx;
        this.batchPojo = batchPojo;

        storage = new DataStorage("Login_Detail", ctx);

    }

    @Override
    public BatchAdapterCheckBox.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_batch_checkbox, parent, false);
        return new BatchAdapterCheckBox.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final BatchAdapterCheckBox.ViewHolder holder, int position)
    {
        holder.batchPojo = batchPojo;
        holder.cbcheck.setText(batchPojo.getTable().get(position).getBch_name()+"");

    }


    @Override
    public int getItemCount()
    {
        return batchPojo.getTable().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        final View mView;
        BatchPojo batchPojo;



        private android.widget.CheckBox cbcheck;


        ViewHolder(View view)
        {
            super(view);
            mView = view;


            this.cbcheck = (CheckBox) view.findViewById(R.id.cb_lv_batch);
            this.cbcheck.setChecked(true);
            this.cbcheck.setEnabled(false);


        }


    }
}
