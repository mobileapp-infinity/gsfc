package com.infinity.infoway.gsfc.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.infinity.infoway.gsfc.R;
import com.infinity.infoway.gsfc.app.DataStorage;
import com.infinity.infoway.gsfc.model.BatchPojo;

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_batch_checkbox, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
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



        private CheckBox cbcheck;


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
